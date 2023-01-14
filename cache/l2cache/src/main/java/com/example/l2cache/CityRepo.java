package com.example.l2cache;

import jakarta.persistence.QueryHint;
import org.springframework.data.jpa.repository.QueryHints;
import org.springframework.data.repository.CrudRepository;

import java.util.Optional;

public interface CityRepo extends CrudRepository<City, Integer> {

    @QueryHints(@QueryHint(name = "org.hibernate.cacheable", value = "true"))
    Optional<City> findByCityName(String cityName);
}
