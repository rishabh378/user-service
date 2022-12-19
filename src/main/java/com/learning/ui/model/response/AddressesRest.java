package com.learning.ui.model.response;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class AddressesRest {
    private String addressId;
    private String country;
    private String state;
    private String city;
    private String houseAddress;
    private String pinCode;
    private String type;

}
