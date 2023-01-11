package com.khoavo.repository;

import com.khoavo.domain.Invoice;
import com.khoavo.domain.InvoiceNumber;
import com.khoavo.domain.Items;
import com.khoavo.domain.Person;

public interface InvoiceCommandRepository {
    void registerPerson(Person person);
    void registerInvoiceItems(Items items, InvoiceNumber invoiceNumber);
    void registerInvoice(Invoice invoice);
}
