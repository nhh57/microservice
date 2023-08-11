package com.example.identityservice.controller;

import com.example.identityservice.dto.request.UsersRequest;
import com.example.identityservice.dto.response.BaseResponse;
import com.example.identityservice.service.UsersService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/auth")
@RequiredArgsConstructor
@Slf4j
public class UsersController {
    private final UsersService usersSer;
    private final AuthenticationManager authenticationManager;

    @RequestMapping(value = "/register", method = RequestMethod.POST, produces = {
            MediaType.APPLICATION_JSON_VALUE})
    public ResponseEntity<BaseResponse> Register(@RequestBody UsersRequest request){
        log.info("START Register");
        BaseResponse response = new BaseResponse();
        String messages = usersSer.saveUsers(request);
        response.setData(messages);
        log.info("END Register");
        return new ResponseEntity<BaseResponse>(response, HttpStatus.CREATED);
    }

    @RequestMapping(value = "/token", method = RequestMethod.POST, produces = {
            MediaType.APPLICATION_JSON_VALUE})
    public ResponseEntity<BaseResponse> getToken(@RequestBody UsersRequest usersRequest){
        BaseResponse response = new BaseResponse();
        Authentication authenticate = authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(usersRequest.getName(), usersRequest.getPassword()));
       if(authenticate.isAuthenticated()){
           response.setData(usersSer.generateToken(usersRequest.getName()));
           return new ResponseEntity<BaseResponse>(response, HttpStatus.OK);
       }
       else {
           throw new RuntimeException("invalid access");
       }

    }

    @RequestMapping(value = "/validate", method = RequestMethod.GET, produces = {
            MediaType.APPLICATION_JSON_VALUE})
    public ResponseEntity<BaseResponse> validate(@RequestParam("token") String token){
        BaseResponse response = new BaseResponse();
        usersSer.validateToken(token);
        response.setData("Token is valid");
        return new ResponseEntity<BaseResponse>(response, HttpStatus.OK);
    }
}
