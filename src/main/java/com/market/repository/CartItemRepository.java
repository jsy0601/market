package com.market.repository;

import com.market.domain.CartItem;
import com.market.dto.CartDetailDto;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

//장바구니에 들어갈 상품 저장, 조회
public interface CartItemRepository extends JpaRepository<CartItem, Long> {
    CartItem findByCartIdAndItemId(Long cartId, Long itemId);

    @Query("select new com.market.dto.CartDetailDto(ci.id, i.title, i.price, ci.count, im.imgUrl) " +
            "from CartItem ci, ItemImage im " +
            "join ci.item i " +
            "where ci.cart.id = :cartId " +
            "and im.item.id = ci.item.id " +
            "and im.repImgYn = 'Y'" +
            "order by ci.createdAt desc"
    )
    List<CartDetailDto> findCartDetailDtoList(Long cartId);
}
