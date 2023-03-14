package com.ram.controller;

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
import org.springframework.web.bind.annotation.SessionAttributes;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.ram.entity.Socio;
import com.ram.service.SocioService;

import jakarta.validation.Valid;

@Controller
@SessionAttributes("socios")
@RequestMapping(value = "/socios")
public class SocioController {

	@Autowired
	private SocioService socioServ;
	
	//Listado socios
	
	@GetMapping("")
	public String listado(Model model)
	{
		
		List<Socio> socios = socioServ.listar();
		boolean editable = false;
		
		model.addAttribute("socios", socios);
		model.addAttribute("editable", editable);

		return "socios";
	}
	
	//Crear socio
	
	@GetMapping("/alta")
	public String alta(Model model)
	{
		boolean editable = true;

		model.addAttribute("editable", editable);
		model.addAttribute("socio", new Socio());
		
		return "socios";
	}
	
	//Editar socio
	
	@GetMapping("/editar/{id}")
	public String editar(@PathVariable("id") Integer id,
		  	             Model model){
		
		boolean editable = true;
		Optional<Socio> socio = socioServ.seleccionar(id);
		
		model.addAttribute("socio", socio.get());
		model.addAttribute("editable", editable);
		
		return "socios";
	}
	
	//Guardar socio
	
	@PostMapping("/guardar")
	public String guardar(@Valid Socio socio, 
			              BindingResult result, 
			              Model model, RedirectAttributes redirect)
	{
		
		if (result.hasErrors()){
			    
			boolean editable = true;

			model.addAttribute("editable", editable);
			model.addAttribute("alerta", "Error al guardar el socio. Revisa el formulario.");
				 
			return "socios";
	
		}else {
			
			socioServ.guardar(socio);
			redirect.addFlashAttribute("mensaje", "Socio guardado.");
		}
		
		return "redirect:/socios";
	}

	//Borrar socio
	
	@GetMapping("/borrar/{id}")
	public String borrar(@PathVariable("id") Integer id, 
			             Model model, RedirectAttributes redirect)
	{
		Optional<Socio> socio = socioServ.seleccionar(id);
		
		if(socio.get().getLibros().isEmpty()) {
			
			redirect.addFlashAttribute("mensaje", "Socio borrado.");
			
			socioServ.borrar(socio);
			
		}else {
			
			redirect.addFlashAttribute("alerta", "El socio no se puede borrar, tiene libros prestados.");
			
		}
		
		return "redirect:/socios";
	}
}
