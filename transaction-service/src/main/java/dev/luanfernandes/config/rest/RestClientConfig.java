package dev.luanfernandes.config.rest;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.client.RestClient;

@Configuration
public class RestClientConfig {

    @Bean
    public RestClient restClient() {
        return RestClient.builder()
                .baseUrl("https://graph.facebook.com/v18.0/296400450226660/messages")
                .defaultHeader(
                        "Authorization",
                        "Bearer EAADZBxNUTUxEBO4bmHwz66Tg8dZAxv7gy5xZAcZCZBrdKVk74YwlfNPC0z7SsKZByfLqHWMbLQeIGgs6YpvpmZAFL8FLxv0rlHDtuxZCdyLKrcxKP4ZC3lfRQZA9TwZAtY0KitT7qxCRYOjKVdSppQz5rDMv7gR70joCP3OoAKOHk492S0FnOfZBJ6ZBa2wOlZCtZA2KBo9gdoyRnh9bs3iP3eUHK6kCdhN52FajvH7k6wZD")
                .build();
    }
}
