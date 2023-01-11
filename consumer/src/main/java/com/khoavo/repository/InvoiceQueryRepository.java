package com.khoavo.repository;

import com.khoavo.domain.Invoice;
import com.khoavo.domain.InvoiceNumber;
import com.khoavo.domain.Invoices;
import com.khoavo.domain.Items;

public interface InvoiceQueryRepository {
    Invoices findAll();
    Invoice findBy(InvoiceNumber invoiceNumber);

    Items selectItemsBy(InvoiceNumber invoiceNumber);
}
