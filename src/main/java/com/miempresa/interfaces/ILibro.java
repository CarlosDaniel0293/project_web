package com.miempresa.interfaces;

import java.util.List;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import com.miempresa.modelo.Libro;

@Repository
public interface ILibro extends CrudRepository<Libro, Integer>{
	List<Libro> findByNombreContaining(String titulo);
}
