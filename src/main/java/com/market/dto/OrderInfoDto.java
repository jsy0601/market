package com.market.dto;

import com.market.constant.OrderStatus;
import com.market.domain.Orders;
import lombok.Getter;
import lombok.Setter;

import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

//주문 정보
@Getter @Setter
public class OrderInfoDto {

    public OrderInfoDto(Orders orders) {
        this.orderId = orders.getId();
        this.orderDate = orders.getOrderDate().format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm"));
        this.orderStatus = orders.getOrderStatus();
    }

    private Long orderId;
    private String orderDate;
    private OrderStatus orderStatus;
    //주문 상품 리스트
    private List<OrderItemDto> orderItemDtoList = new ArrayList<>();

    //주문 내역에 있는 객체를 주문 상품 리스트에 추가
    public void addOrderItemDto(OrderItemDto orderItemDto) {
        orderItemDtoList.add(orderItemDto);
    }
}
