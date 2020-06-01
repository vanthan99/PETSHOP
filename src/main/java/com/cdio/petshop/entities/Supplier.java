package com.cdio.petshop.entities;

import lombok.Data;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.util.Set;

@Entity
@Getter
@Setter
@Table
public class Supplier {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(length = 50)
    private String name;

    @Column
    private String address;

    @Column(length = 10)
    private String phoneNumber;

    @Column(columnDefinition = "TEXT")
    private String description;

    @OneToMany(fetch = FetchType.LAZY,mappedBy = "supplier")
    private Set<Product> products;
}
