package com.example.identityservice.dto.request;

import lombok.*;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
//@Builder
public class UsersRequest {
    private String name;
    private String password;
}
