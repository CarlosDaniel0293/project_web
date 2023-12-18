package com.miempresa.controlador;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.Collections;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.miempresa.interfaceServicio.ILibroServicio;
import com.miempresa.interfaceServicio.IPrestamoServicio;
import com.miempresa.modelo.Libro;
import com.miempresa.modelo.Prestamo;
import com.miempresa.servicio.LibroServicio;


import jakarta.validation.Valid;

@Controller
@RequestMapping
public class controlador {
	
	//Busqueda			
	
	
	@GetMapping("/buscar")
    public String buscarLibrosPorTitulo(@RequestParam String titulo, Model model) {
        // Lógica para buscar libros y agregar al modelo
        List<Libro> librosEncontrados = servicio.findByNombreContaining(titulo);
        model.addAttribute("libros", librosEncontrados);
        return "libros";  // o el nombre de tu vista Thymeleaf
    }
	
	
	//Index
	
	@GetMapping("/index")
    public String mostrarIndex() {
        return "index";
    }
	
	
	//Libros
	
	
	@Autowired
	private ILibroServicio servicio;
	
	@GetMapping("/listarLibros")
	public String listarLibros(Model model) {
		List<Libro> libros = servicio.listarLibros();
		model.addAttribute("libros", libros);
		
		// Agrega el número total de libros
        model.addAttribute("totalLibros", libros.size());

        // Agrega el número total de libros por género
        Map<String, Long> totalLibrosPorGenero = servicio.obtenerTotalLibrosPorGenero();
        model.addAttribute("totalLibrosPorGenero", totalLibrosPorGenero);

		
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
		
		// Agrega el número total de préstamos
	    model.addAttribute("totalPrestamos", prestamos.size());

	    // Agrega la cantidad de préstamos por libro
	    ResponseEntity<Map<String, Integer>> prestamosPorLibroResponse = obtenerPrestamosPorLibro();
	    Map<String, Integer> prestamosPorLibro = prestamosPorLibroResponse.getBody();
	    model.addAttribute("prestamosPorLibro", prestamosPorLibro);
		
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
	public String guardarPrestamo(@ModelAttribute("prestamo") @Valid Prestamo prestamo, BindingResult result,
			RedirectAttributes redirectAttributes, Model model) {
	    
		// Verificar si hay errores de validación
        if (result.hasErrors()) {
            // Agregar mensajes de error al modelo
            model.addAttribute("libros", servicio.listarLibros()); // Necesario para volver a cargar la lista de libros
            return "agregarPrestamo";
        }
		
		// Obtén el libro seleccionado por su ID
	    Libro libroSeleccionado = servicio.obtenerLibroPorId(prestamo.getLibroId()).orElse(null);

	    // Asigna el libro al prestamo
	    prestamo.setLibro(libroSeleccionado);

	    // Lógica para guardar el préstamo
	    servicio1.guardarPrestamo(prestamo);
	    
	    redirectAttributes.addFlashAttribute("mensaje", "Préstamo registrado correctamente.");

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
	
	@GetMapping("/prestamosPorLibro")
	public ResponseEntity<Map<String, Integer>> obtenerPrestamosPorLibro() {
	    List<Prestamo> prestamos = servicio1.listarPrestamos();

	    // Crea un mapa para almacenar la cantidad de préstamos por libro
	    Map<String, Integer> prestamosPorLibro = new HashMap<>();

	    // Itera sobre los préstamos y cuenta la cantidad por libro
	    for (Prestamo prestamo : prestamos) {
	        String nombreLibro = prestamo.getLibro().getNombre();

	        // Verifica si el libro ya está en el mapa, si no, inicializa el contador en 1
	        prestamosPorLibro.put(nombreLibro, prestamosPorLibro.getOrDefault(nombreLibro, 0) + 1);
	    }

	    return ResponseEntity.ok(prestamosPorLibro);
	}
	
	 @GetMapping("/mostrarGrafico")
	    public String mostrarGrafico(Model model) {
	        // Hacer una llamada al nuevo endpoint que obtiene la cantidad de préstamos por libro
	        ResponseEntity<Map<String, Integer>> response = obtenerPrestamosPorLibro();

	        // Obtener el cuerpo de la respuesta (cantidad de préstamos por libro)
	        Map<String, Integer> prestamosPorLibro = response.getBody();

	        // Agregar los datos al modelo para ser utilizados en la página HTML
	        model.addAttribute("prestamosPorLibro", prestamosPorLibro);

	        return "mostrarGrafico";
	    }

	    // Método que obtiene la cantidad de préstamos por libro desde el backend
	    private ResponseEntity<Map<String, Integer>> obtenerPrestamosPorLibro1() {
	        // Lógica para hacer una llamada al nuevo endpoint que has creado en el controlador
	        // Utiliza RestTemplate, WebClient u otra librería para hacer la solicitud HTTP

	        // Ejemplo de cómo podrías hacer la llamada usando RestTemplate (requiere configuración adicional)
	        RestTemplate restTemplate = new RestTemplate();
	        String url = "http://localhost:8080/prestamosPorLibro";
	        ResponseEntity<Map<String, Integer>> response = restTemplate.exchange(
	                url,
	                HttpMethod.GET,
	                null,
	                new ParameterizedTypeReference<Map<String, Integer>>() {
	                });

	        return response;
	   }
}

