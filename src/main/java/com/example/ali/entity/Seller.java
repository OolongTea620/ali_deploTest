package com.example.ali.entity;

import com.example.ali.dto.SellerSignupRequestDto;
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
    private LocalDateTime deletedAt; // db filed : delete_at


    public Seller(SellerSignupRequestDto requestDto, String password) {
        this.username = requestDto.getUsername();
        this.storeName = requestDto.getStoreName();
        this.info = requestDto.getInfo();

        this.password = password;
    }
}
