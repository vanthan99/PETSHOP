package com.cdio.petshop.entities;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.util.Set;

@Entity
@Getter
@Setter
@Table
public class Product {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column
    private String name;

    @Column
    private String imagePath;

    @Column
    private Integer unitPrice;

    @Column
    private Integer promotionPrice;

    @Column(length = 10)
    private String unit;

    @Column(columnDefinition = "TEXT")
    private String description;

    // map ManyToOne to table supplier
    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "supplierId",nullable = false,foreignKey = @ForeignKey(name = "fk_product_supplier"))
    private Supplier supplier;

//    map ManyToOne to table Category
    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "categoryId",nullable = false,foreignKey = @ForeignKey(name = "fk_product_category"))
    private Category category;

    // liên kết với bảng BillDetail
    @OneToMany(fetch = FetchType.LAZY, mappedBy = "product")
    private Set<BillDetail> billDetails;
}
