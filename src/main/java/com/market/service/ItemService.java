package com.market.service;

import com.market.domain.Item;
import com.market.domain.ItemImage;
import com.market.domain.User;
import com.market.dto.ItemFormDto;
import com.market.dto.ItemImageDto;
import com.market.dto.ItemSearchDto;
import com.market.repository.ItemImgRepository;
import com.market.repository.ItemRepository;
import com.market.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import javax.persistence.EntityNotFoundException;
import java.util.ArrayList;
import java.util.List;

@Service
@Transactional
@RequiredArgsConstructor
@Slf4j
public class ItemService {

    private final ItemRepository itemRepository;

    private final ItemImgService itemImgService;

    private final ItemImgRepository itemImgRepository;

    private final UserRepository userRepository;

    @Transactional
    public Long saveItem(String email  ,ItemFormDto itemFormDto, List<MultipartFile> itemImgFileList) throws Exception {

        User user = userRepository.findByEmail(email);
        //상품 등록
        Item item = itemFormDto.createItem();
        item.setUser(user);
        itemRepository.save(item);
        //이미지 등록
        for (int i = 0; i < itemImgFileList.size(); i++) {
            ItemImage itemImage = new ItemImage();
            itemImage.setItem(item);

            itemImgService.saveItemImg(itemImage, itemImgFileList.get(i));
        }

        return item.getId();
    }

    @Transactional(readOnly = true)
    public ItemFormDto getItemDtl(Long itemId){
        List<ItemImage> itemImageList = itemImgRepository.findByItemIdOrderByIdAsc(itemId);
        List<ItemImageDto> itemImageDtoList = new ArrayList<>();
        for (ItemImage itemImage : itemImageList) {
            ItemImageDto itemImgDto = ItemImageDto.of(itemImage);
            itemImageDtoList.add(itemImgDto);
        }

        Item item = itemRepository.findById(itemId)
                .orElseThrow(EntityNotFoundException::new);
        ItemFormDto itemFormDto = ItemFormDto.of(item);
        itemFormDto.setItemImageDtoList(itemImageDtoList);
        return itemFormDto;
    }

    public Long updateItem(String email, ItemFormDto itemFormDto, List<MultipartFile> itemImgFileList) throws Exception {
        User user = userRepository.findByEmail(email);
        log.info("수정중");
        //상품 수정
        Item item = itemRepository.findById(itemFormDto.getId())
                .orElseThrow(() -> new NullPointerException("해당 아이디가 존재하지 않습니다."));
        item.updateItem(itemFormDto);
        item.setUser(user);
        List<Long> itemImgIds = itemFormDto.getItemImgIds();

        //이미지 등록
        for(int i=0;i<itemImgFileList.size();i++){
            itemImgService.updateItemImg(itemImgIds.get(i),
                    itemImgFileList.get(i));
        }

        return item.getId();
    }

    @Transactional(readOnly = true)
    public Page<Item> getAdminItemPage(ItemSearchDto itemSearchDto, Pageable pageable) {
        return itemRepository.getAdminItemPage(itemSearchDto, pageable);
    }
}
