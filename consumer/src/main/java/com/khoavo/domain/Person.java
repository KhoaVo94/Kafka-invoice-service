package com.khoavo.domain;

import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.Objects;

@Getter
@NoArgsConstructor
public class Person {
    Integer id;
    String name;
    Address address;

    public Person(String name, Address address) {
        this.name = name;
        this.address = address;
    }

    public String generalAddress() {
        if (Objects.isNull(address)) {
            return "";
        }

        return String.format("%s, %s %s, %s", address.city, address.stateProvince, address.zip, address.country);
    }
}
