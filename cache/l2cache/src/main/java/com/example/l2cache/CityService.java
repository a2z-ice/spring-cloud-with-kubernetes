package com.example.l2cache;

import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.StreamSupport;

@AllArgsConstructor
@Service
public class CityService {
    private final CityRepo cityRepo;
    public List<City> getAllCity(){
        List<City> list = StreamSupport.stream(cityRepo.findAll().spliterator(), false).map(city -> city).toList();
        return list;
    }
    public City getCityById(Integer id){
        return cityRepo.findById(id).get();
    }

    public City getCityByCityName(String name) {
        return cityRepo.findByCityName(name).get();
    }
}
