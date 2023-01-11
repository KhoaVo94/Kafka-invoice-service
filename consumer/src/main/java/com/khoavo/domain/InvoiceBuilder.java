package com.khoavo.domain;

import com.khoavo.util.StringUtils;

import java.time.format.DateTimeParseException;
import java.util.Objects;

public class InvoiceBuilder {
    InvoiceNumber invoiceNumber;
    Seller seller;
    Customer customer;
    Items items = Items.ofEmpty();
    InvoiceDate invoiceDate;
    Discount discount;
    Tax tax;

    public InvoiceBuilder setInvoiceNumber(String invoiceNumber) {
        if (StringUtils.isNotBlank(invoiceNumber)) {
            this.invoiceNumber = new InvoiceNumber(invoiceNumber);
        }
        return this;
    }

    public InvoiceBuilder setSeller(String name, String addressLine, String city, String country, String stateProvince, String zip) {
        seller = new Seller(name, new Address(addressLine, city, country, stateProvince, zip));
        return this;
    }

    public InvoiceBuilder setCustomer(String name, String addressLine, String city, String country, String stateProvince, String zip) {
        customer = new Customer(name, new Address(addressLine, city, country, stateProvince, zip));
        return this;
    }

    public InvoiceBuilder setInvoiceDate(String invoiceDate) {
        if (StringUtils.isNotBlank(invoiceDate)) {
            try {
                this.invoiceDate = new InvoiceDate(invoiceDate);
            } catch (DateTimeParseException ex) {
                this.invoiceDate = InvoiceDate.ofDefault();
            }
        }
        return this;
    }

    public InvoiceBuilder setDiscountPercent(Integer percent) {
        if (Objects.nonNull(percent)) {
            this.discount = new Discount(percent);
        }
        return this;
    }

    public InvoiceBuilder setTaxPercent(Integer percent) {
        if (Objects.nonNull(percent)) {
            this.tax = new Tax(percent);
        }
        return this;
    }

    public InvoiceBuilder addItem(String name, String description, int quantity, double rate) {
        items.addItem(new Item(name, new Amount(quantity, rate), description));
        return this;
    }

    public Invoice build() {
        if (Objects.isNull(invoiceNumber)) {
            invoiceNumber = InvoiceNumber.randGenerate();
        }

        if (Objects.isNull(invoiceDate)) {
            invoiceDate = InvoiceDate.ofDefault();
        }

        if (Objects.isNull(discount)) {
            discount = new Discount();
        }

        if (Objects.isNull(tax)) {
            tax = new Tax();
        }

        return new Invoice(invoiceNumber, seller, customer, items, invoiceDate, discount, tax);
    }
}
