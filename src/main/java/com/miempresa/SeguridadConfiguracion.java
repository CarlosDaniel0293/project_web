package com.miempresa;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;

import com.miempresa.servicio.UsuarioServicio;

@Configuration
@EnableWebSecurity
public class SeguridadConfiguracion {
	
	@Autowired
	private UsuarioServicio userDet;
	
	@Autowired
	private BCryptPasswordEncoder bycryp;
	
	@Bean
	public BCryptPasswordEncoder passEncoder() {
		BCryptPasswordEncoder bcpe = new BCryptPasswordEncoder();
		return bcpe;
	}
	 
	protected void configure(AuthenticationManagerBuilder auth) throws Exception {
		auth.userDetailsService(userDet).passwordEncoder(bycryp);
	}
	
	@Bean
	public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
		http.authorizeHttpRequests(
				(authz) -> authz
				.requestMatchers("/", "/listarLibros").permitAll()
				.requestMatchers("/agregarLibro").hasRole("ADMIN")
				.requestMatchers("/mostrarLibro").hasRole("ADMIN")
				.requestMatchers("/guardarLibro").hasRole("ADMIN")
				.requestMatchers("/editarLibro").hasRole("ADMIN")
				.requestMatchers("/eliminarLibro/*").hasRole("ADMIN")
				
				.requestMatchers("/", "/listarPrestamos").permitAll()
				.requestMatchers("/agregarPrestamo").hasRole("ADMIN")
				.requestMatchers("/mostrarPrestamo").hasRole("ADMIN")
				.requestMatchers("/guardarPrestamo").hasRole("ADMIN")
				.requestMatchers("/editarPrestamo").hasRole("ADMIN")
				.requestMatchers("/eliminarPrestamo/*").hasRole("ADMIN")
				
				.anyRequest().authenticated())
				.formLogin((form) -> form.permitAll())
				.logout((logout) -> logout.permitAll()
						);
		return http.build();
	}
}
