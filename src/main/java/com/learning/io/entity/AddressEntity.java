package com.learning.io.entity;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.io.Serializable;

@Getter
@Setter
@Entity(name = "addresses")
public class AddressEntity implements Serializable {

    private static final long serialVersionUID = 4527729031394744561L;
    @Id
    @GeneratedValue
    private long id;
    @Column(nullable = false,length = 15)
    private String addressId;
    @Column(nullable = false,length = 20)
    private String country;
    @Column(nullable = false,length = 25)
    private String state;
    @Column(nullable = false,length = 30)
    private String city;
    @Column(nullable = false,length = 100)
    private String houseAddress;
    @Column(nullable = false,length = 10)
    private String pinCode;
    @Column(nullable = false,length = 15)
    private String type;
    @ManyToOne
    @JoinColumn(name = "users_id")
    private UserEntity userDetails;

}
