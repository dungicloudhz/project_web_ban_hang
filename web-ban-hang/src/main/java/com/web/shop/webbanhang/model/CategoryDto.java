package com.web.shop.webbanhang.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotEmpty;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class CategoryDto {

	private Long categoryId;
	@NotEmpty(message = "Category name is not Empty!")
	private String name;
	@NotEmpty(message = "Slug name is not Empty!")
	private String slug;
	
	private String description;

	private Boolean isEdit = false;
}
