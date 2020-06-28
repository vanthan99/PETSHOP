package com.cdio.petshop.repositories;

import com.cdio.petshop.entities.BillStatus;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface BillStatusRepository extends JpaRepository<BillStatus,Long>{
}
