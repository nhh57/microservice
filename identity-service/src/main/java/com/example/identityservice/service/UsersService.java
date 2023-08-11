package com.example.identityservice.service;

import com.example.identityservice.dto.request.UsersRequest;
import com.example.identityservice.dto.response.UsersResponse;
import com.example.identityservice.model.Users;
import com.example.identityservice.repository.UsersRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
@RequiredArgsConstructor
public class UsersService {

    private final UsersRepository usersRepo;

    private  final PasswordEncoder passwordEncoder;

    private  final JwtService jwtService;
    public String saveUsers(UsersRequest usersRequest) {
        Users users = Users.builder()
                .name(usersRequest.getName())
                .password(passwordEncoder.encode(usersRequest.getPassword()))
                .build();
        usersRepo.save(users);
        return "User added to the system";
    }

    public String generateToken(String userName){
        return jwtService.generateToken(userName);
    }
    public void validateToken(String token){
        jwtService.validateToken(token);
    }
}
