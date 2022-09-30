package com.market.service;

import com.market.domain.Item;
import com.market.dto.ItemFormDto;
import com.market.repository.ItemRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;

@Service
@Transactional
@RequiredArgsConstructor
public class ItemService {
    private final ItemRepository itemRepository;

    public Long saveItem(ItemFormDto itemFormDto) throws Exception {
        Item item = itemFormDto.createItem();
        itemRepository.save(item);

        return item.getId();
    }
}
