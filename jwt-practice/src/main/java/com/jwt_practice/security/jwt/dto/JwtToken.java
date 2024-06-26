package com.jwt_practice.security.jwt.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

// 클라이언트에 보낼 토큰을 위한 DTO
@Builder
@Data
@AllArgsConstructor
public class JwtToken {
    private String grantType;
    private String accessToken;
    private String refreshToken;
}
