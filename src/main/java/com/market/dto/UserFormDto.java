package com.market.dto;

import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.validator.constraints.Length;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;

@Getter @Setter
public class UserFormDto {
    @ApiModelProperty(value = "이름", example = "kim", required = true)
    @NotBlank(message = "이름은 필수")
    private String username;

    @ApiModelProperty(value = "이메일", example = "email@gmail.com", required = true)
    @NotBlank(message = "이메일은 필수")
    @Email(message = "이메일 형식으로 입력")
    private String email;

    @ApiModelProperty(value = "비밀번호", example = "abcd1234", required = true)
    @NotBlank(message = "비밀번호는 필수")
    @Length(min = 8, max = 16, message = "비밀번호는 8자 이상, 16자 이하로 입력")
    private String password;

    @ApiModelProperty(value = "주소", example = "seoul", required = true)
    @NotBlank(message = "주소는 필수")
    private String address;
}
