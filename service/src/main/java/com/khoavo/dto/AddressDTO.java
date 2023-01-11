package com.khoavo.dto;

import lombok.Builder;
import lombok.Data;

@Builder
@Data
public class AddressDTO {

    private String addressLine1;
    private String city;
    private String state;
    private String country;
    private String zip;

}
