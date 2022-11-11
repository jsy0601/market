package com.market.dto;

import com.market.constant.ItemSellStatus;
import com.market.domain.Item;
import lombok.Builder;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;
import org.modelmapper.ModelMapper;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
//@Builder
public class ItemFormDto {
    private Long id;

    @NotBlank(message = "상품명을 입력하세요")
    private String title;
    @NotNull(message = "가격을 입력하세요")
    private int price;
    @NotNull(message = "재고수량을 입력하세요")
    private int stockQuantity;
    private ItemSellStatus itemSellStatus;

    //상품 저장 후 수정할 때 이미지 저장
    private List<ItemImageDto> itemImageDtoList = new ArrayList<>();

    //수정 시 이미지 아이디 저장
    private List<Long> itemImgIds = new ArrayList<>();
    private static ModelMapper modelMapper = new ModelMapper();

    public Item createItem() {
        return modelMapper.map(this, Item.class);
    }

    public static ItemFormDto of(Item item) {
        return modelMapper.map(item, ItemFormDto.class);
    }
}
