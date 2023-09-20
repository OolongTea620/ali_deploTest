//package com.example.ali.repository;
//
//import com.example.ali.entity.Orders;
//import com.example.ali.entity.Review;
//import org.springframework.data.jpa.repository.JpaRepository;
//
//import java.util.List;
//
//public interface ReviewRepository extends JpaRepository<Review, Long> {
//
//    List<Review> findAllWithOrdersByOrders(List<Orders> orders);
//}