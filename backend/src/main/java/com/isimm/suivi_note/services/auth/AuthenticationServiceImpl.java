package com.isimm.suivi_note.services.auth;

import com.isimm.suivi_note.brevo.entities.BrevoOTPTemplate;
import com.isimm.suivi_note.dto.UserDTO;
import com.isimm.suivi_note.exceptions.InvalidCredentials;
import com.isimm.suivi_note.exceptions.InvalidOtpException;
import com.isimm.suivi_note.services.EmailService;
import com.isimm.suivi_note.utils.OtpGenerator;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.isimm.suivi_note.enums.Role;
import com.isimm.suivi_note.models.Filiere;
import com.isimm.suivi_note.models.Admin;
import com.isimm.suivi_note.models.Etudiant;
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

import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;
import java.time.temporal.TemporalUnit;

@Slf4j
@Service
@RequiredArgsConstructor
public class AuthenticationServiceImpl implements AuthenticationService  {

    private final AuthenticationManager authenticationManager;
    private final JWTService jwtService;
    private final UserRepo userRepository;
    private final UserIsimmRepo userIsimmRepo;
    private final PasswordEncoder passwordEncoder;
    private final EmailService emailService;

    @Override
    // This checks if the user is logged in, if so, returns true so we can go to the next step (send OTP)
    public boolean login(final AuthenticationRequest request) {
        final Authentication auth = this.authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(request.getCin(), request.getPassword()));

        User user = (User) auth.getPrincipal();
        if(auth.isAuthenticated()){
            String otpCode = OtpGenerator.generateOtp();
            emailService.sendEmail(
                    user.getEmail(),
                    new BrevoOTPTemplate(otpCode)
            );
            System.out.println("User is logged in = "+auth.isAuthenticated()+", otp="+otpCode+", sent email to "+user.getEmail());

            user.setOtp(passwordEncoder.encode(otpCode));
            user.setOtpExpiry(LocalDateTime.now().plusMinutes(5));
            userRepository.save(user);
        }

        return auth.isAuthenticated();

    }

    @Override
    public AuthenticationResponse loginWithOTP(String cin, String passwd, String otp) {
        if(cin==null || passwd==null || cin.trim().isEmpty() || passwd.trim().isEmpty()){
            throw new InvalidCredentials("Invalid username and/or password");
        }
        final Authentication auth = this.authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(cin, passwd));
        System.out.println("les données envoyés sont : "+cin+" "+passwd+" "+otp);

        if(!auth.isAuthenticated())
            throw new InvalidCredentials("Invalid username or/and password");

        User user = (User) auth.getPrincipal(); // This generated the problem

        if(LocalDateTime.now().isAfter(user.getOtpExpiry()))
            throw new InvalidOtpException("La date de l'otp est expiré, Veuillez réessayer");

        if(!passwordEncoder.matches(otp, user.getOtp())){
            throw new InvalidOtpException("L'otp saisie est invalide");
        }
        if(passwordEncoder.matches(otp, user.getOtp()) && auth.isAuthenticated()){
            //TODO: Remove OTP from DB after validation

            final String accessToken = this.jwtService.generateAccessToken(user.getUsername());
            final String refreshToken = this.jwtService.generateRefreshToken(user.getUsername());
            System.out.println("accessToken is :"+accessToken);
            final String tokenType="Bearer";
            UserDTO loggedUser=extractUserFromToken(accessToken);
            return AuthenticationResponse.builder()
                    .accessToken(accessToken)
                    .refreshToken(refreshToken)
                    .tokenType(tokenType)
                    .user(loggedUser)
                    .build();
        }
        return null;

    }

    @Override
    @Transactional
    // TODO: If the email inside UserIsimm isn't viable, add OTP here
    public void register(final RegistrationRequest request) {
        Filiere f =new Filiere();
        f.setId("ING2_INFO");
        checkUserCin(request.getCin());
        UserIsimm userIsimm=checkUserIsimm(request.getCin());
        checkUserPasswords(request.getPassword(),request.getConfirmPassword());
        User user = Etudiant.builder()
                .cin(userIsimm.getCin())
                .firstName(userIsimm.getFirstName())
                .lastName(userIsimm.getLastName())
                .email(userIsimm.getEmail())
                .password(passwordEncoder.encode(request.getPassword()))
                .role(Role.STUDENT)
                .filiere(f)
                .build();
        userRepository.save(user);
        
    }



    private UserIsimm checkUserIsimm(String cin) {
        return userIsimmRepo.findByCin(cin).orElseThrow(() -> new EntityNotFoundException("user does not exist in bd isimm"));
    }

    @Override
    public AuthenticationResponse refreshToken(final RefreshRequest request) {
        /*if (request.getRefreshToken().trim().isEmpty())
            throw new Invalid*/
        final String newAccessToken = jwtService.refreshAccessToken(request.getRefreshToken());
        final String token_type="Bearer";
        return AuthenticationResponse.builder()
                                     .accessToken(newAccessToken)
                                     .refreshToken(request.getRefreshToken())
                                     .tokenType(token_type)
                                     .build();
    }

    @Override
    public UserDTO extractUserFromToken(String token) {
        String cin = jwtService.extractUserCin(token);
        User u = userRepository.findByCin(cin).orElseThrow(()->new EntityNotFoundException("User cin="+cin+" doens't exist"));
        return new UserDTO(
                u.getCin(),
                u.getFirstName() +" "+ u.getLastName(),
                u.getRole().name()
        );
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
/*final User user =(User) auth.getPrincipal();
            final String accessToken = this.jwtService.generateAccessToken(user.getUsername());
            final String refreshToken = this.jwtService.generateRefreshToken(user.getUsername());
            System.out.println("accessToken is :"+accessToken);
            final String tokenType="Bearer";*/