package com.miempresa.servicio;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import com.miempresa.interfaceServicio.IUsuario;
import com.miempresa.modelo.Usuario;

@Service
public class UsuarioServicio implements UserDetailsService {

    @Autowired
    private IUsuario repo;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        Usuario user = repo.findByNombre(username);

        List<GrantedAuthority> rol = new ArrayList<>();
        rol.add(new SimpleGrantedAuthority(user.getRol()));

        UserDetails userDet = new User(user.getNombre(), user.getClave(), rol);

        return userDet;
    }

}
