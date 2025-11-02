package com.web.shop.webbanhang.model;


import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.validator.constraints.Length;

import javax.validation.constraints.Min;
import javax.validation.constraints.NotEmpty;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ProductDto {

	private Long productId;

	@Length(min = 1)
	private String productName;

	private String image;

	private String description;

	private Boolean status;

	private Long categoryId;

	private Long brandId;

	private Boolean isEdit = false;
}
