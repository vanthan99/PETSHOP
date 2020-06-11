package com.cdio.petshop.entities;

import lombok.Getter;
import lombok.Setter;


import javax.persistence.*;
import java.util.Set;

@Entity
@Getter
@Setter
@Table
public class User {
    @Id
    private String username;

    @Column(nullable = false,length = 60)
    private String password;

    @Column(length = 50)
    private String fullName;

    @Column(length = 10)
    private String phoneNumber;

    @Column
    private String addressOfDelivery;

    @Column
    private String personalAddress;

    @Column
    private String status;

    @Column(columnDefinition = "TEXT")
    private String notes;


    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "role_id", nullable = false,foreignKey = @ForeignKey(name = "fk_user_role"))
    private Role role;

    @OneToMany(fetch = FetchType.LAZY,mappedBy = "user")
    private Set<Bill> bills;
}
