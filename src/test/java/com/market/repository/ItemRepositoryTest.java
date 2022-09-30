package com.market.repository;

import com.market.constant.ItemSellStatus;
import com.market.domain.Item;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.time.LocalDateTime;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
class ItemRepositoryTest {
    @Autowired
    ItemRepository itemRepository;

    @Test
    @DisplayName("상품 저장 테스트")
    public void createItemTest() {
        Item item = new Item();
        item.setTitle("테스트 상품");
        item.setPrice(5000);
        item.setItemSellStatus(ItemSellStatus.SELL);
        item.setStockQuantity(20);
        item.setCreatedAt(LocalDateTime.now());
        Item saveItem = itemRepository.save(item);
        System.out.println(saveItem.toString());
    }

    public void createItemList() {
        for (int i = 1; i <= 10; i++) {
            Item item = new Item();
            item.setTitle("테스트 상품" + i);
            item.setPrice(10000 + i);
            item.setItemSellStatus((ItemSellStatus.SELL));
            item.setStockQuantity(100);
            item.setCreatedAt(LocalDateTime.now());
            item.setModifiedAt(LocalDateTime.now());
            Item savedItem = itemRepository.save(item);
        }
    }

    @Test
    @DisplayName("상품명 조회 테스트")
    public void findByItemTitle() {
        this.createItemList();
        List<Item> itemList = itemRepository.findByTitle("테스트 상품2");
        for (Item item : itemList) {
            System.out.println(item.toString());
        }
    }
}