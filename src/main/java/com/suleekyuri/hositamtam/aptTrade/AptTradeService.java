package com.suleekyuri.hositamtam.aptTrade;

import jakarta.xml.bind.JAXBContext;
import jakarta.xml.bind.JAXBException;
import jakarta.xml.bind.Unmarshaller;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.StringReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLEncoder;
import java.util.List;
import java.util.stream.Collectors;

@Slf4j
@Service
public class AptTradeService {

    @Autowired
    private AptTradeDataRepository aptTradeDataRepository;

    public void callApiAndSaveData(String apiKey, List<Integer> list, List<Integer> monthList) throws IOException {
        String apiUrl = "https://apis.data.go.kr/1613000/RTMSDataSvcAptTrade/getRTMSDataSvcAptTrade";
        for(int m : monthList) {
            System.out.println("====================" + m + "====================");
            for (int n : list) {
                String lawdCd = ""+n;  // 지역코드 (예: 종로구)
                String dealYmd = ""+m;  // 계약월
                String pageNo = "1";
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
                    JAXBContext jaxbContext = JAXBContext.newInstance(AptTradeSave.class);
                    Unmarshaller unmarshaller = jaxbContext.createUnmarshaller();
                    AptTradeSave apiResponse = (AptTradeSave) unmarshaller.unmarshal(new StringReader(xml));

                    processApiResponse(apiResponse);
                } catch (JAXBException e) {
                    e.printStackTrace();
                }
            }
        }
    }


    private void processApiResponse(AptTradeSave apiResponse) {
        if (apiResponse == null || apiResponse.getBody() == null || apiResponse.getBody().getItems() == null) {
            log.warn("No data found in API response");
            return;
        }

        List<AptTradeSave.Item> items = apiResponse.getBody().getItems().getItemList();
        if (items == null || items.isEmpty()) {
            log.warn("No items found in API response");
            return;
        }

        List<AptTradeData> trades = items.stream()
                .map(this::convertToEntity)
                .collect(Collectors.toList());
        aptTradeDataRepository.saveAll(trades);
    }

    private AptTradeData convertToEntity(AptTradeSave.Item item) {
        AptTradeData trade = new AptTradeData();
        trade.setAptName(item.getAptNm());
        trade.setBuildYear(item.getBuildYear());
        trade.setBuyerType(item.getBuyerGbn());
        trade.setDealAmount(item.getDealAmount());
        trade.setDealYear(item.getDealYear());
        trade.setDealMonth(item.getDealMonth());
        trade.setDealDay(item.getDealDay());
        trade.setDealingType(item.getDealingGbn());
        trade.setExcluUseArea(item.getExcluUseAr());
        trade.setFloor(item.getFloor());
        trade.setJibun(item.getJibun());
        trade.setLandLeaseholdGbn(item.getLandLeaseholdGbn());
        trade.setSggCode(item.getSggCd());
        trade.setUmdName(item.getUmdNm());
        trade.setEstateAgentAddress(item.getEstateAgentSggNm());
        return trade;
    }


}
