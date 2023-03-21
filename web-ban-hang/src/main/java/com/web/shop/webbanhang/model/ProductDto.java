package com.web.shop.webbanhang.model;


import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.Min;
import javax.validation.constraints.NotEmpty;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ProductDto {

	private Long productId;

	@NotEmpty
	@Min(8)
	private String productName;

	private String image;

	@NotEmpty
	private String description;

	private Boolean status;

	private Long categoryId;

	private Long brandId;

	private Boolean isEdit = false;
}
