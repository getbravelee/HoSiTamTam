package com.suleekyuri.hositamtam.aptTrade;

import jakarta.xml.bind.JAXBContext;
import jakarta.xml.bind.JAXBException;
import jakarta.xml.bind.Unmarshaller;
import jakarta.xml.bind.annotation.XmlRootElement;
import lombok.Getter;
import lombok.Setter;
import lombok.extern.slf4j.Slf4j;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.StringReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLEncoder;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Slf4j
public class DataService {
    //    https://api.odcloud.kr/api/15123287/v1/uddi:c167d44a-d8ad-4624-b442-a67e904635d0?page=10&perPage=5000&serviceKey=bmmOlbSJYHYcuI19Sohn%2FMBSL40ggmbScRyJCo72LGOo%2BBSfNYhSfeKCwakYHYBHXQAo1hI6ChILPlO9548ogA%3D%3D
//    https://api.odcloud.kr
//    /api
//    /15123287
//    /v1
//    /uddi:c167d44a-d8ad-4624-b442-a67e904635d0
//    ?page=10
//    &perPage=5000
//    &serviceKey=bmmOlbSJYHYcuI19Sohn%2FMBSL40ggmbScRyJCo72LGOo%2BBSfNYhSfeKCwakYHYBHXQAo1hI6ChILPlO9548ogA%3D%3D
//    bmmOlbSJYHYcuI19Sohn/MBSL40ggmbScRyJCo72LGOo+BSfNYhSfeKCwakYHYBHXQAo1hI6ChILPlO9548ogA==

    public void callApiAndSaveData(String apiKey) throws IOException {
        String apiUrl = "https://api.odcloud.kr/api/15123287/v1/uddi:c167d44a-d8ad-4624-b442-a67e904635d0";
        String page = "10";
        String perPage = "5000";
        String serviceKey = apiKey; // 본인이 발급 받은 api키

        StringBuilder urlBuilder = new StringBuilder(apiUrl);
        urlBuilder.append("?" + URLEncoder.encode("page", "UTF-8") + "=" + page);
        urlBuilder.append("&" + URLEncoder.encode("perPage", "UTF-8") + "=" + URLEncoder.encode(perPage, "UTF-8"));
        urlBuilder.append("&" + URLEncoder.encode("serviceKey", "UTF-8") + "=" + URLEncoder.encode(serviceKey, "UTF-8"));

        URL url = new URL(urlBuilder.toString());
        HttpURLConnection conn = (HttpURLConnection) url.openConnection();

        conn.setRequestMethod("GET");
        conn.setRequestProperty("Content-type", "application/json");
        log.info("Response code: " + conn.getResponseCode());

        BufferedReader rd;
        if (conn.getResponseCode() >= 200 && conn.getResponseCode() <= 300) {
            rd = new BufferedReader(new InputStreamReader(conn.getInputStream()));
        } else {
            rd = new BufferedReader(new InputStreamReader(conn.getErrorStream()));
        }

        StringBuilder sb = new StringBuilder();
        String line;

        while ((line = rd.readLine()) != null) {
            sb.append(line);
        }

        rd.close();
        conn.disconnect();

        String xml = sb.toString();
        Map<String, DataSave> result = new HashMap<>();
        try {
            JAXBContext jaxbContext = JAXBContext.newInstance(DataSave.class); // JAXB Context 생성
            Unmarshaller unmarshaller = jaxbContext.createUnmarshaller();  // Unmarshaller Object 생성
            DataSave apiResponse = (DataSave) unmarshaller.unmarshal(new StringReader(xml)); // unmarshall 메서드 호출
            result.put("response", apiResponse);

            if (apiResponse.getBody() != null && apiResponse.getBody().getItems() != null) {
                List<DataSave.Body.Items.Item> itemList = apiResponse.getBody().getItems().getItem();
                List<Data> datas = itemList.stream()
                        .map(this::convertToEntity)
                        .collect(Collectors.toList());
                dataRepository.saveAll(datas);
            }
        } catch (JAXBException e) {
            e.printStackTrace();
        }
    }

}



}
