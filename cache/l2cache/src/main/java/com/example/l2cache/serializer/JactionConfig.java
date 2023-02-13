package com.example.l2cache.serializer;

import org.springframework.boot.autoconfigure.jackson.Jackson2ObjectMapperBuilderCustomizer;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class JactionConfig {
    @Bean
    public Jackson2ObjectMapperBuilderCustomizer addDraftDataDeserializer() {
        return jacksonObjectMapperBuilder -> jacksonObjectMapperBuilder.deserializerByType(DraftData.class, new DraftDataDeserializer());
    }
}
