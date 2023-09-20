package com.example.ali.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.ColumnDefault;

@Entity
@Getter
@Setter
@NoArgsConstructor
public class UserWallet extends Timestamped{
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long Id;

    @Column
    @ColumnDefault("1000000")
    private Long point;

    @OneToOne
    @JoinColumn(name = "user_id")
    private User user;

    public void changePoint(Long price) {
        this.point -= price;
    }
}
