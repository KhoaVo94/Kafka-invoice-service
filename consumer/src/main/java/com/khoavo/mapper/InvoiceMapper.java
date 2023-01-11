package com.khoavo.mapper;

import com.khoavo.domain.Invoice;
import com.khoavo.domain.InvoiceNumber;
import com.khoavo.domain.Item;
import com.khoavo.domain.Items;
import com.khoavo.domain.Person;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

@Mapper
public interface InvoiceMapper {

    List<Invoice> findAll();
    Invoice findBy(@Param("invoiceNumber") InvoiceNumber invoiceNumber);

    List<Item> selectItemsBy(@Param("invoiceNumber") InvoiceNumber invoiceNumber);

    void registerPerson(@Param("person") Person person);
    void registerInvoiceItems(@Param("items") Items items, @Param("invoiceNumber") InvoiceNumber invoiceNumber);
    void registerInvoice(@Param("invoice") Invoice invoice);
}
