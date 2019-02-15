package com.example.kafka.repository;


import org.springframework.data.jpa.repository.JpaRepository;

import com.example.kafka.model.Location;

import java.util.List;

public interface LocationRepository extends JpaRepository<Location, Integer> {

}
