package com.client_jwt.security.request;

import com.client_jwt.security.enums.PerfilEnum;
import jakarta.persistence.Column;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.validation.constraints.NotEmpty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class UserRequest {

    @NotEmpty(message = "Campo email é obrigatório!")
    private String email;

    @NotEmpty(message = "Campo password é obrigatório!")
    private String password;

    @Enumerated(EnumType.STRING)
    private PerfilEnum perfil;
}
