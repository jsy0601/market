package com.market.service;

import com.market.domain.Item;
import com.market.domain.ItemImage;
import com.market.dto.ItemFormDto;
import com.market.repository.ItemImgRepository;
import com.market.repository.ItemRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import javax.transaction.Transactional;
import java.util.List;

@Service
@Transactional
@RequiredArgsConstructor
public class ItemService {

    private final ItemRepository itemRepository;

    private final ItemImgService itemImgService;

    private final ItemImgRepository itemImgRepository;

    public Long saveItem(ItemFormDto itemFormDto, List<MultipartFile> itemImgFileList) throws Exception {

        //상품 등록
        Item item = itemFormDto.createItem();
        itemRepository.save(item);

        //이미지 등록
        for (int i = 0; i < itemImgFileList.size(); i++) {
            ItemImage itemImage = new ItemImage();
            itemImage.setItem(item);

            itemImgService.saveItemImg(itemImage, itemImgFileList.get(i));
        }

        return item.getId();
    }
}
