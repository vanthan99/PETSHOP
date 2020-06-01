package com.cdio.petshop.entities;


import lombok.*;

import javax.persistence.*;
@Entity
@Data
@Table
public class DiscountDetail {
    @EmbeddedId
    public DiscountDetailIdentity discountDetailIdentity;

    @Column
    public Float rateDiscount;

    // Map ManyToOne to table Discount
    @ManyToOne(fetch = FetchType.EAGER)
    @MapsId("discountId")
    private Discount discount;

    // Map ManyToOne to table Product
    @ManyToOne(fetch = FetchType.EAGER)
    @MapsId("productId")
    private Product product;
}
