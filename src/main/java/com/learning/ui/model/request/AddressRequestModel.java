package com.learning.ui.model.request;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class AddressRequestModel {
    private String country;
    private String state;
    private String city;
    private String houseAddress;
    private String pinCode;
    private String type;

}
