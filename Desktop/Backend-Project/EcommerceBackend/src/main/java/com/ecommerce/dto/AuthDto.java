package com.ecommerce.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.*;

public class AuthDto {

    @Getter @Setter
    public static class RegisterRequest {
        @NotBlank @Email
        private String email;

        @NotBlank @Size(min = 6, max = 100)
        private String password;

        @NotBlank
        private String firstName;

        @NotBlank
        private String lastName;

        private String phone;
    }

    @Getter @Setter
    public static class LoginRequest {
        @NotBlank @Email
        private String email;

        @NotBlank
        private String password;
    }

    @Getter @Setter @AllArgsConstructor @NoArgsConstructor @Builder
    public static class AuthResponse {
        private String token;
        private String type = "Bearer";
        private Long id;
        private String email;
        private String firstName;
        private String lastName;
        private String role;
    }
}
