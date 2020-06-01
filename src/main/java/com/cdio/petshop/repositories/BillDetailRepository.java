package com.cdio.petshop.repositories;

import com.cdio.petshop.entities.BillDetail;
import com.cdio.petshop.entities.BillDetailIdentity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.stereotype.Repository;

@Repository
public interface BillDetailRepository extends JpaRepository< BillDetail, BillDetailIdentity > {
}
