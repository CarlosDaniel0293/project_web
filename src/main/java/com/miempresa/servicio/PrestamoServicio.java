package com.miempresa.servicio;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.miempresa.interfaceServicio.*;
import com.miempresa.interfaces.ILibro;
import com.miempresa.interfaces.IPrestamo;
import com.miempresa.modelo.Libro;
import com.miempresa.modelo.Prestamo;

@Service
public class PrestamoServicio implements IPrestamoServicio {

	@Autowired
	private ILibro repo1;
	
	@Autowired
	private IPrestamo repo;
	
	@Override
	public List<Prestamo> listarPrestamos() {
		// TODO Auto-generated method stub
		return (List<Prestamo>)repo.findAll();
	}

	@Override
	public Optional<Prestamo> obtenerPrestamoPorId(int id) {
		// TODO Auto-generated method stub
		return repo.findById(id);
	}

	@Override
	public int guardarPrestamo(Prestamo p) {
		// Verificar si el libro ya está persistido
        if (p.getLibro() != null && p.getLibro().getId() == 0) {
            // El libro aún no está persistido, guárdalo primero
            Libro libroGuardado = repo1.save(p.getLibro());
            p.setLibro(libroGuardado);
        }

        // Guardar el Prestamo después
        Prestamo prestamoGuardado = repo.save(p);
        return prestamoGuardado.getId();
    }
	
	@Override
	public void borrarPrestamo(int id) {
		// TODO Auto-generated method stub
		repo.deleteById(id);
	}

}
