package com.cdio.petshop.entities;

import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.util.Date;
import java.util.HashSet;
import java.util.Set;

@Entity
@Table
@Getter
@Setter
public class Bill {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    // map ManyToOne to table User
    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "id_user",nullable = false,foreignKey = @ForeignKey(name = "fk_bill_user"))
    private User user;

    @Temporal(TemporalType.TIMESTAMP)
    private Date createdAt;

    @Column(nullable = false)
    private String status;

    @Column(columnDefinition = "TEXT")
    private String note;

    // map OneToMany to table BillDetail
    @OneToMany(fetch = FetchType.EAGER, mappedBy = "bill")
    private Set<BillDetail> billDetails;
}
