package com.example.convert;

import lombok.extern.slf4j.Slf4j;

import java.time.Instant;

@Slf4j
public class TempUrlHandler {
    static public void validateTemporaryUrl( String time) {



        long expirationTime = Long.parseLong(time);
        final long currentTime = Instant.now().getEpochSecond();
        log.info("seconds url {}: {}", currentTime, expirationTime);
        log.info("deference {}", (expirationTime - currentTime));

        if ( currentTime > expirationTime) {
            throw new RuntimeException("Url is invalid or expired");
        }


    }
    static public String generateUUIDUrl(String id){
        final String tempUrl = Instant.now().plusSeconds(40).getEpochSecond() + ":" + id;
        log.info("generated url before encrypt: {}", tempUrl);
        // 5 hours 5 * 60 * 60
        return CryptoUtils.encrypt(tempUrl);

    }
}
