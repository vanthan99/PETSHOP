package com.cdio.petshop.entities;

import lombok.*;

import javax.persistence.*;
import java.io.Serializable;

@Entity
@Getter
@Setter
@Table
public class BillDetail {
    @EmbeddedId
    private BillDetailIdentity billDetailIdentity;

    // map ManyToOne  to table Bill
    @ManyToOne(fetch = FetchType.EAGER)
    @MapsId("billId")
    private Bill bill;

    // map ManyToOne  to table Product
    @ManyToOne(fetch = FetchType.EAGER)
    @MapsId("productId")
    private Product product;

    @Column
    private Integer quantity;

    @Column
    private Integer price;

    @Column
    private Float rateDiscount;
}
