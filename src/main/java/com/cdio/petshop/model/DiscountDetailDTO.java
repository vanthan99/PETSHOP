package com.cdio.petshop.model;

import com.cdio.petshop.entities.Discount;
import com.cdio.petshop.entities.Product;
import lombok.*;

@Getter
@Setter
@EqualsAndHashCode
@NoArgsConstructor
public class DiscountDetailDTO {
    private Product product;
    private Discount discount;
    private Float rateDiscount;
}
