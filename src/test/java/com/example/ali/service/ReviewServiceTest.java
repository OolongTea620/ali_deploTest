package com.example.ali.service;

import com.example.ali.dto.ReviewRequestDto;
import com.example.ali.dto.ReviewResponseDto;
import com.example.ali.entity.Orders;
import com.example.ali.entity.Review;
import com.example.ali.entity.User;
import com.example.ali.repository.OrdersRepository;
import com.example.ali.repository.ProductRepository;
import com.example.ali.repository.ReviewRepository;
import org.junit.jupiter.api.*;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.util.Optional;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class ReviewServiceTest {
    @Mock
    ReviewRepository reviewRepository;

    @Mock
    OrdersRepository ordersRepository;

    @Mock
    ProductRepository productRepository;

    
    @Nested
    @DisplayName("댓글 조회")
    class getProductsReview {
        private String comment;
        private Integer rating;
        @BeforeEach
        void setup() {
            comment = "여기에 댓글 내용.";
            rating = 3;
        }
        @Test
        @DisplayName("[200] 댓글 조회")
        void getProductReviews_Success() {
            //given
            //when
            //then
        }
    }

    @Nested
    @DisplayName("댓글 조회")
    class createReview {
        @Mock
        Orders orders;
        private String comment;
        private Integer rating;
        @BeforeEach
        void setup() {
            comment = "여기에 댓글 내용.";
            rating = 3;
        }
        @Test
        @DisplayName("[201] 성공 케이스")
        void createReview() {
            ReviewService reviewService = new ReviewService(reviewRepository, ordersRepository, productRepository);
            //given
            ReviewRequestDto requestDto = new ReviewRequestDto(1L, comment, rating);

            Review review = new Review(comment, rating);
            User user = new User();
            Optional<Orders> optionalOrders = Optional.of(orders);

            // when
            when(reviewRepository.save(any(Review.class))).thenReturn(review);
            when(ordersRepository.findById(any(Long.class))).thenReturn(optionalOrders);
            when(orders.getUser()).thenReturn(user);
            //when(orders.getUser().equals(user)).thenReturn(true); // premitive type은 when을 사용할 수 없다.

            //then
            ResponseEntity<ReviewResponseDto> result = reviewService.createReview(requestDto, user);
            assertThat(result.getBody().getComment()).isEqualTo(review.getComment());
            assertThat(result.getBody().getRating()).isEqualTo(review.getRating());
            assertThat(result.getStatusCode()).isEqualTo(HttpStatus.CREATED);
        }
    }


 
    @Test
    @DisplayName("[202] 성공 케이스")
    void updateReview() {
        //given
        //when
        //then
    }

    @Test
    @DisplayName("[200] 성공 케이스")
    void deleteReview() {
        //given
        //when
        //then
    }
}