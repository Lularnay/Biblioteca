package com.ram.controller;

import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.SessionAttributes;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.ram.entity.Libro;
import com.ram.entity.Socio;
import com.ram.service.LibroService;
import com.ram.service.SocioService;

import jakarta.validation.Valid;

@Controller
@SessionAttributes("libros")
@RequestMapping(value = "/catalogo")
public class LibroController {
	
	@Autowired
	private LibroService libroServ;
	
	@Autowired
	private SocioService socioServ;;
	
	//Listado libros
	
	@GetMapping("")
	public String listado(Model model)
	{
		
		boolean editable = false;
		
		model.addAttribute("libros", libroServ.listar());
		model.addAttribute("editable", editable);

		return "catalogo";
	}
	
	//Crear libro
	
	@GetMapping("/alta")
	public String alta(Model model)
	{
		boolean editable = true;
		
		model.addAttribute("editable", editable);
		model.addAttribute("libro", new Libro());
		
		return "catalogo";
	}
	
	//Editar libro
	
	@GetMapping("/editar/{referencia}")
	public String editar(@PathVariable("referencia") Integer referencia,
		  	             Model model){
		
		boolean editable = true;
		Optional<Libro> libro = libroServ.seleccionar(referencia);
		
		if (libro.isPresent()) {
			
			model.addAttribute("libro", libro.get());
			model.addAttribute("editable", editable);
			
		}else {
			System.out.println("Error, el libro con la referencia:" + referencia + " no esta en la base de datos.");
		}
		
		return "catalogo";
	}
	
	//Prestar libro
	
	@GetMapping("/prestar/{referencia}")
	public String prestar(@PathVariable("referencia") Integer referencia,
		  	             Model model){
		
		boolean editable = true;
		Optional<Libro> libro = libroServ.seleccionar(referencia);
		List<Socio> socios = socioServ.listar();

		if (libro.isPresent()) {
			
			if(libro.get().getSocio() != null) {
			
                return "redirect:/catalogo";
			
			}else{
				
				model.addAttribute("libro", libro.get());
				model.addAttribute("editable", editable);
				model.addAttribute("socios", socios);
				model.addAttribute("libros", libroServ.prestamos());
			}
			
		}else {
			System.out.println("Error, el libro con la referencia:" + referencia + " no esta en la base de datos.");
		}
		
	   return "prestamos";
	}
	
	//Devolver libro
	
	@GetMapping("/devolver/{referencia}")
	public String devolver(@PathVariable("referencia") Integer referencia,
	                       Model model, RedirectAttributes redirect){
		
	    Optional<Libro> libroOp = libroServ.seleccionar(referencia);
	    
	    if (libroOp.isPresent()) {
	    	
	    	if(libroOp.get().getSocio() == null) {
                return "redirect:/catalogo";
			
			}else{
				
	    		Optional<Socio> socioOp = socioServ.seleccionar(libroOp.get().getSocio().getId());

	    		if(socioOp.isPresent()) {
	    			
	    		    Socio socio = socioOp.get();
	    		    Libro libro = libroOp.get();
	    		    
	    		    libro.setSocio(null);
	    		    socio.getLibros().remove(libro);
	    		    System.out.println(socio.getLibros());
	    		    
		            socioServ.guardar(socio);
		            libroServ.guardar(libro);
		            
		            model.addAttribute("libro", libro);
		            model.addAttribute("libros", libroServ.prestamos());
		    		redirect.addFlashAttribute("mensaje", "¡Guardado! // Libro: " + libro.getTitulo() + ". // Sin prestamo.");
	    		    
	    		}else {}
			}
	    	
	    } else {
	        System.out.println("Error, el libro con la referencia:" + referencia + " no esta en la base de datos.");
	    }

	    return "redirect:/catalogo";
	}
	
	//Guardar libro

	@PostMapping("/guardar")
	public String guardar(@Valid Libro libro, 
						BindingResult result, 
						@RequestParam(name = "file", required= false) MultipartFile file,						 
						Model model, RedirectAttributes redirect)
	{
		
		 if (!file.isEmpty()){
				
			   try{
				  libro.setFotoPortada(file);
				  
			   }catch (IOException e){
				e.printStackTrace();
			   }
			
		    }else{
			
			   Optional<Libro> libroBd = libroServ.seleccionar(libro.getReferencia() != null ? libro.getReferencia() : 0 );
			
			   if (libroBd.isPresent()){
				
				  libro.setFotoPortada(libroBd.get().getFotoPortada());
				
			   }else{
				
				  String fileName = "static/images/libroVacio.png";
                  ClassLoader classLoader = getClass().getClassLoader();
				  URL resource = classLoader.getResource(fileName);
				  File file2;
				  
				  try{
					 file2 = new File(resource.toURI());
					 libro.setFotoPortada(file2);
					 
				  }catch (Exception e){
					e.printStackTrace();
				  }
			   }
		    }
		
		if  (result.hasErrors()){
			
			boolean editable = true;

			model.addAttribute("editable", editable);
			model.addAttribute("alerta", "Error al guardar el libro. Revisa el formulario.");
			
			return "catalogo";
			
		}else {

		libroServ.guardar(libro);
		
		model.addAttribute("libros", libroServ.listar());
		
		if(libro.getSocio() == null){
			redirect.addFlashAttribute("mensaje", "¡Guardado! // Libro: " + libro.getTitulo() + ". // Sin prestamo.");
			
		}else {
			redirect.addFlashAttribute("mensaje", "¡Guardado! // Libro: " + libro.getTitulo() + ". // Prestado: " + libro.getSocio().getNombre() + " " + libro.getSocio().getApellidos() + ".");
		}
	}
		return "redirect:/catalogo";
	}
}
