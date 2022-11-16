package com.market.repository;

import com.market.domain.ItemImage;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ItemImgRepository extends JpaRepository<ItemImage, Long> {
    List<ItemImage> findByItemIdOrderByIdAsc(Long itemId);
    ItemImage findByItemIdAndRepImgYn(Long itemId, String repimgYn);
}
