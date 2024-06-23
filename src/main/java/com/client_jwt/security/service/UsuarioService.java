package com.client_jwt.security.service;

import com.client_jwt.security.entities.Usuario;

import java.util.Optional;

public interface UsuarioService {
    Optional<Usuario> findByEmail(String email);

    Usuario save(Usuario usuario);
}
