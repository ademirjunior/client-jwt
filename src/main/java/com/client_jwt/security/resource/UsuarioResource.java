package com.client_jwt.security.resource;

import com.client_jwt.security.entities.Usuario;
import com.client_jwt.security.request.UserRequest;
import com.client_jwt.security.response.UserResponse;
import com.client_jwt.security.service.UsuarioService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.BeanUtils;
import org.springframework.http.HttpStatus;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;

@Log4j2
@RestController
@RequestMapping("/usuario")
@CrossOrigin(origins = "*")
@RequiredArgsConstructor
public class UsuarioResource {

    private final UsuarioService usuarioService;
    private final PasswordEncoder passwordEncoder;

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public UserResponse save(@RequestBody @Valid UserRequest userRequest) {
        Usuario usuario = new Usuario();
        BeanUtils.copyProperties(userRequest, usuario);
        usuario.setPassword(passwordEncoder.encode(userRequest.getPassword()));
        usuarioService.save(usuario);
        log.info("Usuario cadastrado com sucesso {}", usuario);
        UserResponse userResponse = new UserResponse();
        BeanUtils.copyProperties(usuario, userResponse);
        return userResponse;
    }
}
