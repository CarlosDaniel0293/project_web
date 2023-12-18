package com.miempresa.servicio;

import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.miempresa.interfaceServicio.*;
import com.miempresa.interfaces.ILibro;
import com.miempresa.modelo.Libro;

@Service
public class LibroServicio implements ILibroServicio{
	
	@Autowired
	private ILibro repo;
	
	@Override
	public List<Libro> listarLibros() {
		// TODO Auto-generated method stub
		return (List<Libro>)repo.findAll();
	}

	@Override
	public Optional<Libro> obtenerLibroPorId(int id) {
		// TODO Auto-generated method stub
		return repo.findById(id);
	}

	@Override
	public int guardarLibro(Libro p) {
		// TODO Auto-generated method stub
		Libro li = repo.save(p);
		if(!li.equals(null)) {
			return 1;
		}
		return 0;
	}

	@Override
	public void borrarLibro(int id) {
		// TODO Auto-generated method stub
		repo.deleteById(id);
	}
	
	@Override
	public Map<String, Long> obtenerTotalLibrosPorGenero() {
	    List<Libro> libros = listarLibros();

	    // Usa Java Streams para agrupar los libros por género y contarlos
	    Map<String, Long> totalPorGenero = libros.stream()
	            .collect(Collectors.groupingBy(Libro::getGenero, Collectors.counting()));

	    // Si un género no tiene libros, se agrega con un total de 0
	    obtenerTodosLosGeneros().forEach(genero -> totalPorGenero.putIfAbsent(genero, 0L));

	    return totalPorGenero;
	}

	@Override
	public List<String> obtenerTodosLosGeneros() {
	    List<Libro> libros = listarLibros();

	    // Usa Java Streams para obtener todos los géneros únicos
	    List<String> todosLosGeneros = libros.stream()
	            .map(Libro::getGenero)
	            .distinct()
	            .collect(Collectors.toList());

	    return todosLosGeneros;
	}
	
	@Override
    public List<Libro> findByNombreContaining(String titulo) {
        // Lógica para buscar libros por título en el repositorio
        // Utiliza el método definido en la interfaz del repositorio
        return repo.findByNombreContaining(titulo);
    }

}
