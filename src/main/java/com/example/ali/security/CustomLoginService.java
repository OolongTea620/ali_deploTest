package com.example.ali.security;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
public class CustomLoginService implements UserDetailsService {

    private final UserDetailsServiceImpl userDetailsService;
    private final SellerDetailsServiceImpl sellerDetailsService;

    @Autowired
    public CustomLoginService(UserDetailsServiceImpl userDetailsService, SellerDetailsServiceImpl sellerDetailsService) {
        this.userDetailsService = userDetailsService;
        this.sellerDetailsService = sellerDetailsService;
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        try {
            return userDetailsService.loadUserByUsername(username);
        } catch (UsernameNotFoundException e) {
            return sellerDetailsService.loadUserByUsername(username);
        }
    }
}
