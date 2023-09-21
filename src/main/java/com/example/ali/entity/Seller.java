package com.example.ali.entity;

import com.example.ali.dto.SellerSignupRequestDto;
import com.example.ali.dto.StoreRequestDto;
import com.example.ali.dto.UserSignupRequestDto;
import com.example.ali.repository.SellerWalletRepository;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.SQLDelete;
import org.hibernate.annotations.Where;

import java.time.LocalDateTime;

@Entity
@Getter
@NoArgsConstructor

@SQLDelete(sql = "UPDATE seller SET deleted_at = CURRENT_TIMESTAMP where id = ?")
@Where(clause = "deleted_at IS NULL")
public class Seller {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String username;

    @Column(nullable = false)
    private String password;

    @Column(nullable = false)
    private String storeName;

    @Column(nullable = false)
    private String info;

    @Column
    private LocalDateTime deletedAt;

    @OneToOne(fetch = FetchType.LAZY, mappedBy = "seller")
    private SellerWallet sellerWallet;

    public Seller(SellerSignupRequestDto requestDto, String password, SellerWallet sellerWallet) {
        this.username = requestDto.getUsername();
        this.password = password;
        this.storeName = requestDto.getStoreName();
        this.info = requestDto.getInfo();
        this.sellerWallet = sellerWallet;
        sellerWallet.setSeller(this);
    }

    public Seller(String store1, String info1) {
        this.storeName = store1;
        this.info = info1;
    }

    public void update(StoreRequestDto requestDto) {
        this.storeName = requestDto.getStoreName();
        this.info = requestDto.getInfo();
    }
}