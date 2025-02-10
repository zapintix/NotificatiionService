package com.example.Notifications.auth.config

import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.http.client.HttpComponentsClientHttpRequestFactory
import org.springframework.web.client.RestClient
import org.springframework.web.client.RestTemplate

@Configuration
class RestClientConfig {
    @Bean
    fun getRestClient()= RestClient.builder()
        .requestFactory(HttpComponentsClientHttpRequestFactory())
        .build()

    @Bean
    fun restTemplate(): RestTemplate {
        return RestTemplate()
    }
}