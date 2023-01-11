package com.khoavo.domain;

import com.khoavo.util.DataClassUtil;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.Objects;

@Getter
@AllArgsConstructor
@NoArgsConstructor
public class Item {
    String name;
    Amount amount;
    String description;

    public Double amount() {
        if (Objects.isNull(amount)) {
            return 0.0;
        }

        return amount.getAmount();
    }

    @Override
    public boolean equals(Object o) {
        return DataClassUtil.equals(this, o);

    }

    @Override
    public int hashCode() {
        return DataClassUtil.hashCode(this);
    }
}
