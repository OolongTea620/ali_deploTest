package com.example.ali.entity;

import com.example.ali.dto.ProductRequestDto;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.SQLDelete;
import org.hibernate.annotations.Where;

import java.time.LocalDateTime;

@Entity
@Getter
@NoArgsConstructor
//@SQLDelete 제대로 작동하는지 확인 필요
@SQLDelete(sql = "UPDATE product SET deleted_at = CURRENT_TIMESTAMP, productStatus = 'DISCONTINUED' where id = ?")
@Where(clause = "deleted_at IS NULL")
public class Product {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long Id;

    @Column(nullable = false)
    private String productName;

    @Column(nullable = false)
    private Long price;

    @Column(nullable = false)
    private String info;

    @Column(nullable = false)
    private String imageUrl;

    @Enumerated(value = EnumType.STRING)
    private ProductStatus productStatus = ProductStatus.AVAILABLE;

    @Column
    private LocalDateTime deletedAt;

    @ManyToOne
    @JoinColumn(name = "seller_id")
    private Seller seller;

    @OneToOne(mappedBy = "product", cascade = CascadeType.ALL)
    private ProductStock productStock;

    public Product(ProductRequestDto requestDto, Seller seller, ProductStock productStock) {
        this.productName = requestDto.getProductName();
        this.price = requestDto.getPrice();
        this.info = requestDto.getInfo();
        this.imageUrl = requestDto.getImageUrl();
        this.seller = seller;
        this.productStock = productStock;
    }

    //product 수정
    public void update(ProductRequestDto requestDto) {
        this.productName = requestDto.getProductName();
        this.price = requestDto.getPrice();
        this.info = requestDto.getInfo();
        this.imageUrl = requestDto.getImageUrl();
        this.productStatus = requestDto.getProductStatus();
        this.productStock.update(requestDto.getStock());

    }

}
