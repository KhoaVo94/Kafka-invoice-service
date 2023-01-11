package com.khoavo.dto;

import lombok.Builder;
import lombok.Data;

import java.math.BigDecimal;

@Builder
@Data
public class OrderLineItemDTO {

    private String name;
    private String description;
    private Integer quantity;
    private Double cost;

}
