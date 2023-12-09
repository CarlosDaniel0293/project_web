package com.miempresa.modelo;
import java.util.List;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

@Entity
@Table(name = "Libros")
public class Libro {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private int id;
	
	@NotBlank(message = "el título no puede estar vacío")
	@Column(name = "nombre")
	private String nombre;
	
	@NotBlank(message = "El autor no puede estar vacío")
	@Column(name = "autor")
	private String autor;
	
	@NotBlank(message = "La editorial no puede estar vacía")
	@Column(name = "editorial")
	private String editorial;
	
	@Column(name = "anio_publicacion")
	private int anioPublicacion;
	
	@Column(name = "genero")
	private String genero;
	
	@OneToMany(mappedBy = "libro", cascade = CascadeType.ALL)
    private List<Prestamo> prestamos;
	
	// Constructor vacío 
    public Libro() {
    }

    // Constructor con parámetros
    public Libro(String nombre, String autor, String editorial, int anioPublicacion, String genero) {
        this.nombre = nombre;
        this.autor = autor;
        this.editorial = editorial;
        this.anioPublicacion = anioPublicacion;
        this.genero = genero;
    }

    // Getters y setters

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getAutor() {
        return autor;
    }

    public void setAutor(String autor) {
        this.autor = autor;
    }

    public String getEditorial() {
        return editorial;
    }

    public void setEditorial(String editorial) {
        this.editorial = editorial;
    }

    public int getAnioPublicacion() {
        return anioPublicacion;
    }

    public void setAnioPublicacion(int anioPublicacion) {
        this.anioPublicacion = anioPublicacion;
    }

    public String getGenero() {
        return genero;
    }

    public void setGenero(String genero) {
        this.genero = genero;
    }
    
    public List<Prestamo> getPrestamos() {
        return prestamos;
    }

    public void setPrestamos(List<Prestamo> prestamos) {
        this.prestamos = prestamos;
    }
}

