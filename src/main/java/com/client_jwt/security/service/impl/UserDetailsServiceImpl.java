package com.client_jwt.security.service.impl;

import com.client_jwt.security.entities.Usuario;
import com.client_jwt.security.enums.PerfilEnum;
import com.client_jwt.security.repository.UsuarioRepository;
import com.client_jwt.security.request.UserJwtRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class UserDetailsServiceImpl implements UserDetailsService {

    @Autowired
    private UsuarioRepository usuarioRepository;

    private PasswordEncoder passwordEncoder;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        Usuario usuario = usuarioRepository.findByEmail(username)
                .orElseThrow((() -> new UsernameNotFoundException("Email não encontrado: " + username)));
        return User.builder()
                .username(usuario.getEmail())
                .password(usuario.getPassword())
                .authorities(mapToGrantedAuthorities(usuario.getPerfil()))
                .build();
    }

    private static List<GrantedAuthority> mapToGrantedAuthorities(PerfilEnum perfilEnum){
        List<GrantedAuthority> authorities = new ArrayList<>();
        authorities.add(new SimpleGrantedAuthority(perfilEnum.toString()));
        return authorities;
    }

    public UserDetails autenticar(UserJwtRequest usuarioJwtRequest) throws Exception {
        UserDetails userDetails =  loadUserByUsername(usuarioJwtRequest.getEmail());
        boolean senhaValida = passwordEncoder.matches(usuarioJwtRequest.getPassword(), userDetails.getPassword());

        if(senhaValida){
            return userDetails;
        }
        throw new Exception("Senha Invalida.");
    }

    @Autowired
    public void setPasswordEncoder(@Lazy PasswordEncoder passwordEncoder) {
        this.passwordEncoder = passwordEncoder;
    }
}
