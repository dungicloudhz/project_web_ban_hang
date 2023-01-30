package com.web.shop.webbanhang.model;

import com.web.shop.webbanhang.entity.Role;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.validator.constraints.Length;

import javax.validation.Valid;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Pattern;
import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class UserDetailDto {

    private Long userId;

    private String firstName;
    private String image;
    private String lastName;
    private String phone;
    private String city;
    private String district;
    private String commune;
}
