package com.web.shop.webbanhang.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;
import java.util.concurrent.atomic.AtomicReference;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class OrderDto {

    private Long orderId;

    private Date orderDate;

    private Boolean status;

    private Float amount;

    private String firstName;

    private String lastName;

    private String email;

    private String phone;

    private String city;

    private String district;

    private String commune;

    private String address;

    private String note;

}
