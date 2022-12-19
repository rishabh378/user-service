package com.learning.io.entity;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.io.Serializable;
import java.util.List;

@Getter
@Setter
@Entity
@Table(name = "users")
public class UserEntity implements Serializable {

    private static final long serialVersionUID = -6946039192645955058L;

    @Id
    @GeneratedValue
    private long id;
    @Column(nullable = false)
    private String userId;
    @Column(nullable = false,length = 50)
    private String firstName;
    @Column(nullable = false,length = 50)
    private String lastName;
    @Column(nullable = false,length = 120 )
    private String email;
    @Column(nullable = false)
    private String encryptedPassword;
    private String emailVerificationToken;
    @Column(nullable = true)
    private Boolean emailVerificationStatus = false;
    @OneToMany(mappedBy = "userDetails",cascade = CascadeType.ALL)
    private List<AddressEntity> addresses;

}
