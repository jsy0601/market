package com.market.controller;

import com.market.dto.ItemFormDto;
import com.market.service.ItemService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;

@Controller
@RequiredArgsConstructor
public class ItemController {
    private final ItemService itemService;

    @GetMapping("/admin/item/new")
    public String itemForm(Model model) {
        model.addAttribute("itemFormDto", new ItemFormDto());
        return "item/itemForm";
    }

    @PostMapping("/admin/item/new")
    public String creatItem(ItemFormDto itemFormDto, Model model) {
        try {
            itemService.saveItem(itemFormDto);
        } catch (Exception e) {
            model.addAttribute("errorMessage", "상품 등록 에러 발생");
            return "item/itemForm";
        }
        return "redirect:/";
    }
}
