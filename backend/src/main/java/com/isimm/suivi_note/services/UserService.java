package com.isimm.suivi_note.services;

import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import com.isimm.suivi_note.repositories.UserRepo;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class UserService implements UserDetailsService {
    
    private final UserRepo userRepository;

    @Override
    public UserDetails loadUserByUsername(String userCin) throws UsernameNotFoundException {
        return userRepository.findByCin(userCin)
                .orElseThrow(() -> new UsernameNotFoundException("User not found"));
    }
    

}
