package com.khoavo.datasource;

import com.khoavo.domain.Invoice;
import com.khoavo.domain.InvoiceNumber;
import com.khoavo.domain.Invoices;
import com.khoavo.domain.Items;
import com.khoavo.mapper.InvoiceMapper;
import com.khoavo.repository.InvoiceQueryRepository;
import org.mybatis.spring.SqlSessionTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public class InvoiceQueryDatasource implements InvoiceQueryRepository {

    InvoiceMapper invoiceMapper;

    @Autowired
    public InvoiceQueryDatasource(@Qualifier("querySessionTemplate") SqlSessionTemplate querySession) {
        invoiceMapper = querySession.getMapper(InvoiceMapper.class);
    }

    @Override
    public Invoices findAll() {
        List<Invoice> invoices = invoiceMapper.findAll();
        invoices.forEach(invoice -> {
            invoice.setItems(selectItemsBy(invoice.getInvoiceNumber()));
        });
        return new Invoices(invoices);
    }

    @Override
    public Invoice findBy(final InvoiceNumber invoiceNumber) {
        Invoice invoice = invoiceMapper.findBy(invoiceNumber);
        invoice.setItems(selectItemsBy(invoiceNumber));
        return invoice;
    }

    @Override
    public Items selectItemsBy(final InvoiceNumber invoiceNumber) {
        return new Items(invoiceMapper.selectItemsBy(invoiceNumber));
    }
}
