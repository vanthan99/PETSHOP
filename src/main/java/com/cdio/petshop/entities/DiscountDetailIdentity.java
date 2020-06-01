package com.cdio.petshop.entities;

import lombok.*;


import javax.persistence.Embeddable;
import java.io.Serializable;

@Embeddable
@Getter
@Setter
@EqualsAndHashCode
@AllArgsConstructor
@NoArgsConstructor
public class DiscountDetailIdentity implements Serializable {
    private Long discountId;
    private Long productId;
}
