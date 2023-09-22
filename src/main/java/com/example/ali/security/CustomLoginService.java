package com.example.ali.security;

import com.example.ali.entity.Seller;
import com.example.ali.entity.User;
import com.example.ali.repository.SellerRepository;
import com.example.ali.repository.UserRepository;
import java.util.Optional;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class CustomLoginService implements UserDetailsService {

    private final UserRepository userRepository;
    private final SellerRepository sellerRepository;

//    private final UserDetailsServiceImpl userDetailsService;
//    private final SellerDetailsServiceImpl sellerDetailsService;
//
//    public CustomLoginService(UserDetailsServiceImpl userDetailsService, SellerDetailsServiceImpl sellerDetailsService) {
//        this.userDetailsService = userDetailsService;
//        this.sellerDetailsService = sellerDetailsService;
//    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {

        Optional<User> user = userRepository.findByUsername(username);
        if(user.isPresent())
            return user.map(UserDetailsImpl::new).orElseThrow(()
                -> new IllegalArgumentException("잘못된 인증 객체"));
        else {
            Seller seller = sellerRepository.findByUsername(username).orElseThrow(()
            -> new IllegalArgumentException("잘못된 인증 객체"));
            return new SellerDetailsImpl(seller);

        }
    }
}
