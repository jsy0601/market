package com.market.controller;

import com.market.domain.User;
import com.market.dto.UserFormDto;
import com.market.service.UserService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiResponse;
import io.swagger.v3.oas.annotations.Operation;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.validation.Valid;

@Api(tags = "User Controller")
@RequestMapping("/accounts")
@Controller
@RequiredArgsConstructor
@Slf4j
public class UserController {
    private final UserService userService;
    private final PasswordEncoder passwordEncoder;

    @Operation(summary = "요약", description = "설명")
    @ApiResponse(code = 200, message = "ok")
    @GetMapping(value = "/new")
    public String userForm(Model model) {
        model.addAttribute("userFormDto", new UserFormDto());
        return "user/userForm";
    }

    @PostMapping(value = "/new")
    public String userForm(@Validated UserFormDto userFormDto, BindingResult bindingResult, Model model) {
        if (bindingResult.hasErrors()) {
            return "user/userForm";
        }
        try {
            User user = User.createUser(userFormDto, passwordEncoder);
            userService.saveUser(user);
        } catch (IllegalStateException e) {
            model.addAttribute("errorMessage", e.getMessage());
            return "user/userForm";
        }

        return "redirect:/";
    }

    @GetMapping(value = "/login")
    public String login() {
        return "user/loginForm";
    }

    @GetMapping(value = "/login/error")
    public String loginError(Model model) {
        model.addAttribute("loginErrorMsg", "아이디 또는 비밀번호를 확인해주세요.");
        return "user/loginForm";
    }
}
