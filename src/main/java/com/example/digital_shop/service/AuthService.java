package com.example.digital_shop.service;

import com.example.digital_shop.exception.DataNotFoundException;
import com.example.digital_shop.entity.user.UserEntity;
import com.example.digital_shop.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@RequiredArgsConstructor
@Service
public class AuthService implements UserDetailsService {
    private final UserRepository userRepository;
    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
        System.out.println(email);
        return  userRepository.findUserEntityByEmailEquals(email)
                .orElseThrow(()->new DataNotFoundException("User not found"));
    }
}
