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
	@NotEmpty
	private String name;
	@NotEmpty
	private String slug;
	
	private String description;

	private Boolean isEdit = false;
}
