package com.example.l2cache;

import jakarta.persistence.EntityManagerFactory;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import java.util.Collection;
import java.util.Collections;
import java.util.List;

@AllArgsConstructor
@RestController
public class CityController {
    private final CityService cityService;
    private final EntityManagerFactory entityManagerFactory;
    @GetMapping("/city")
    List<City> getAllCity(){
        return cityService.getAllCity();
    }
    @GetMapping("/city/{id}")
    City getCity(@PathVariable("id") Integer id){
        return cityService.getCityById(id);
    }
    @GetMapping("/city/{name}/name")
    City getCity(@PathVariable("name") String name){
        return cityService.getCityByCityName(name);
    }
    @GetMapping("/cache/clean")
    ResponseEntity<?> clear(){
        entityManagerFactory.getCache().unwrap(org.hibernate.Cache.class).evictAllRegions();
        return new ResponseEntity<>("Cache cleared", HttpStatus.OK);
    }
}
