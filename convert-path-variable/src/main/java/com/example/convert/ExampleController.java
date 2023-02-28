package com.example.convert;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.web.bind.annotation.*;

import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.security.SignatureException;
import java.security.spec.InvalidKeySpecException;
import java.util.Map;
import java.util.UUID;

@RestController
public class ExampleController {
    @GetMapping("value/{id}")
    public String pathVariableValue(@PathVariable("id") TempUUID uuid){
        return uuid.toString();
    }
    @GetMapping("/value")
    public String getTempUrl(){
        return TempUrlHandler.generateUUIDUrl(UUID.randomUUID().toString());
    }

    @PostMapping("/signature")
    public String getSignature(@RequestBody Map<String,Object> data) throws NoSuchAlgorithmException, InvalidKeySpecException, SignatureException, InvalidKeyException {
        return SignatureGenerator.getSignature(data.toString());
    }


}
