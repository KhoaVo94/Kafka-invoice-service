package com.khoavo.datasource;

import com.khoavo.domain.Invoice;
import com.khoavo.domain.InvoiceNumber;
import com.khoavo.domain.Items;
import com.khoavo.domain.Person;
import com.khoavo.mapper.InvoiceMapper;
import com.khoavo.repository.InvoiceCommandRepository;
import org.mybatis.spring.SqlSessionTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Repository;

@Repository
public class InvoiceCommandDatasource implements InvoiceCommandRepository {

    InvoiceMapper invoiceMapper;

    @Autowired
    public InvoiceCommandDatasource(@Qualifier("registerSessionTemplate") SqlSessionTemplate registerSession) {
        invoiceMapper = registerSession.getMapper(InvoiceMapper.class);
    }

    @Override
    public void registerPerson(final Person person) {
        invoiceMapper.registerPerson(person);
    }

    @Override
    public void registerInvoiceItems(final Items items, final InvoiceNumber invoiceNumber) {
        invoiceMapper.registerInvoiceItems(items, invoiceNumber);
    }

    @Override
    public void registerInvoice(final Invoice invoice) {
        invoiceMapper.registerInvoice(invoice);
    }
}
