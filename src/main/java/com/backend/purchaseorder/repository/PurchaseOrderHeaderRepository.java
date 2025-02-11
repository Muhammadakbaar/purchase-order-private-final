package com.backend.purchaseorder.repository;

import com.backend.purchaseorder.entity.PurchaseOrderHeader;
import java.util.List;
import java.util.Optional;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface PurchaseOrderHeaderRepository extends JpaRepository<PurchaseOrderHeader, Integer> {
    
    @EntityGraph(attributePaths = "details")
    List<PurchaseOrderHeader> findAll();
    
    @EntityGraph(attributePaths = "details")
    Optional<PurchaseOrderHeader> findById(Integer id);
}

