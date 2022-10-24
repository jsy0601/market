package com.market.dto;

import com.market.constant.ItemSellStatus;
import lombok.Getter;
import lombok.Setter;

@Getter @Setter
public class ItemSearchDto {
    private ItemSellStatus searchSellStatus; //SELL, SOLD_OUT
    private String searchBy; //상품명, 상품 등록자 아이디
    private String searchQuery; //검색어
}
