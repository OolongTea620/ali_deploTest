package com.example.ali.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Getter
@NoArgsConstructor
public class UserWallet extends Timestamped{
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long Id;

    @Column
    private Long point;

    @OneToOne
    @JoinColumn(name = "user_id")
    private User user;

    public void changePoint(Long price) {
        this.point -= price;
    }
}
