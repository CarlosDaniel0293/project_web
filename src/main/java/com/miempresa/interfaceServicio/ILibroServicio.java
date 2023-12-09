package com.miempresa.interfaceServicio;

import java.util.List;
import java.util.Optional;

import com.miempresa.modelo.Libro;

public interface ILibroServicio {
	public List<Libro> listarLibros();
	public Optional<Libro> obtenerLibroPorId(int id);
	public int guardarLibro(Libro p);
	public void borrarLibro(int id);
}
