package com.web.shop.webbanhang.model;

import java.util.Date;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class Order {
	
	private Long orderId;
	private Date orderDate;
	private Long customerId;
	private Float amount;
	private Boolean status;

}
