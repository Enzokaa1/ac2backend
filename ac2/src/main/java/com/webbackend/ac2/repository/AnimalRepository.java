package com.webbackend.ac2.repository;

import com.webbackend.ac2.model.Animal;
import org.springframework.data.jpa.repository.JpaRepository;

public interface AnimalRepository extends JpaRepository<Animal, Long> {
}
