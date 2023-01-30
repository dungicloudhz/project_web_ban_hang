package com.web.shop.webbanhang.model;

import java.util.Date;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.web.multipart.MultipartFile;

import javax.validation.constraints.NotEmpty;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ProductDto {

	private Long productId;
	@NotEmpty
	private String productName;

	private String image;

	private String description;

	private Boolean status;

	private Long categoryId;

	private Long brandId;

	private Boolean isEdit = false;
}
