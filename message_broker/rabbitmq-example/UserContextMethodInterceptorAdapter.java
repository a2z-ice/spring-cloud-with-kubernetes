package com.ba.cms.admin.gateway.amqp;
import com.ba.cms.admin.filter.ContextHolder;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.aopalliance.intercept.MethodInterceptor;
import org.aopalliance.intercept.MethodInvocation;
import org.springframework.amqp.core.Message;

import java.io.IOException;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Slf4j
@AllArgsConstructor
public class UserContextMethodInterceptorAdapter implements MethodInterceptor {

    final private ObjectMapper objectMapper;
    final private ContextHolder contextHolder;


    @Override
    public Object invoke(MethodInvocation invocation) throws Throwable {
        log.info("Set context holder: +++++++++++++++++++++++++++++++++++++++++" );
        setUserContext(invocation.getArguments());
        Object proceed = invocation.proceed();
        ContextHolder.clearContext();
        log.info("Clear context holder: --------------------------------------------" );
        return proceed;
    }


    public void setUserContext(Object...args){
        Arrays.asList(args).stream().filter(arg -> arg instanceof Message)
                .map(arg -> (Message) arg)
                .findFirst()
                .ifPresent(this::setJwtTokenToContextHolder);
    }

    private void setJwtTokenToContextHolder(Message message){
        try {
//            Message clonedMessage = new Message(message.getBody(), message.getMessageProperties());

            JsonNode jsonNode = objectMapper.readTree(message.getBody());
            contextHolder.put(jsonNode.path("metaData").path("token").asText());
        } catch (IOException e) {
            log.warn("Exception to convert message to JsonNode");
        }
    }



}
