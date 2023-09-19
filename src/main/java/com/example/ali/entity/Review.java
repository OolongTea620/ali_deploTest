package com.example.ali.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.SQLDelete;
import org.hibernate.annotations.Where;

import java.time.LocalDateTime;


@Entity
@Getter
@NoArgsConstructor
@SQLDelete(sql = "UPDATE review SET deleted_at = CURRENT_TIMESTAMP where id = ?")
@Where(clause = "deleted_at IS NULL")
public class Review {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String comment;

    @Column(nullable = false, updatable = false)
    private Integer rating;

    @Column
    private LocalDateTime deletedAt;

    @ManyToOne
    @JoinColumn(name = "order_id")
    private Orders orders;

}
