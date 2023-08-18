package com.example.identityservice.dto.request;

import lombok.*;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class UsersRequest {
    private String name;
    private String email;
    private String password;
}
