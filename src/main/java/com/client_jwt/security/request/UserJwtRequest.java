package com.client_jwt.security.request;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class UserJwtRequest {

    private String email;

    private String password;
}
