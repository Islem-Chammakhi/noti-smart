package com.isimm.suivi_note.controllers;

import com.isimm.suivi_note.dto.AuthOtpLoginReq;
import com.isimm.suivi_note.dto.OtpDTO;
import com.isimm.suivi_note.dto.UserDTO;
import com.isimm.suivi_note.utils.HttpCookieManager;
import org.springframework.web.bind.annotation.*;

import com.isimm.suivi_note.services.auth.JWTService;
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


@RestController
@RequestMapping("/api/auth")
@RequiredArgsConstructor
public class AuthController {
    private final AuthenticationService authenticationService;
    private final JWTService jwtService;

    @PostMapping("/login")
    //TODO: Why does this returns cookie with refresh token
    public ResponseEntity<String> login(@Valid @RequestBody final AuthenticationRequest req, HttpServletResponse res) {
        boolean isAuthenticated= this.authenticationService.login(req);
        if(isAuthenticated){
            res.addCookie(
                HttpCookieManager.generateCookie(
                        "cin",
                        req.getCin(),
                        60*5,
                        true
                )
            );

            res.addCookie(
                    HttpCookieManager.generateCookie(
                            "passwd",
                            req.getPassword(),
                            60*5,
                            true
                    )
            );
        }

        return ResponseEntity.ok().build();
    }

    @PostMapping("/otp")
    public ResponseEntity<Void> loginOTP(@Valid @RequestBody OtpDTO otp, HttpServletRequest req, HttpServletResponse response) {
        String cin = jwtService.getTextFromCookie(req, "cin");
        String passwd = jwtService.getTextFromCookie(req, "passwd");

        System.out.println(cin+ " "+passwd+" "+otp.value());

        AuthenticationResponse authResponse= this.authenticationService.loginWithOTP(cin, passwd, otp.value());


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

        //TODO: Remove cin and passwd cookies

        return ResponseEntity.ok().build();
    }

    @PostMapping("/register")
    public ResponseEntity<Void> register(@Valid @RequestBody final RegistrationRequest req) {   
        this.authenticationService.register(req);
        return ResponseEntity.status(HttpStatus.CREATED).build();
    }

    @PostMapping("/refresh")
    public ResponseEntity<Void> refreshAccessToken(HttpServletRequest req,HttpServletResponse response) {
        String refreshToken = jwtService.getTextFromCookie(req,"refreshToken");
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

    @GetMapping("/me")
    public ResponseEntity<UserDTO> verifyUser(HttpServletRequest req){
        String token = jwtService.getTextFromCookie(req, "accessToken");
        UserDTO user = authenticationService.extractUserFromToken(token);
        return ResponseEntity.ok(user);
    }
}
