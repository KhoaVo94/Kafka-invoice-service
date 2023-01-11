package com.khoavo.domain;

import com.khoavo.util.DataClassUtil;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
@AllArgsConstructor
public class Invoice {
    InvoiceNumber invoiceNumber;
    Seller seller;
    Customer customer;
    Items items;
    InvoiceDate invoiceDate;
    Discount discount;
    Tax tax;

    public double subTotal() {
        if (Items.isEmpty(items)) {
            return 0;
        }

        return items.items()
                .stream()
                .mapToDouble(Item::amount)
                .sum();
    }

    public double total() {
        double total = subTotal();
        total = total - discount.calculateDiscountAmount(total);
        total = total + tax.calculateTaxAmount(total);

        return total;
    }

    public String discountAmount() {
        return String.format("%.2f", discount.calculateDiscountAmount(subTotal()));
    }

    public int getDiscountPercent() {
        return discount.getDiscountPercent();
    }

    public String taxAmount() {
        return String.format("%.2f", tax.calculateTaxAmount(subTotal() - discount.calculateDiscountAmount(subTotal())));
    }

    public int getTaxPercent() {
        return tax.getTaxPercent();
    }

    public void setItems(Items items) {
        this.items = items;
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
