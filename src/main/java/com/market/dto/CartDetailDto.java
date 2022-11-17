package com.market.dto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class CartDetailDto {
    private Long cartItemId;
    private String title;
    private int price;
    private int count;
    private String imgUrl;

    public CartDetailDto(Long cartItemId, String title, int price, int count, String imgUrl) {
        this.cartItemId = cartItemId;
        this.title = title;
        this.price = price;
        this.count = count;
        this.imgUrl = imgUrl;
    }
}
