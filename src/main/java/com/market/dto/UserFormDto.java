package com.market.dto;

import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.NotBlank;

@Getter @Setter
public class UserFormDto {
    @NotBlank(message = "이름은 필수")
    private String username;
    @NotBlank(message = "이메일은 필수")
    private String email;
    @NotBlank(message = "비밀번호는 필수")
    private String password;
    @NotBlank(message = "주소는 필수")
    private String address;
}
