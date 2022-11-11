package com.market.repository;

import com.market.constant.ItemSellStatus;
import com.market.domain.Item;
import com.market.domain.QItem;
import com.market.domain.QItemImage;
import com.market.dto.ItemSearchDto;
import com.market.dto.MainItemDto;
import com.market.dto.QMainItemDto;
import com.querydsl.core.QueryResults;
import com.querydsl.core.types.dsl.BooleanExpression;
import com.querydsl.jpa.impl.JPAQueryFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.security.access.annotation.Secured;
import org.springframework.util.StringUtils;

import javax.persistence.EntityManager;
import java.util.List;

public class ItemRepositoryCustomImpl implements ItemRepositoryCustom {
    private JPAQueryFactory queryFactory;

    public ItemRepositoryCustomImpl(EntityManager em) {
        this.queryFactory = new JPAQueryFactory(em);
    }

    private BooleanExpression searchSellStatusEq(ItemSellStatus searchSellStatus) {
        return searchSellStatus == null ? null : QItem.item.itemSellStatus.eq(searchSellStatus);
    }

    private BooleanExpression searchByLike(String searchBy, String searchQuery) {
        if (StringUtils.pathEquals("title", searchBy)) {
            return QItem.item.title.like("%" + searchQuery + "%");
        }
        else if (StringUtils.pathEquals("createdBy", searchBy)) {
            return QItem.item.user.email.like("%" + searchQuery + "%");
        }
        return null;
    }

    @Override
    public Page<Item> getAdminItemPage(ItemSearchDto itemSearchDto, Pageable pageable) {
        QueryResults<Item> results = queryFactory
                .selectFrom(QItem.item)
                .where(searchSellStatusEq(itemSearchDto.getSearchSellStatus()),
                        searchByLike(itemSearchDto.getSearchBy(), itemSearchDto.getSearchQuery()))
                .orderBy(QItem.item.id.desc())
                .offset(pageable.getOffset())
                .limit(pageable.getPageSize())
                .fetchResults();
        List<Item> content = results.getResults();
        long total = results.getTotal();
        return new PageImpl<>(content, pageable, total);
    }

    @Override
    public Page<MainItemDto> getMainItemPage(ItemSearchDto itemSearchDto, Pageable pageable) {
        QItem item = QItem.item;
        QItemImage itemImage = QItemImage.itemImage;

        QueryResults<MainItemDto> results = queryFactory
                .select(
                        new QMainItemDto(
                                item.id,
                                item.title,
                                itemImage.imgUrl,
                                item.price)
                )
                .from(itemImage)
                .join(itemImage.item, item)
                .where(itemImage.repImgYn.eq("Y"))
//                .where(titleLike(itemSearchDto.getSearchQuery()))
                .orderBy(item.id.desc())
                .offset(pageable.getOffset())
                .limit(pageable.getPageSize())
                .fetchResults();

        List<MainItemDto> content = results.getResults();
        long total = results.getTotal();
        return new PageImpl<>(content, pageable, total);
    }

    private BooleanExpression titleLike(String searchQuery) {
        return StringUtils.isEmpty(searchQuery) ? null : QItem.item.title.like("%" + searchQuery + "%");
    }
}
