package com.cdio.petshop.entities;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.util.Set;

@Entity
@Getter
@Setter
@Table
public class BillStatus {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column
    private String status;

    @OneToMany(fetch = FetchType.LAZY,mappedBy = "billStatus")
    private Set<Bill> bills;
}
