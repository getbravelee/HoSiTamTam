package com.suleekyuri.hositamtam.aptRent;

import jakarta.xml.bind.JAXBContext;
import jakarta.xml.bind.JAXBException;
import jakarta.xml.bind.Unmarshaller;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.StringReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLEncoder;
import java.util.List;
import java.util.stream.Collectors;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Slf4j
@Service
public class AptRentService {

    @Autowired
    private AptRentDataRepository aptRentDataRepository;

    public void callApiAndSaveData(String apiKey, List<Integer> lawdCdList, List<Integer> monthList) throws IOException {
        String apiUrl = "https://apis.data.go.kr/1613000/RTMSDataSvcAptRent/getRTMSDataSvcAptRent";

        for (int month : monthList) {
            for (int lawdCd : lawdCdList) {
                int currentPage = 1;
                boolean hasNextPage = true;

                while (hasNextPage) {
                    String url = buildUrl(apiUrl, apiKey, lawdCd, month, currentPage);

                    String response = getApiResponse(url);
                    if (response == null || response.isEmpty()) {
                        log.warn("No response received for LAWD_CD: {}, DEAL_YMD: {}, Page: {}", lawdCd, month, currentPage);
                        break;
                    }

                    AptRentSave apiResponse = parseXmlResponse(response);
                    if (apiResponse == null || apiResponse.getBody() == null || apiResponse.getBody().getItems() == null) {
                        log.warn("Invalid response structure for LAWD_CD: {}, DEAL_YMD: {}, Page: {}", lawdCd, month, currentPage);
                        break;
                    }

                    processApiResponse(apiResponse);

                    // totalCount와 totalPages 계산
                    int totalCount = apiResponse.getBody().getTotalCount();
                    int numOfRows = 1000;
                    int totalPages = (int) Math.ceil((double) totalCount / numOfRows); // 올림 처리

                    log.info("LAWD_CD: {}, DEAL_YMD: {}, CurrentPage: {}, TotalPages: {}", lawdCd, month, currentPage, totalPages);

                    // 다음 페이지 여부 판단
                    currentPage++;

                    // **다음 페이지가 없거나 데이터가 비어있으면 종료**
                    hasNextPage = currentPage <= totalPages && apiResponse.getBody().getItems() != null && !apiResponse.getBody().getItems().getItemList().isEmpty();
                }
            }
        }
    }

    private String buildUrl(String apiUrl, String apiKey, int lawdCd, int dealYmd, int pageNo) throws IOException {
        StringBuilder urlBuilder = new StringBuilder(apiUrl);
        urlBuilder.append("?").append(URLEncoder.encode("numOfRows", "UTF-8")).append("=").append(1000);
        urlBuilder.append("&").append(URLEncoder.encode("pageNo", "UTF-8")).append("=").append(pageNo);
        urlBuilder.append("&").append(URLEncoder.encode("serviceKey", "UTF-8")).append("=").append(apiKey);
        urlBuilder.append("&").append(URLEncoder.encode("LAWD_CD", "UTF-8")).append("=").append(lawdCd);
        urlBuilder.append("&").append(URLEncoder.encode("DEAL_YMD", "UTF-8")).append("=").append(dealYmd);
        return urlBuilder.toString();
    }

    private String getApiResponse(String url) throws IOException {
        HttpURLConnection conn = (HttpURLConnection) new URL(url).openConnection();
        conn.setRequestMethod("GET");
        conn.setRequestProperty("Content-type", "application/json");
        log.info("Request URL: {}", url);

        try (BufferedReader rd = new BufferedReader(new InputStreamReader(conn.getInputStream()))) {
            StringBuilder sb = new StringBuilder();
            String line;
            while ((line = rd.readLine()) != null) {
                sb.append(line);
            }
            return sb.toString();
        } finally {
            conn.disconnect();
        }
    }

    private AptRentSave parseXmlResponse(String xml) {
        try {
            JAXBContext jaxbContext = JAXBContext.newInstance(AptRentSave.class);
            Unmarshaller unmarshaller = jaxbContext.createUnmarshaller();
            return (AptRentSave) unmarshaller.unmarshal(new StringReader(xml));
        } catch (JAXBException e) {
            log.error("Error parsing XML response", e);
            return null;
        }
    }

    private void processApiResponse(AptRentSave apiResponse) {
        if (apiResponse.getBody() == null || apiResponse.getBody().getItems() == null) {
            log.warn("No data found in API response");
            return;
        }

        List<AptRentSave.Item> items = apiResponse.getBody().getItems().getItemList();
        if (items == null || items.isEmpty()) {
            log.warn("No items found in API response");
            return;
        }

        List<AptRentData> rents = items.stream()
                .filter(item -> item.getAptNm() != null && !item.getAptNm().isEmpty()) // apt_name 검증
                .map(this::convertToEntity)
                .collect(Collectors.toList());

        aptRentDataRepository.saveAll(rents);
    }

    private AptRentData convertToEntity(AptRentSave.Item item) {
        AptRentData rent = new AptRentData();
        rent.setAptName(item.getAptNm());
        rent.setBuildYear(item.getBuildYear());
        rent.setContractTerm(item.getContractTerm());
        rent.setContractType(item.getContractType());
        rent.setDealDay(item.getDealDay());
        rent.setDealMonth(item.getDealMonth());
        rent.setDealYear(item.getDealYear());
        rent.setDeposit(item.getDeposit());
        rent.setExcluUseArea(item.getExcluUseAr());
        rent.setFloor(item.getFloor());
        rent.setJibun(item.getJibun());
        rent.setMonthlyRent(item.getMonthlyRent());
        rent.setPreDeposit(item.getPreDeposit());
        rent.setPreMonthlyRent(item.getPreMonthlyRent());
        rent.setSggCode(item.getSggCd());
        rent.setUmdName(item.getUmdNm());
        rent.setUseRRRight(item.getUseRRRight());
        return rent;
    }
}
