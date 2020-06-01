package com.cdio.petshop.entities;

import lombok.*;


import javax.persistence.Embeddable;
import java.io.Serializable;

@Embeddable
@Getter
@Setter
@EqualsAndHashCode
public class BillDetailIdentity implements Serializable {
    private Long productId;
    private Long billId;
}
