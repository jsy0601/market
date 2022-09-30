package com.market.domain;

import com.market.constant.ItemSellStatus;
import lombok.*;

import javax.persistence.*;

@Entity
@Table(name = "ITEM")
@Getter
@Setter
@ToString
public class Item extends Timestamped {
    @Id
    @Column(name = "item_id")
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id; //상품코드

    private String title; //상품명

    private int price; //상품가격

    private int stockQuantity; //재고수량

    @Enumerated(EnumType.STRING)
    private ItemSellStatus itemSellStatus; //상품 판매 상태 SELL, SOLD_OUT
}
