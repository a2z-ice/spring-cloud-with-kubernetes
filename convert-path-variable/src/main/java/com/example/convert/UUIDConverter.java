package com.example.convert;

import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;

@Component
public class UUIDConverter implements Converter<String, TempUUID> {

    @Override
    public TempUUID convert(String source) {

        final String[] urlElements = CryptoUtils.decrypt(source).split(":");
        if(urlElements.length < 2){
            throw new RuntimeException("Invalid url");
        }
        String expirationTimeStr = urlElements[0];
        String uuid = urlElements[1];

        TempUrlHandler.validateTemporaryUrl(expirationTimeStr);
        return new TempUUID(uuid);
    }
}
