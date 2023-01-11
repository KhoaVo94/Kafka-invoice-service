package com.khoavo.domain;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@AllArgsConstructor
@NoArgsConstructor
public class Discount {
    private Integer discountPercent = 0;

    public Double calculateDiscountAmount(Double amount) {
        return amount * ((discountPercent * 1.0)/100);
    }
}
