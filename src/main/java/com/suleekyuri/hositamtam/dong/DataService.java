package com.suleekyuri.hositamtam.dong;

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
public class DataService {

    @Autowired
    private DataRepository dataRepository;

    public void callApiAndSaveData(String apiKey) throws IOException {
        String apiUrl = "https://api.odcloud.kr/api/15123287/v1/uddi:a3a4ea9d-2e34-4e32-a092-ef8feb8d71f1";
        for (int i = 1; i <= 50; i++) {
            String page = ""+i;
            String perPage = "1000";
            String returnType = "XML";
            String serviceKey = apiKey;
            System.out.println(apiKey);

            StringBuilder urlBuilder = new StringBuilder(apiUrl);
            urlBuilder.append("?" + URLEncoder.encode("page", "UTF-8") + "=" + page);
            urlBuilder.append("&" + URLEncoder.encode("perPage", "UTF-8") + "=" + URLEncoder.encode(perPage, "UTF-8"));
            urlBuilder.append("&" + URLEncoder.encode("returnType", "UTF-8") + "=" + URLEncoder.encode(returnType, "UTF-8"));
            urlBuilder.append("&").append(URLEncoder.encode("serviceKey", "UTF-8")).append("=").append(serviceKey);

            URL url = new URL(urlBuilder.toString());
            HttpURLConnection conn = (HttpURLConnection) url.openConnection();
            conn.setRequestMethod("GET");
            conn.setRequestProperty("Content-type", "application/json");

            BufferedReader rd;
            if (conn.getResponseCode() >= 200 && conn.getResponseCode() <= 300) {
                rd = new BufferedReader(new InputStreamReader(conn.getInputStream()));
            } else {
                rd = new BufferedReader(new InputStreamReader(conn.getErrorStream()));
            }

            log.info("Request URL: " + urlBuilder.toString());
            log.info("Response Code: " + conn.getResponseCode());

            StringBuilder sb = new StringBuilder();
            String line;
            while ((line = rd.readLine()) != null) {
                sb.append(line);
            }
            rd.close();
            conn.disconnect();

            String xml = sb.toString();
            try {
                JAXBContext jaxbContext = JAXBContext.newInstance(DataSave.class);
                Unmarshaller unmarshaller = jaxbContext.createUnmarshaller();
                DataSave apiResponse = (DataSave) unmarshaller.unmarshal(new StringReader(xml));

                processApiResponse(apiResponse);
            } catch (JAXBException e) {
                e.printStackTrace();
            }
        }
    }


    private void processApiResponse(DataSave apiResponse) {
        if (apiResponse.getItems() != null) {
            List<DataSave.Item> items = apiResponse.getItems();
            List<Data> datas = items.stream()
                    .map(this::convertToEntity)
                    .collect(Collectors.toList());
            dataRepository.saveAll(datas);
        }
    }

    private Data convertToEntity(DataSave.Item item) {
        Data data = new Data();
        for (DataSave.Item.Column col : item.getColumns()) {
            switch (col.getName()) {
                case "법정동명":
                    data.setLawDongName(col.getValue());
                    break;
                case "법정동코드":
                    // 지수 표기 처리
                    data.setLawDongCode(Double.valueOf(col.getValue()).longValue());
                    break;
                case "폐지여부":
                    data.setStatus(col.getValue());
                    break;
            }
        }
        return data;
    }
}
