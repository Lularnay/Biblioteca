package com.ram.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.ram.entity.Libro;

@Repository
public interface ILibroRepository extends JpaRepository<Libro, Integer>{

	public List<Libro> findTop2ByOrderByFechaAdquisicionDesc();
	
	@Query("select l from com.ram.entity.Libro l JOIN l.socio")
	public List<Libro> getAllWithSocioByOrderByReferenciaDes();
	
}
