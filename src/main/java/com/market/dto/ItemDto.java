package com.market.dto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ItemDto {
    private Long id;
    private String title;
    private int price;
    private String itemSellStatus;

}
