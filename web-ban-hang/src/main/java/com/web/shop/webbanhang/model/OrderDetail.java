package com.web.shop.webbanhang.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class OrderDetail {

	private Long orderDetailId;
	private Long orderId;
	private Long productId;
	private Integer quantity;
	private Float unitPrice;
}
