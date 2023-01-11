package com.khoavo.domain;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@AllArgsConstructor
@NoArgsConstructor
public class Address {
    String addressLine;
    String city;
    String country;
    String stateProvince;
    String zip;
}
