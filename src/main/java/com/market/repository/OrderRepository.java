package com.market.repository;

import com.market.domain.Orders;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import java.util.List;

public interface OrderRepository extends JpaRepository<Orders, Long> {
//    주문 조회
    @Query("select o from Orders o " +
            "where o.user.email = :email " +
            "order by o.orderDate desc")
    List<Orders> findOrders(@Param("email") String email, Pageable pageable);

//    주문 개수 조회
    @Query("select count(o) from Orders o " +
            "where o.user.email = :email")
    Long countOrder(@Param("email") String email);
}

