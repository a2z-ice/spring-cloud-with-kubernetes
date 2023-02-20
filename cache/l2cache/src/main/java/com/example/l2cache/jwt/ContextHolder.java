package com.example.l2cache.jwt;



import com.example.l2cache.Context;
import com.fasterxml.jackson.core.JsonProcessingException;
import org.springframework.stereotype.Component;

@Component
public class ContextHolder {

    private static final ThreadLocal<Context> userContext = new ThreadLocal<>();
    private final JwtTokenInfoParser jwtTokenInfoParser;

    public ContextHolder(JwtTokenInfoParser jwtTokenInfoParser) {
        this.jwtTokenInfoParser = jwtTokenInfoParser;
    }

    public  void put(String jwt){
        try {
            JwtTokenInfo jwtTokenInfo = jwtTokenInfoParser.getJwtTokenInfo(jwt);
            String username =  jwtTokenInfo.getPreferredUsername() ;
            Context context = new Context(username,jwt,"apiVersion");
            userContext.set(context);
        } catch (JsonProcessingException e) {
            e.printStackTrace();
        }
    }

    public static Context get(){
        return userContext.get();
    }
    public static void clearContext() {
        userContext.remove();
    }
}

