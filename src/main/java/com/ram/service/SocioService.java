package com.ram.service;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.ram.entity.Socio;
import com.ram.repository.ISocioRepository;

@Service
public class SocioService {

	@Autowired
	private ISocioRepository socioRepo;
	
	//Listar
	
	public List<Socio> listar() {
		return socioRepo.findAll();
	}
	
	//Guardar
	
	public Socio guardar(Socio socio) {
		return socioRepo.save(socio);
	}

	//Seleccionar
	
	public Optional<Socio> seleccionar(Integer id) {
		return socioRepo.findById(id);
	}

	//Borrar 
	
	public void borrar(Optional<Socio> socio) {
		
		try {
			if(socio.get() != null) {
				
				socioRepo.delete(socio.get());
			
			}else {}
			
		}catch(Exception ex) {
			System.out.println("Error al borrar el socio" + socio.get().getId() + ".");
		}
		
	}
	
}
