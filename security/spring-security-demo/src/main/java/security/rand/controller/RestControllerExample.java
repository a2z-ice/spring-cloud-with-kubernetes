package security.rand.controller;

import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import security.rand.config.ContextHolder;

@Slf4j
@CorporateV1API
public class RestControllerExample {
    @GetMapping("/test/{id}")
    public String getId( @PathVariable("id") String id){
        return "your id is : " + id;
    }
    @GetMapping("/admin/{id}")
    public String admin( @PathVariable("id") String id){
        return "your id is : " + id;
    }

    @GetMapping("/user/{id}")
    public String user( @PathVariable("id") String id){
        log.info("corporate id----------------->:{}", ContextHolder.getCorporateId());
        return "your id is : " + id + " and the corporateId is : " + ContextHolder.getCorporateId()
                + " and service tag is : " + ContextHolder.getServiceTag();
    }
    @GetMapping("/account/{id}")
    public String account( @PathVariable("id") String id){
        return "your id is : " + id;
    }

    @GetMapping("/all/{id}")
    public String all( @PathVariable("id") String id){
        return "your id is : " + id;
    }



}
