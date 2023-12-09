package com.miempresa.controlador;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.miempresa.interfaceServicio.ILibroServicio;
import com.miempresa.interfaceServicio.IPrestamoServicio;
import com.miempresa.modelo.Libro;
import com.miempresa.modelo.Prestamo;

import jakarta.validation.Valid;

@Controller
@RequestMapping
public class controlador {
	
	
	//Libros
	
	
	@Autowired
	private ILibroServicio servicio;
	
	@GetMapping("/listarLibros")
	public String listarLibros(Model model) {
		List<Libro> libros = servicio.listarLibros();
		model.addAttribute("libros", libros);
		return "libros";
	}
	
	@GetMapping("/agregarLibro")
    public String agregarLibro(@ModelAttribute("libro") Libro libro, Model model) {
        model.addAttribute("libro", libro);
        return "agregarLibro";
    }
	
	@PostMapping("/guardarLibro")
	public String guardarLibro(@ModelAttribute("libro") @Valid Libro form, 
			BindingResult errores, Model model) {
	    if (errores.hasErrors()) {
	    	return "agregarLibro";
	    }
	    servicio.guardarLibro(form);  
	    return "redirect:/listarLibros";
	}
	
	@GetMapping("/editarLibro/{id}")
	public String editarLibro(@PathVariable int id, RedirectAttributes atributos) {
		Optional<Libro> libro = servicio.obtenerLibroPorId(id);
		atributos.addFlashAttribute("libro", libro);
		return "redirect:/mostrarLibro";
	}
	
	@GetMapping("/mostrarLibro")
	public String mostrarLibro(@ModelAttribute("libro") Libro p, Model model) {
	return "agregarLibro";
	}
	
	@GetMapping("/eliminarLibro/{id}")
	public String eliminarLibro(@PathVariable int id) {
		servicio.borrarLibro(id);
		return "redirect:/listarLibros";
	}
	
	
	//Prestamos
	
	
	@Autowired
	private IPrestamoServicio servicio1;
	
	@GetMapping("/listarPrestamos")
	public String listarPrestamos(Model model) {
		List<Prestamo> prestamos = servicio1.listarPrestamos();
		model.addAttribute("prestamos", prestamos);
		return "prestamos";
	}
	
	@GetMapping("/agregarPrestamo")
	public String agregarPrestamo(Model model) {
	    model.addAttribute("prestamo", new Prestamo());
	    
	    // Agrega la lista de libros al modelo
	    List<Libro> libros = servicio.listarLibros();
	    model.addAttribute("libros", libros);
	    
	    return "agregarPrestamo";
	}
	
	@PostMapping("/guardarPrestamo")
	public String guardarPrestamo(@ModelAttribute("prestamo") Prestamo prestamo) {
	    // Obtén el libro seleccionado por su ID
	    Libro libroSeleccionado = servicio.obtenerLibroPorId(prestamo.getLibroId()).orElse(null);

	    // Asigna el libro al prestamo
	    prestamo.setLibro(libroSeleccionado);

	    // Lógica para guardar el préstamo
	    servicio1.guardarPrestamo(prestamo);

	    return "redirect:/listarPrestamos";
	}
	
	@GetMapping("/editarPrestamo/{id}")
	public String editarPrestamo(@PathVariable int id, RedirectAttributes atributos) {
		Optional<Prestamo> prestamo = servicio1.obtenerPrestamoPorId(id);
		atributos.addFlashAttribute("prestamo", prestamo);
		return "redirect:/mostrarPrestamo";
	}
	
	@GetMapping("/mostrarPrestamo")
	public String mostrarPrestamo(@ModelAttribute("prestamo") Prestamo p, Model model) {
	    // Obtén la lista de libros
	    List<Libro> libros = servicio.listarLibros();

	    // Agrega la lista de libros al modelo
	    model.addAttribute("libros", libros);
	    // Agrega el objeto Prestamo al modelo
	    model.addAttribute("prestamo", p);

	    return "agregarPrestamo";
	}
	
	@GetMapping("/eliminarPrestamo/{id}")
	public String eliminarPrestamo(@PathVariable int id) {
		servicio1.borrarPrestamo(id);
		return "redirect:/listarPrestamos";
	}
}
