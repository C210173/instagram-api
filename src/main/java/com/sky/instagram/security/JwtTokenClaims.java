package com.sky.instagram.security;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class JwtTokenClaims {
    private String username;
}
