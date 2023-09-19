package com.example.ali.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Getter
@NoArgsConstructor
public class SellerWallet extends Timestamped{
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long Id;

    @Column
    private Long point;

    @OneToOne
    @JoinColumn(name = "seller_id")
    private Seller seller;
}
