package com.suleekyuri.hositamtam.chatGPT;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

@Service
public class ChatService {

    @Value("${kakao-admin-key}")
    private String apiKey;

    @Autowired
    private final RestTemplate restTemplate;

    public ChatService(RestTemplate restTemplate) {
        this.restTemplate = restTemplate;
    }

    public String getChatbotResponse(String userInput) {
        String url = "https://api.openai.com/v1/chat/completions";
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        headers.setBearerAuth(apiKey);

        // 사용할 모델의 이름을 여기서 지정합니다.
        String requestBody = String.format(
                "{\"model\": \"gpt-3.5-turbo\", \"messages\": [{\"role\": \"user\", \"content\": \"%s\"}]}",
                userInput
        );

        HttpEntity<String> entity = new HttpEntity<>(requestBody, headers);
        ResponseEntity<String> response = restTemplate.postForEntity(url, entity, String.class);
        String responseBody = response.getBody();

        // 응답에서 실제 챗봇의 응답 메시지만 추출
        String chatResponse = extractChatbotMessage(responseBody);
        System.out.println(chatResponse);
        return chatResponse;
    }
    /*
        public String getChatbotResponse(String userInput) {
        String url = "https://api.openai.com/v1/chat/completions";
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        headers.setBearerAuth(apiKey);
        // 사용할 모델의 이름을 여기서 지정합니다.
        String requestBody = String.format(
                "{\"model\": \"gpt-3.5-turbo\", \"messages\": [{\"role\": \"user\", \"content\": \"%s\"}]}",
                userInput
        );

        HttpEntity<String> entity = new HttpEntity<>(requestBody, headers);
        ResponseEntity<String> response = restTemplate.postForEntity(url, entity, String.class);
        System.out.println(response.getBody());
        return response.getBody();
    }
    */

    private String extractChatbotMessage(String responseBody) {
        // 응답 JSON에서 choices 배열의 첫 번째 요소의 message.content 추출
        // 예를 들어: {"choices":[{"message":{"content":"안녕"}}]}
        try {
            JsonNode rootNode = new ObjectMapper().readTree(responseBody);
            return rootNode.path("choices").get(0).path("message").path("content").asText();
        } catch (Exception e) {
            e.printStackTrace();
            return "챗봇 응답을 처리하는 데 문제가 발생했습니다.";
        }
    }
}
