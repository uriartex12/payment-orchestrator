package com.sgi.payment_orchestrator.config;

import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.reactive.function.client.WebClient;

@Configuration
public class WebClientConfig {

    @Bean
    @Qualifier("account")
    public WebClient accountClient(@Value("${feign.client.config.account-service.url}") String endpoint){
        return WebClient.builder()
                .baseUrl(endpoint)
                .build();
    }

    @Bean
    @Qualifier("transaction")
    public WebClient transactionClient(@Value("${feign.client.config.transaction-service.url}") String endpoint){
        return WebClient.builder()
                .baseUrl(endpoint)
                .build();
    }
}
