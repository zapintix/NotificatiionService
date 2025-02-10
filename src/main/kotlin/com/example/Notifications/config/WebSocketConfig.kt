package com.example.Notifications.config

import com.example.Notifications.auth.RmeSessionChannelInterceptor
import org.springframework.context.annotation.Configuration
import org.springframework.messaging.simp.config.ChannelRegistration
import org.springframework.messaging.simp.config.MessageBrokerRegistry
import org.springframework.web.socket.config.annotation.EnableWebSocketMessageBroker
import org.springframework.web.socket.config.annotation.StompEndpointRegistry
import org.springframework.web.socket.config.annotation.WebSocketMessageBrokerConfigurer

@Configuration
@EnableWebSocketMessageBroker
class WebSocketConfig(
    private val rmeSessionChannelInterceptor: RmeSessionChannelInterceptor
) : WebSocketMessageBrokerConfigurer {

    override fun registerStompEndpoints(registry: StompEndpointRegistry) {
        println(">>> Registering WebSocket endpoint")
        registry.addEndpoint("/ws")
            .setAllowedOriginPatterns("*")
    }

    override fun configureClientInboundChannel(registration: ChannelRegistration) {
        registration.interceptors(rmeSessionChannelInterceptor)
        println(">>> WebSocket client inbound channel configured with interceptor")
    }

    override fun configureMessageBroker(registry: MessageBrokerRegistry) {
        registry.enableSimpleBroker("/topic", "/user")
        registry.setUserDestinationPrefix("/user")
        registry.setApplicationDestinationPrefixes("/app")
        println(">>> WebSocket message broker configured")
    }
}
