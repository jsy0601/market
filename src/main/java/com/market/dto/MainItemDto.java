package com.market.dto;

import lombok.Getter;
import lombok.Setter;

@Getter @Setter
public class MainItemDto {
    private Long id;
    private String title;
    private String imgUrl;
    private Integer price;

    public MainItemDto(Long id, String title, String imgUrl, Integer price) {
        this.id = id;
        this.title = title;
        this.imgUrl = imgUrl;
        this.price = price;
    }
}
