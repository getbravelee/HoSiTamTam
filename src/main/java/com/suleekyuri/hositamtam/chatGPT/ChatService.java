package com.suleekyuri.hositamtam.chatGPT;

import org.springframework.ai.chat.messages.Message;
import org.springframework.ai.chat.messages.SystemMessage;
import org.springframework.ai.chat.messages.UserMessage;
import org.springframework.ai.chat.model.ChatModel;
import org.springframework.ai.chat.prompt.PromptTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

@Service
public class ChatService {

    private final ChatModel chatModel;

    @Autowired
    public ChatService(ChatModel chatModel) {
        this.chatModel = chatModel;
    }

    public String getChatbotResponse(String userInput) {
        // 명확한 지침 제공
        String command = """
                <instruction>
                You are the best real estate expert in Korea.
                I always answer in Korean.
                See www.naver.com .
                See placeview.co.kr .
                See www.hogangnono.com .
                It informs the actual transaction price of apartments in Korea based on the latest transaction details. Information provides five recent actual transaction prices for each acreage.
                It informs you of nearby convenience facilities (convenience stores, transportation, school districts, etc.) in detail. It provides various information such as branch name, distance, etc. in detail.
                When you tell me about apartment price trends and trends, show them in easy-to-see and pretty tables and graphs.
                When you give information about local school districts, please give information about the distance of daycare centers, kindergartens, elementary schools, middle schools, and high schools, and what the actual school district level is
                </instruction>
                """;

        PromptTemplate template = new PromptTemplate(command);
        template.add("userInput", userInput);  // 사용자 입력을 템플릿에 추가

        String message = template.render();
        Message userMessage = new UserMessage(message);
        Message systemMessage = new SystemMessage("""
                You are the best real estate expert in Korea.
                I always answer in Korean.
                See www.naver.com .
                See placeview.co.kr .
                See www.hogangnono.com .
                It informs the actual transaction price of apartments in Korea based on the latest transaction details. Information provides five recent actual transaction prices for each acreage.
                It informs you of nearby convenience facilities (convenience stores, transportation, school districts, etc.) in detail. It provides various information such as branch name, distance, etc. in detail.
                When you tell me about apartment price trends and trends, show them in easy-to-see and pretty tables and graphs.
                When you give information about local school districts, please give information about the distance of daycare centers, kindergartens, elementary schools, middle schools, and high schools, and what the actual school district level is
                """);

        // OpenAI API 호출하여 응답 받기
        return chatModel.call(userMessage, systemMessage);  // 챗봇 응답 반환
    }
}