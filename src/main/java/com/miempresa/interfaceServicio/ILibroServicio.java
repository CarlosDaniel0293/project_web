package com.miempresa.interfaceServicio;

import java.util.List;
import java.util.Map;
import java.util.Optional;

import org.springframework.data.jpa.repository.Query;

import com.miempresa.modelo.Libro;

public interface ILibroServicio {
	public List<Libro> listarLibros();
	public Optional<Libro> obtenerLibroPorId(int id);
	public int guardarLibro(Libro p);
	public void borrarLibro(int id);
	Map<String, Long> obtenerTotalLibrosPorGenero();
	List<String> obtenerTodosLosGeneros();
	List<Libro> findByNombreContaining(String titulo);
}