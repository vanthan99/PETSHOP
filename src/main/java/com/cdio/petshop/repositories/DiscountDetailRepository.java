package com.cdio.petshop.repositories;

import com.cdio.petshop.entities.DiscountDetail;
import com.cdio.petshop.entities.DiscountDetailIdentity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface DiscountDetailRepository extends JpaRepository<DiscountDetail, DiscountDetailIdentity> {
}
