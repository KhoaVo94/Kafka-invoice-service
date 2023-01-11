package com.khoavo.dto;

import lombok.Builder;
import lombok.Data;

import java.time.LocalDateTime;
import java.util.List;

@Builder
@Data
public class OrderDTO {
    private String id;
    private PersonDTO seller;
    private PersonDTO customer;
    private List<OrderLineItemDTO> orderLineItems;
    private LocalDateTime purchaseDate;
    private Integer discountPercent;
    private Integer taxPercent;

}
