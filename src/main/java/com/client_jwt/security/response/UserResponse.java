package com.client_jwt.security.response;

import com.client_jwt.security.enums.PerfilEnum;
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
public class UserResponse {

    private String email;

    @Enumerated(EnumType.STRING)
    private PerfilEnum perfil;
}
