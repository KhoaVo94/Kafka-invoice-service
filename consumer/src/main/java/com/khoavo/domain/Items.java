package com.khoavo.domain;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Objects;

public class Items {
    private List<Item> values;

    public static Items ofEmpty() {
        return new Items(new ArrayList<>());
    }

    public Items(List<Item> values) {
        if (Objects.isNull(values)) {
            this.values = new ArrayList<>();
        } else {
            this.values = values;
        }
    }

    public List<Item> items() {
        return values;
    }

    public void addItem(Item item) {
        values.add(item);
    }

    public boolean isEmpty() {
        return values.isEmpty();
    }

    public static boolean isEmpty(Items items) {
        if (Objects.isNull(items)) {
            return true;
        }

        return items.isEmpty();
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Items items = (Items) o;

        return Objects.equals(values, items.values);
    }
}
