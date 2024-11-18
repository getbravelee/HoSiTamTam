package com.suleekyuri.hositamtam.config;

import io.swagger.v3.oas.annotations.OpenAPIDefinition;
import io.swagger.v3.oas.annotations.info.Info;
import org.springframework.context.annotation.Configuration;

@OpenAPIDefinition(
        info = @Info(
                title = "Hositamtam API",
                version = "v1",
                description = "사용자 인증 및 기타 API"
        )
)
@Configuration
public class OpenApiConfig {
}