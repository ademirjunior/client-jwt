package com.client_jwt.security.resource;

import com.client_jwt.security.entities.Usuario;
import com.client_jwt.security.request.UserJwtRequest;
import com.client_jwt.security.request.UserRequest;
import com.client_jwt.security.response.UserJwtResponse;
import com.client_jwt.security.response.UserResponse;
import com.client_jwt.security.service.UsuarioService;
import com.client_jwt.security.service.impl.UserDetailsServiceImpl;
import com.client_jwt.security.utils.JwtUtil;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

@Log4j2
@RestController
@RequestMapping("/usuario")
@CrossOrigin(origins = "*")
@RequiredArgsConstructor
public class UsuarioResource {

    private final UsuarioService usuarioService;
    private final PasswordEncoder passwordEncoder;

    @Autowired
    private final UserDetailsServiceImpl userDetailsServiceImpl;

    @Autowired
    private JwtUtil jwtUtil;

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

    @PostMapping("/autenticacao")
    @ResponseStatus(HttpStatus.OK)
    public UserJwtResponse getAutenticacao(@RequestBody UserJwtRequest usuarioJwtRequest) {
        try {
            UserDetails userDetails = userDetailsServiceImpl.autenticar(usuarioJwtRequest);
            String token = jwtUtil.obterToken(usuarioJwtRequest);
            return UserJwtResponse.builder()
                    .email(userDetails.getUsername())
                    .token(token)
                    .build();
        } catch (Exception e) {
            throw new ResponseStatusException(HttpStatus.UNAUTHORIZED, e.getMessage());
        }
    }
}
