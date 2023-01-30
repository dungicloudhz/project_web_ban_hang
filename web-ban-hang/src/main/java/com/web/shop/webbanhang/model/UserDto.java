package com.web.shop.webbanhang.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.validator.constraints.Length;

import javax.validation.Valid;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Pattern;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class UserDto {

    private Long userId;
    @Valid
    @NotBlank
    @Pattern(regexp = "\\p{Alnum}{1,30}")
    private String username;
    @NotEmpty
    private String email;
    @NotEmpty
    @Length(min = 6)
    private String password;
    private String captcha;
    private Boolean enabled;
    private String hiddenCaptcha;
    private String realCaptcha;
}
