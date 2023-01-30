package com.web.shop.webbanhang.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.Date;
import java.util.Set;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "Orders")
public class Order {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long orderId;
    @Column(name = "orderDate")
    private Date orderDate;

    @Column(name = "status")
    private Boolean status;

    @Column(name = "amount")
    private Float amount;

    @Column(name = "first_name")
    private String firstName;

    @Column(name = "last_name")
    private String lastName;

    @Column(name = "email")
    private String email;

    @Column(name = "phone")
    private String phone;

    @Column(name = "city")
    private String city;

    @Column(name = "district")
    private String district;

    @Column(name = "commune")
    private String commune;

    @Column(name = "address")
    private String address;

    @Column(name = "note")
    private String note;

    @Column(name = "orderCode")
    private String orderCode;

    @Column(name = "payment")
    private String payment;

    @Column(name = "transport")
    private Float transport;

    @OneToMany(mappedBy = "order",cascade = CascadeType.PERSIST)
    private Set<OrderDetail> orderDetails;

    @ManyToOne
    @JoinColumn(name = "userId")
    private User user;
}
