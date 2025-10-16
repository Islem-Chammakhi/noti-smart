package com.isimm.suivi_note.controllers;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.isimm.suivi_note.services.JWTService;
import com.isimm.suivi_note.utils.auth.AuthenticationService;
import com.isimm.suivi_note.utils.auth.request.AuthenticationRequest;
import com.isimm.suivi_note.utils.auth.request.RefreshRequest;
import com.isimm.suivi_note.utils.auth.request.RegistrationRequest;
import com.isimm.suivi_note.utils.auth.response.AuthenticationResponse;

import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;


@RestController
@RequestMapping("/auth")
@RequiredArgsConstructor
public class AuthController {
    private final AuthenticationService authenticationService;
    private final JWTService jwtService;

    @PostMapping("/login")
    public ResponseEntity<Void> login(@Valid @RequestBody final AuthenticationRequest req,HttpServletResponse response) {
        AuthenticationResponse authResponse= this.authenticationService.login(req);
        Cookie accessTokenCookie = new Cookie("accessToken", authResponse.getAccessToken());
        accessTokenCookie.setHttpOnly(true);     
        // accessTokenCookie.setSecure(true);   HTTPS
        accessTokenCookie.setPath("/");    
        accessTokenCookie.setMaxAge(15*60);

        Cookie refreshTokenCookie = new Cookie("refreshToken", authResponse.getRefreshToken());
        refreshTokenCookie.setHttpOnly(true);
        // refreshTokenCookie.setSecure(true);
        refreshTokenCookie.setPath("/");
        refreshTokenCookie.setMaxAge(7 * 24 * 60 * 60);
        response.addCookie(accessTokenCookie);
        response.addCookie(refreshTokenCookie);

        return ResponseEntity.ok().build();
    }

    @PostMapping("/register")
    public ResponseEntity<Void> register(@Valid @RequestBody final RegistrationRequest req) {   
        this.authenticationService.register(req);
        return ResponseEntity.status(HttpStatus.CREATED).build();
    }

    @PostMapping("/refresh")
    public ResponseEntity<Void> refreshAccessToken(HttpServletRequest req,HttpServletResponse response) {
        String refreshToken = jwtService.getTokenFromCookie(req,"refreshToken");
        RefreshRequest refreshRequest = new RefreshRequest(refreshToken);
        AuthenticationResponse authResponse= this.authenticationService.refreshToken(refreshRequest);
        if(refreshToken==null){
            return ResponseEntity.status(HttpServletResponse.SC_UNAUTHORIZED).build();
        }
        Cookie accessTokenCookie = new Cookie("accessToken", authResponse.getAccessToken());
        accessTokenCookie.setHttpOnly(true);     
        // accessTokenCookie.setSecure(true);   HTTPS
        accessTokenCookie.setPath("/");    
        accessTokenCookie.setMaxAge(15*60);

        response.addCookie(accessTokenCookie);

        return ResponseEntity.ok().build();
    }
    

}
