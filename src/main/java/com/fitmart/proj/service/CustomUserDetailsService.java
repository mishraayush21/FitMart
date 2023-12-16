package com.fitmart.proj.service;

import com.fitmart.proj.model.CustomUserDetails;
import com.fitmart.proj.model.User;
import com.fitmart.proj.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.Optional;
@Service
public class CustomUserDetailsService implements UserDetailsService {
    @Autowired
    UserRepository userRepository;
    @Override
    public UserDetails loadUserByUsername(String s) throws UsernameNotFoundException {
        Optional<User> user = userRepository.findUserByEmail(s);
        user.orElseThrow(() -> new UsernameNotFoundException("User not found"));
        return user.map(CustomUserDetails::new).get();
    }
}
