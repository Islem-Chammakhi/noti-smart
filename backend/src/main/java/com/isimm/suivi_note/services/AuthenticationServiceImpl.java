package com.isimm.suivi_note.services;

import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.isimm.suivi_note.models.Admin;
import com.isimm.suivi_note.models.Role;
import com.isimm.suivi_note.models.Student;
import com.isimm.suivi_note.models.User;
import com.isimm.suivi_note.models.UserIsimm;
import com.isimm.suivi_note.repositories.UserIsimmRepo;
import com.isimm.suivi_note.repositories.UserRepo;
import com.isimm.suivi_note.utils.auth.AuthenticationService;
import com.isimm.suivi_note.utils.auth.request.AuthenticationRequest;
import com.isimm.suivi_note.utils.auth.request.RefreshRequest;
import com.isimm.suivi_note.utils.auth.request.RegistrationRequest;
import com.isimm.suivi_note.utils.auth.response.AuthenticationResponse;

import jakarta.persistence.EntityNotFoundException;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor

public class AuthenticationServiceImpl implements AuthenticationService  {

    private final AuthenticationManager authenticationManager;
    private final JWTService jwtService;
    private final UserRepo userRepository;
    private final UserIsimmRepo userIsimmRepo;
    private final PasswordEncoder passwordEncoder;

    @Override
    public AuthenticationResponse login(final AuthenticationRequest request) {
        try{
        final Authentication auth = this.authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(request.getCin(), request.getPassword()));

        final User user =(User) auth.getPrincipal();
        final String accessToken = this.jwtService.generateAccessToken(user.getUsername());
        final String refreshToken = this.jwtService.generateRefreshToken(user.getUsername());
        System.out.println("accessToken is :"+accessToken);
        final String tokenType="Bearer";

        return AuthenticationResponse.builder()
                                     .accessToken(accessToken)
                                     .refreshToken(refreshToken)
                                     .tokenType(tokenType)
                                     .build();
        }catch(Exception e){
            e.printStackTrace();
            System.out.println(e.getMessage());
            return null;
        }
    }

    @Override
    @Transactional
    public void register(final RegistrationRequest request) {
        checkUserCin(request.getCin());
        UserIsimm userIsimm=checkUserIsimm(request.getCin());
        checkUserPasswords(request.getPassword(),request.getConfirmPassword());
        User user =Admin.builder()
                .cin(userIsimm.getCin())
                .firstName(userIsimm.getFirstName())
                .lastName(userIsimm.getLastName())
                .email(userIsimm.getEmail())
                .password(passwordEncoder.encode(request.getPassword()))
                .role(Role.ADMIN)
                .build();
        userRepository.save(user);
        
    }



    private UserIsimm checkUserIsimm(String cin) {
        return userIsimmRepo.findByCin(cin).orElseThrow(() -> new EntityNotFoundException("user does not exist in bd isimm"));
    }

    @Override
    public AuthenticationResponse refreshToken(final RefreshRequest request) {
        final String newAccessToken = jwtService.refreshAccessToken(request.getRefreshToken());
        final String token_type="Bearer";
        return AuthenticationResponse.builder()
                                     .accessToken(newAccessToken)
                                     .refreshToken(request.getRefreshToken())
                                     .tokenType(token_type)
                                     .build();
    }

    private void checkUserPasswords(String password, String confirmPassword) {
        if(password == null ||!confirmPassword.equals(password) ){
             throw new RuntimeException("password mismatch");
        }
    }

    private void checkUserCin(String cin) {
        

        final boolean cinExists= userRepository.existsByCin(cin);
        if(cinExists){
            throw new RuntimeException("cin already exists");
        }
    }

}
