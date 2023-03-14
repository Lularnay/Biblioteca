package com.ram.service;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Base64;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.ram.entity.Libro;
import com.ram.repository.ILibroRepository;

@Service
public class LibroService {

	@Autowired
	private ILibroRepository libroRepo;
	
	//Listar
	
	public List<Libro> listar() {
		return libroRepo.findAll();
	}
	
	//Guardar
	
	public Libro guardar(Libro libro) {
		return libroRepo.save(libro);
	}
	
	//Obtener dos ultimos libros subidos
	
	public List<Libro> novedades(){
		return libroRepo.findTop2ByOrderByFechaAdquisicionDesc();
	}
	
	//Obtener un listado los libros prestados
	
	public List<Libro> prestamos(){
		return libroRepo.getAllWithSocioByOrderByReferenciaDes();
	}
	
	//Seleccionar
	
	public Optional<Libro> seleccionar(Integer referencia) {
		return libroRepo.findById(referencia);
	}
	
	//Foto
	
	public String foto(String nombreFoto) {
		
    	Path directorioRecursos = Paths.get("src/main/resources/static/images");
	    String rootPath = directorioRecursos.toFile().getAbsolutePath();
	    String imagenBase64 = null;
	    
	    try {
	        Path rutaCompleta = Paths.get(rootPath + "/" + nombreFoto);
	        byte[] bytes = Files.readAllBytes(rutaCompleta);
	        imagenBase64 = Base64.getEncoder().encodeToString(bytes);
	        
	    } catch (IOException e) {
	        e.printStackTrace();
	    }
		return imagenBase64;
	}
}
