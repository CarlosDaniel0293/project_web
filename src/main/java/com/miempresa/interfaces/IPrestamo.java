package com.miempresa.interfaces;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import com.miempresa.modelo.Prestamo;

@Repository
public interface IPrestamo extends CrudRepository<Prestamo, Integer>{

}
