package com.web.shop.webbanhang.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.validator.constraints.Length;

import javax.persistence.*;
import javax.validation.constraints.*;
import java.util.Date;
import java.util.HashSet;
import java.util.Set;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "users")
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long userId;

    @Column(name = "username")
    @NotEmpty
    @Length(min = 8)
    private String username;

    @NotEmpty
    @Column(name = "firstName")
    private String firstName;

    @Column(name = "lastName")
    @NotEmpty
    private String lastName;

    @Column(name = "phone")
    @NotEmpty
    private String phone;

    @Column(name = "city")
    @NotEmpty
    private String city;

    @Column(name = "district")
    @NotEmpty
    private String district;

    @Column(name = "commune")
    @NotEmpty
    private String commune;

    @Column(name = "image")
    private String image;

    @Column(name = "email")
    @NotEmpty(message = "Vui lòng nhập email.")
    @Email(message = "Vui lòng nhập đúng định dạng email.")
    private String email;
    @Column(name = "password")
    @NotEmpty
    private String password;
    @Column(name = "enabled")
    private Boolean enabled;

    @Column(name = "account_non_locked")
    private boolean accountNonLocked;

    @Column(name = "failed_attempt")
    private int failedAttempt;

    @Column(name = "lock_time")
    private Date lockTime;

    @Column(name = "reset_password_token")
    private String resetPasswordToken;



    @ManyToMany(cascade = CascadeType.ALL,fetch = FetchType.EAGER)
    @JoinTable(
            name = "users_roles",
            joinColumns = @JoinColumn(name = "user_id"),
            inverseJoinColumns = @JoinColumn(name ="role_id")
    )
    private Set<Role> roles = new HashSet<>();

    @OneToMany(mappedBy = "user")
    private Set<Order> orders;

    @OneToMany(mappedBy = "user")
    private Set<Wishlish> wishlishes;

    public void addRole(Role role){
        this.roles.add(role);
    }
}
