package com.example.marketwebsite.controller;

import com.example.marketwebsite.dto.AuthDto;
import com.example.marketwebsite.dto.RegistrationDto;
import com.example.marketwebsite.dto.profile.ProfileDetailDto;
import com.example.marketwebsite.repository.ProfileRepository;
import com.example.marketwebsite.service.AuthorizationService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
@RestController
@RequestMapping("/login")
public class AuthorizationController {

    private static final Logger logger = LoggerFactory.getLogger(AuthorizationController.class);
    @Autowired
    private AuthorizationService service;
    @Autowired
    private ProfileRepository profileRepository;

    @PutMapping("/authorization")
    public ResponseEntity<?> authorization(@Valid @RequestBody AuthDto dto) {
        logger.info("Authorization: {}" + dto);
        ProfileDetailDto result = service.authorization(dto);
        return ResponseEntity.ok(result);
    }

    @PostMapping("/registration")
    public ResponseEntity<?> registration(@Valid @RequestBody RegistrationDto dto){
        logger.info("Authorization: {}" + dto);
        String result = service.registration(dto);
        return ResponseEntity.ok(result);
    }

    @GetMapping("/verification/{jwt}")
    public ResponseEntity<?> registration(@PathVariable("jwt") String jwt){
        String result = service.verification(jwt);
        return ResponseEntity.ok(result);
    }
}
