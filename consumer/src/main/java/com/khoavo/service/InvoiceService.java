package com.khoavo.service;

import com.itextpdf.text.DocumentException;
import com.khoavo.domain.Invoice;
import com.khoavo.domain.InvoiceNumber;
import com.khoavo.domain.Invoices;
import com.khoavo.repository.InvoiceCommandRepository;
import com.khoavo.repository.InvoiceQueryRepository;
import com.khoavo.util.PDFWriter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.io.FileNotFoundException;

@Service
public class InvoiceService {

    InvoiceCommandRepository invoiceCommandRepository;
    InvoiceQueryRepository invoiceQueryRepository;

    @Autowired
    public InvoiceService(
            InvoiceCommandRepository invoiceCommandRepository,
            InvoiceQueryRepository invoiceQueryRepository
    ) {
        this.invoiceCommandRepository = invoiceCommandRepository;
        this.invoiceQueryRepository = invoiceQueryRepository;
    }

    public Invoices findAllInvoices() {
        return invoiceQueryRepository.findAll();
    }

    public Invoice findInvoiceBy(String invoiceNumber) {
        return invoiceQueryRepository.findBy(new InvoiceNumber(invoiceNumber));
    }

    @Transactional
    public void registerInvoice(Invoice invoice) {
        invoiceCommandRepository.registerPerson(invoice.getCustomer());
        invoiceCommandRepository.registerPerson(invoice.getSeller());
        invoiceCommandRepository.registerInvoiceItems(invoice.getItems(), invoice.getInvoiceNumber());
        invoiceCommandRepository.registerInvoice(invoice);
    }

    public void writeInvoiceToPDF(Invoice invoice) {
        try {
            PDFWriter.invoiceToPDF(invoice);
        } catch (FileNotFoundException e) {
            throw new RuntimeException(e);
        } catch (DocumentException e) {
            throw new RuntimeException(e);
        }
    }
}
