package com.khoavo.domain;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class Invoices {
    private List<Invoice> values;

    public static Invoices ofEmpty() {
        return new Invoices(new ArrayList<>());
    }

    public Invoices(List<Invoice> values) {
        if (Objects.isNull(values)) {
            this.values = new ArrayList<>();
        } else {
            this.values = values;
        }
    }

    public List<Invoice> invoices() {
        return values;
    }

    public void addInvoice(Invoice invoice) {
        values.add(invoice);
    }

    public boolean isEmpty() {
        return values.isEmpty();
    }

    public static boolean isEmpty(Invoices items) {
        if (Objects.isNull(items)) {
            return true;
        }

        return items.isEmpty();
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Invoices invoices = (Invoices) o;

        return Objects.equals(values, invoices.values);
    }
}
