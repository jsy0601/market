package com.market.dto;

import com.market.domain.OrderItem;
import lombok.Getter;
import lombok.Setter;

//주문 내역 화면
@Getter @Setter
public class OrderItemDto {
    public OrderItemDto(OrderItem orderItem, String imgUrl) {
        this.title = orderItem.getItem().getTitle();
        this.count = orderItem.getCount();
        this.orderPrice = orderItem.getOrderPrice();
        this.imgUrl = imgUrl;
    }

    private String title;
    private int count;
    private int orderPrice;
    private String imgUrl;
}
