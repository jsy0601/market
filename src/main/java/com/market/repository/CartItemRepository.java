package com.market.repository;

import com.market.domain.CartItem;
import org.springframework.data.jpa.repository.JpaRepository;

//장바구니에 들어갈 상품 저장, 조회
public interface CartItemRepository extends JpaRepository<CartItem, Long> {
    CartItem findByCartIdAndItemId(Long cartId, Long itemId);
}
