package com.ahl.alquran.repo;

import com.ahl.alquran.entity.City;
import org.springframework.data.repository.CrudRepository;

public interface CityRepository extends CrudRepository<City, Long> {


    City findByName(String name);
}
