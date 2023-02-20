package com.example.l2cache.jwt;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

@JsonIgnoreProperties(ignoreUnknown = true)
@Data
public class JwtTokenInfo {

    private String email;
    @JsonProperty("preferred_username")
    private String preferredUsername;

}