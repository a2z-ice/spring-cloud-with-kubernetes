package com.example.l2cache.jwt;


import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.nio.charset.StandardCharsets;

@Service
@AllArgsConstructor
public class JwtTokenInfoParser {

    private final ObjectMapper objectMapper;

    public JwtTokenInfo getJwtTokenInfo(String token) throws JsonProcessingException {
        if (token == null) throw new RuntimeException();

        var chunks = token.replace("Bearer ","").split("\\.");

        if (chunks.length < 2) throw new RuntimeException("Invalid Authorization Token");
        String payload = new String(java.util.Base64.getDecoder().decode(chunks[1]), StandardCharsets.UTF_8);
        return objectMapper.readValue(payload, JwtTokenInfo.class);
    }

}