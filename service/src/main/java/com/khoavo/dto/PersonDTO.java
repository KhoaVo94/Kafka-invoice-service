package com.khoavo.dto;

import lombok.Builder;
import lombok.Data;

@Builder
@Data
public class PersonDTO {

    private String name;
    private AddressDTO address;

}
