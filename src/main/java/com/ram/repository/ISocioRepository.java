package com.ram.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.ram.entity.Socio;

@Repository
public interface ISocioRepository extends JpaRepository <Socio, Integer>{

}
