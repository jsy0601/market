package com.market.dto;

import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;

//주문할 때
@Getter
@Setter
public class OrderDto {

    @NotNull(message = "상품 아이디 입력")
    private Long itemId;

    @Min(value = 1, message = "최소 1개")
    @Max(value = 100, message = "최대 주문 100개")
    private int count;
}
