package com.cdio.petshop.model;

import com.cdio.petshop.entities.Bill;
import com.cdio.petshop.entities.Product;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@EqualsAndHashCode
@NoArgsConstructor
public class BillDetailDTO {
    private Bill bill;
    private Product product;
    private Integer quantity;
    private Integer price;
    private Float rateDiscount;
}
