package com.cdio.petshop.entities;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.Parent;
import org.springframework.format.annotation.DateTimeFormat;

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
    @CreationTimestamp
    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private Date createdAt;

    @Column
    private Integer total;

    @Column(columnDefinition = "TEXT")
    private String note;

    // map OneToMany to table BillDetail
    @OneToMany(fetch = FetchType.EAGER, mappedBy = "bill")
    private Set<BillDetail> billDetails;

    // Map ManyToOne to table Bill_Status
    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "bill_status_id", nullable = false,foreignKey = @ForeignKey(name = "fk_bill_billstatus"))
    private BillStatus billStatus;
}
