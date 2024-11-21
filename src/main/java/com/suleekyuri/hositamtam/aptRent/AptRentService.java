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

    public void callApiAndSaveData(String apiKey, List<Integer> list, List<Integer> monthList) throws IOException {
        String apiUrl = "https://apis.data.go.kr/1613000/RTMSDataSvcAptRent/getRTMSDataSvcAptRent";
        for(int m : monthList) {
            for (int n : list) {
                for(int i = 1; i < 3; i++) {
                    String lawdCd = ""+n;  // 지역코드 (예: 종로구)
                    String dealYmd = ""+m;  // 계약월
                    String pageNo = ""+i;
                    String numOfRows = "1000";
                    String serviceKey = apiKey;

                    StringBuilder urlBuilder = new StringBuilder(apiUrl);
                    urlBuilder.append("?").append(URLEncoder.encode("numOfRows", "UTF-8")).append("=").append(numOfRows);
                    urlBuilder.append("&").append(URLEncoder.encode("pageNo", "UTF-8")).append("=").append(pageNo);
                    urlBuilder.append("&").append(URLEncoder.encode("serviceKey", "UTF-8")).append("=").append(serviceKey);
                    urlBuilder.append("&").append(URLEncoder.encode("LAWD_CD", "UTF-8")).append("=").append(lawdCd);
                    urlBuilder.append("&").append(URLEncoder.encode("DEAL_YMD", "UTF-8")).append("=").append(dealYmd);

                    URL url = new URL(urlBuilder.toString());
                    HttpURLConnection conn = (HttpURLConnection) url.openConnection();
                    conn.setRequestMethod("GET");
                    conn.setRequestProperty("Content-type", "application/json");
                    log.info("Request URL: {}", urlBuilder.toString());

                    BufferedReader rd = new BufferedReader(new InputStreamReader(conn.getInputStream()));
                    StringBuilder sb = new StringBuilder();
                    String line;
                    while ((line = rd.readLine()) != null) {
                        sb.append(line);
                    }
                    rd.close();
                    conn.disconnect();

                    String xml = sb.toString();
                    try {
                        JAXBContext jaxbContext = JAXBContext.newInstance(AptRentSave.class);
                        Unmarshaller unmarshaller = jaxbContext.createUnmarshaller();
                        AptRentSave apiResponse = (AptRentSave) unmarshaller.unmarshal(new StringReader(xml));

                        processApiResponse(apiResponse);
                    } catch (JAXBException e) {
                        e.printStackTrace();
                    }
                }

            }
        }
    }

    private void processApiResponse(AptRentSave apiResponse) {
        if (apiResponse == null || apiResponse.getBody() == null || apiResponse.getBody().getItems() == null) {
            log.warn("No data found in API response");
            return;
        }

        List<AptRentSave.Item> items = apiResponse.getBody().getItems().getItemList();
        if (items == null || items.isEmpty()) {
            log.warn("No items found in API response");
            return;
        }

        List<AptRentData> rents = items.stream()
                .map(this::convertToEntity)
                .collect(Collectors.toList());
        aptRentDataRepository.saveAll(rents);
    }

    private AptRentData convertToEntity(AptRentSave.Item item) {
        AptRentData rent = new AptRentData();
        rent.setAptName(item.getAptNm()); // 아파트 이름
        rent.setBuildYear(item.getBuildYear()); // 건축 연도
        rent.setContractTerm(item.getContractTerm()); // 계약 기간
        rent.setContractType(item.getContractType()); // 계약 유형
        rent.setDealDay(item.getDealDay()); // 거래 일
        rent.setDealMonth(item.getDealMonth()); // 거래 월
        rent.setDealYear(item.getDealYear()); // 거래 연도
        rent.setDeposit(item.getDeposit());// 보증금
        rent.setExcluUseArea(item.getExcluUseAr()); // 전용 면적
        rent.setFloor(item.getFloor()); // 층수
        rent.setJibun(item.getJibun()); // 지번
        rent.setMonthlyRent(item.getMonthlyRent()); // 월세
        rent.setPreDeposit(item.getPreDeposit()); // 이전 보증금
        rent.setPreMonthlyRent(item.getPreMonthlyRent()); // 이전 월세
        rent.setSggCode(item.getSggCd()); // 시군구 코드
        rent.setUmdName(item.getUmdNm()); // 읍면동명
        rent.setUseRRRight(item.getUseRRRight()); // 사용 권리

        return rent;
    }
}
