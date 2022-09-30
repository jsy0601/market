package com.market.domain;

import com.market.constant.ItemSellStatus;
import lombok.*;

import javax.persistence.*;

@Entity
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

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id")
    private User user;

    //    연관관계 편의 메서드
    public void setUser(User user) {
        this.user = user;
        user.getItems().add(this);
    }
}
