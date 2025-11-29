package com.lpdev.financemanagerapi.security.controllers;

import com.lpdev.financemanagerapi.security.DTO.LoginDTO;
import com.lpdev.financemanagerapi.security.DTO.LoginResponseDTO;
import com.lpdev.financemanagerapi.security.DTO.RegisterDTO;
import com.lpdev.financemanagerapi.security.DTO.RegisterResponseDTO;
import com.lpdev.financemanagerapi.security.services.UserService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.URI;

@RestController
@RequestMapping(value = "api/auth")
public class UserController {

    private final UserService userService;

    public UserController(UserService userService) {
        this.userService = userService;
    }

    @PostMapping(value = "/register")
    public ResponseEntity<RegisterResponseDTO> register(@RequestBody RegisterDTO dto){
        RegisterResponseDTO response = userService.userRegister(dto);
        URI uri = ServletUriComponentsBuilder
                .fromCurrentRequest()
                .path("email")
                .buildAndExpand(response.email())
                .toUri();
        return ResponseEntity.ok().body(response);
    }

    @PostMapping(value = "/login")
    public ResponseEntity<LoginResponseDTO> login(@RequestBody LoginDTO dto){
        LoginResponseDTO response = userService.userLogin(dto);
        URI uri = ServletUriComponentsBuilder
                .fromCurrentRequest()
                .path("email")
                .buildAndExpand(response.email())
                .toUri();
        return ResponseEntity.ok().body(response);
    }

}
