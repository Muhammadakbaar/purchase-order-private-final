package com.backend.purchaseorder.repository;

import com.backend.purchaseorder.entity.PurchaseOrderDetail;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface PurchaseOrderDetailRepository extends JpaRepository<PurchaseOrderDetail, Integer> {
    void deleteAllByPurchaseOrderHeaderId(Integer headerId);
    Optional<PurchaseOrderDetail> findByPurchaseOrderHeaderIdAndItemId(Integer headerId, Integer itemId);
}
