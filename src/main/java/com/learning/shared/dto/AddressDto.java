package com.learning.shared.dto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class AddressDto {
    private long id;
    private String addressId;
    private String country;
    private String state;
    private String city;
    private String houseAddress;
    private String pinCode;
    private String type;
    private UserDto userDetails;

}
