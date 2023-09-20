package com.example.ali.entity;

import com.example.ali.dto.UserSignupRequestDto;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Getter
@NoArgsConstructor
public class User extends Timestamped{
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String username;

    @Column(nullable = false)
    private String password;

    @Column(nullable = false)
    private String email;

    @OneToOne(mappedBy = "user", cascade = CascadeType.ALL)
    private UserWallet userWallet;

    public User(UserSignupRequestDto requestDto, String password, UserWallet userWallet) {
        this.username = requestDto.getUsername();
        this.email = requestDto.getEmail();
        this.password = password;
        this.userWallet = userWallet;
        userWallet.setUser(this);
    }


}
