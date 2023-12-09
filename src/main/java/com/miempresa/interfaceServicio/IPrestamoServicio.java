package com.miempresa.interfaceServicio;

import java.util.List;
import java.util.Optional;

import com.miempresa.modelo.Prestamo;

public interface IPrestamoServicio {
    public List<Prestamo> listarPrestamos();
    public Optional<Prestamo> obtenerPrestamoPorId(int id);
    public int guardarPrestamo(Prestamo p);
    public void borrarPrestamo(int id);
}
