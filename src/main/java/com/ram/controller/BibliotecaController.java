package com.ram.controller;

import java.io.IOException;
import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import com.ram.entity.Libro;
import com.ram.entity.Socio;
import com.ram.service.LibroService;
import com.ram.service.SocioService;

import jakarta.annotation.PostConstruct;

@Controller
public class BibliotecaController {
	
	@Autowired
	LibroService libroServ;
	
	@Autowired
	SocioService socioServ;
	
	//Crear 5 libros y 5 socios al iniciar.
	
	@PostConstruct
	public String rellenar() throws IOException{
		
	    List<Libro> libros = libroServ.listar();
	    List<Socio> socios = socioServ.listar();

	    if (libros.isEmpty() && socios.isEmpty()) {
	    	 
		    //Libros
		
		    Libro elPrincipito = new Libro();
		    elPrincipito.setTitulo("El principito");
		    elPrincipito.setAutor("Antoine de Saint-Exupéry");
		    elPrincipito.setSinopsis("Cuenta la historia de un piloto que se encuentra perdido en el desierto del Sahara y se encuentra con un pequeño príncipe de otro planeta.");
		    elPrincipito.setFechaAdquisicion(new Date(2023-02-25));
		 	elPrincipito.setFotoPortada(libroServ.foto("principito.jpg"));		
		    libroServ.guardar(elPrincipito);
		
		    Libro laDivinaComedia = new Libro();
		    laDivinaComedia.setTitulo("La Divina comedia");
		    laDivinaComedia.setAutor("Dante Alighieri");
		    laDivinaComedia.setSinopsis("Cuenta el viaje del protagonista a través del Infierno, el Purgatorio y el Paraíso.");
		    laDivinaComedia.setFechaAdquisicion(new Date(2023-02-25));
		    laDivinaComedia.setFotoPortada(libroServ.foto("ladivinacomedia.jpg"));	
		    libroServ.guardar(laDivinaComedia);
		
		    Libro rayuela = new Libro();
		    rayuela.setTitulo("Rayuela");
		    rayuela.setAutor("Julio Cortázar");
		    rayuela.setSinopsis("La historia sigue a Horacio Oliveira, un intelectual argentino que vive en París y está obsesionado con una mujer llamada La Maga.");
		    rayuela.setFechaAdquisicion(new Date(2023-02-25));
		    rayuela.setFotoPortada(libroServ.foto("rayuela.jpg"));
		    libroServ.guardar(rayuela);
		
		    Libro laDamaDelAlba = new Libro();
		    laDamaDelAlba.setTitulo("La dama del alba");
		    laDamaDelAlba.setAutor("Alejandro Casona");
		    laDamaDelAlba.setSinopsis("La llegada de un forastero a la casa de la familia desencadena una serie de eventos que obligan a los personajes a enfrentar sus miedos y secretos.");
		    laDamaDelAlba.setFechaAdquisicion(new Date(2023-02-25));
		    laDamaDelAlba.setFotoPortada(libroServ.foto("ladamadelalba.jpg"));
		    libroServ.guardar(laDamaDelAlba);
		
		    Libro elQuijote = new Libro();
		    elQuijote.setTitulo("El ingenioso hidalgo Don Quijote de la Mancha");
		    elQuijote.setAutor("Miguel de Cervantes Saavedra");
		    elQuijote.setSinopsis("Un hombre llamado Alonso Quijano que se vuelve loco después de leer demasiados libros de caballería y decide convertirse en un caballero andante, llamándose a sí mismo Don Quijote de la Mancha.");
		    elQuijote.setFechaAdquisicion(new Date(2023-02-25));
		    elQuijote.setFotoPortada(libroServ.foto("donquijote.jpg"));
		    libroServ.guardar(elQuijote);
		
		    //Socios:
		
		    Socio MariaG = new Socio(0, "Maria", "Garcia Fernandez");
		    socioServ.guardar(MariaG);
		
		    Socio AnaC = new Socio(0, "Ana", "Cuesta Martin");
		    socioServ.guardar(AnaC);
		
		    Socio FelipeD = new Socio(0, "Felipe", "Diaz Agustin");
		    socioServ.guardar(FelipeD);
		
		    Socio JuanaP = new Socio(0, "Juana", "Perez Gutierrez");
		    socioServ.guardar(JuanaP);
		
		    Socio FernandoF = new Socio(0, "Fernando", "Fuentes Blanco");
		    socioServ.guardar(FernandoF);
		
		}else {}
		
		return "inicio";
	}

	//Inicio
	
	@GetMapping("/")
	public String inicio(Model model) {
		
		List<Libro> novedades = libroServ.novedades();
		
		model.addAttribute("libros", novedades);
		
		return "inicio";
	}
	
	//Prestamos
	
	@GetMapping("/prestamos")
	public String prestamos(Model model) {
		
		List<Libro> prestamos = libroServ.prestamos();
		boolean editable = false;
		
		model.addAttribute("editable", editable);
		model.addAttribute("libros", prestamos);
		
		return "prestamos";
	}
	
}
