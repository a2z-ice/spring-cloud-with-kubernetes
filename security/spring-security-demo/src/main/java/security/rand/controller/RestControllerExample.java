package security.rand.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class RestControllerExample {
    @GetMapping("/test/{id}")
    public String getId(@PathVariable("id") String id){
        return "the value of id:" + id;
    }
}
