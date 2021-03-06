package com.cdio.petshop.repositories;

import com.cdio.petshop.entities.Product;
import com.fasterxml.jackson.databind.annotation.JsonAppend;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ProductRepository extends JpaRepository<Product,Long> {

    @Query("SELECT p FROM Product p WHERE lower(p.name) LIKE CONCAT('%',lower(:keyword),'%')" +
            "OR TRIM(p.unitPrice) LIKE CONCAT('%',:keyword,'%')" +
            "OR TRIM(p.promotionPrice) LIKE CONCAT('%',:keyword,'%')" +
            "OR lower(p.category.name)  LIKE CONCAT('%',lower(:keyword),'%')" +
            "OR lower(p.supplier.name)  LIKE CONCAT('%',lower(:keyword),'%')")
    List<Product> findAllByKeyword(String keyword);

    @Query("SELECT p FROM Product p WHERE lower(p.name) LIKE CONCAT('%',lower(:keyword),'%')")
    List<Product> findByProductName(String keyword);

    @Query("SELECT p FROM Product p WHERE p.enable=2")
    List<Product> finAllProductActive();

    @Query("SELECT p FROM Product p WHERE p.enable=1")
    List<Product> finAllProductNotActive();


}
