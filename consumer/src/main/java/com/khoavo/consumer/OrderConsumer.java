package com.khoavo.consumer;

import com.khoavo.domain.Invoice;
import com.khoavo.domain.InvoiceBuilder;
import com.khoavo.domain.generated.PaidOrder;
import com.khoavo.service.InvoiceService;
import lombok.extern.slf4j.Slf4j;
import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Component;

import java.time.Instant;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;

@Component
@Slf4j
public class OrderConsumer {

    InvoiceService invoiceService;

    @Autowired
    public OrderConsumer(InvoiceService invoiceService) {
        this.invoiceService = invoiceService;
    }

    @KafkaListener(topics = {"${spring.kafka.template.default-topic}"})
    public void onMessage(ConsumerRecord<String, PaidOrder> consumerRecord) {
        PaidOrder paidOrder = consumerRecord.value();
        log.info("Order key: {} , value: {}", paidOrder.getInvoiceNumber(), paidOrder);

        Invoice invoice = of(paidOrder);
        log.info("invoice: {}", invoice);

        // TODO: Need to upload invoice into private access google drive/sharepoint...
        invoiceService.writeInvoiceToPDF(invoice);

        invoiceService.registerInvoice(invoice);
    }

    private Invoice of(PaidOrder paidOrder) {
        InvoiceBuilder invoiceBuilder = new InvoiceBuilder()
                .setInvoiceNumber(paidOrder.getInvoiceNumber())
                .setSeller(paidOrder.getSeller().getName(),
                        paidOrder.getSeller().getAddress().getAddressLine(),
                        paidOrder.getSeller().getAddress().getCity(),
                        paidOrder.getSeller().getAddress().getCountry(),
                        paidOrder.getSeller().getAddress().getStateProvince(),
                        paidOrder.getSeller().getAddress().getZip()
                )
                .setCustomer(paidOrder.getCustomer().getName(),
                        paidOrder.getCustomer().getAddress().getAddressLine(),
                        paidOrder.getCustomer().getAddress().getCity(),
                        paidOrder.getCustomer().getAddress().getCountry(),
                        paidOrder.getCustomer().getAddress().getStateProvince(),
                        paidOrder.getCustomer().getAddress().getZip()
                )
                .setInvoiceDate(
                        Instant.ofEpochSecond(paidOrder.getOrderTime().getSeconds(), paidOrder.getOrderTime().getNanos())
                                .atZone(ZoneId.systemDefault())
                                .format(DateTimeFormatter.ofPattern("dd/mm/yyyy HH:mm"))
                )
                .setDiscountPercent(paidOrder.getDiscountPercent())
                .setTaxPercent(paidOrder.getTaxPercent());

        paidOrder.getItemsList().forEach(item -> invoiceBuilder.addItem(item.getName(), item.getDescription(), item.getQuantity(), item.getRate()));

        return invoiceBuilder.build();
    }
}
