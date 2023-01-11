package com.khoavo.domain;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@AllArgsConstructor
@NoArgsConstructor
public class Amount {
    Integer quantity = 0;
    Double rate = 0.0;

    public Double getAmount() {
        return quantity * rate;
    }
}
