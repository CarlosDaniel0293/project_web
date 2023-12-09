package com.miempresa.servicio;

import java.util.List;
import java.util.Optional;

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

}
