package com.khoavo.service;

import com.khoavo.domain.generated.Address;
import com.khoavo.domain.generated.Item;
import com.khoavo.domain.generated.PaidOrder;
import com.khoavo.domain.generated.Presenter;
import com.khoavo.dto.AddressDTO;
import com.khoavo.dto.OrderDTO;
import com.khoavo.dto.OrderLineItemDTO;
import com.khoavo.dto.PersonDTO;
import com.khoavo.producer.OrderProducer;
import com.google.protobuf.Timestamp;
import org.springframework.stereotype.Service;

import java.time.Instant;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;
@Service
public class OrderService {

    final OrderProducer orderProducer;

    public OrderService(OrderProducer orderProducer) {
        this.orderProducer = orderProducer;
    }

    public OrderDTO newOrder(OrderDTO orderDTO) {
        PaidOrder paidOrder = of(orderDTO);
        orderDTO.setId(paidOrder.getId());
        orderProducer.sendMessage(paidOrder);
        return orderDTO;
    }

    private PaidOrder of(OrderDTO orderDTO) {
        Instant instant = Instant.now();
        Timestamp orderedTime = Timestamp.newBuilder().setSeconds(instant.getEpochSecond()).setNanos(instant.getNano()).build();

        return PaidOrder.newBuilder()
                .setId(UUID.randomUUID().toString())
                .setCustomer(of(orderDTO.getCustomer()))
                .setSeller(of(orderDTO.getSeller()))
                .setOrderTime(orderedTime)
                .setDiscountPercent(orderDTO.getDiscountPercent())
                .setTaxPercent(orderDTO.getTaxPercent())
                .addAllItems(of(orderDTO.getOrderLineItems()))
                .build();
    }

    private List<Item> of(List<OrderLineItemDTO> orderLineItemDTOList) {
        return orderLineItemDTOList.stream().map(this::of).collect(Collectors.toList());
    }

    private Item of(OrderLineItemDTO item) {
        return Item.newBuilder()
                .setName(item.getName())
                .setQuantity(item.getQuantity())
                .setDescription(item.getDescription())
                .setRate(item.getCost().doubleValue())
                .build();
    }

    private Presenter of(PersonDTO personDTO) {
        return Presenter.newBuilder()
                .setName(personDTO.getName())
                .setAddress(of(personDTO.getAddress()))
                .build();
    }

    private Address of(AddressDTO addressDTO) {
        return Address.newBuilder()
                .setAddressLine(addressDTO.getAddressLine1())
                .setCity(addressDTO.getCity())
                .setStateProvince(addressDTO.getState())
                .setCountry(addressDTO.getCountry())
                .setZip(addressDTO.getZip())
                .build();
    }

}
