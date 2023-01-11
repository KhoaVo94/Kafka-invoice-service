package com.khoavo.domain;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.beans.factory.annotation.Value;

@Getter
@AllArgsConstructor
@NoArgsConstructor
public class Tax {
    @Value("${spring.default.tax-percent}")
    private Integer taxPercent;

    public Double calculateTaxAmount(Double amount) {
        return amount * ((taxPercent * 1.0)/100);
    }


}
