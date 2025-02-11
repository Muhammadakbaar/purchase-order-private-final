package com.backend.purchaseorder.service;

import com.backend.purchaseorder.dto.po.PurchaseOrderDetailDTO;
import com.backend.purchaseorder.dto.po.PurchaseOrderHeaderDTO;
import com.backend.purchaseorder.entity.PurchaseOrderDetail;
import com.backend.purchaseorder.entity.PurchaseOrderHeader;
import com.backend.purchaseorder.entity.Item;
import com.backend.purchaseorder.repository.ItemRepository;
import com.backend.purchaseorder.repository.PurchaseOrderDetailRepository;
import com.backend.purchaseorder.repository.PurchaseOrderHeaderRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class PurchaseOrderService {

    private final PurchaseOrderHeaderRepository poHRepository;
    private final PurchaseOrderDetailRepository poDRepository;
    private final ItemRepository itemRepository;

    @Transactional(readOnly = true)
    public List<PurchaseOrderHeaderDTO> getAllPOs() {
        return poHRepository.findAll().stream()
                .map(this::convertToDTO)
                .toList();
    }

    @Transactional(readOnly = true)
    public PurchaseOrderHeaderDTO getPOById(int id) {
        return poHRepository.findById(id)
                .map(this::convertToDTO)
                .orElseThrow(() -> new RuntimeException("Purchase order not found with ID: " + id));
    }

    @Transactional
    public PurchaseOrderHeaderDTO createPO(PurchaseOrderHeaderDTO poHDTO) {
        Map<Integer, Integer> itemQuantityMap = poHDTO.getPoDetails().stream()
            .collect(Collectors.toMap(
                PurchaseOrderDetailDTO::getItemId,
                PurchaseOrderDetailDTO::getItemQty,
                Integer::sum
            ));

        List<Integer> itemIds = new ArrayList<>(itemQuantityMap.keySet());
        List<Item> existingItems = itemRepository.findAllById(itemIds);
        if (existingItems.size() != itemIds.size()) {
            throw new RuntimeException("One or more items not found");
        }

        PurchaseOrderHeader poH = new PurchaseOrderHeader();
        poH.setCreatedBy(poHDTO.getCreatedBy());
        poH.setUpdatedBy(poHDTO.getCreatedBy());
        poH.setDescription(poHDTO.getDescription());
        poH.setDatetime(LocalDateTime.now());

        List<PurchaseOrderDetail> poDetails = itemQuantityMap.entrySet().stream()
            .map(entry -> createDetail(entry.getKey(), entry.getValue(), poH, existingItems))
            .toList();

        BigDecimal totalCost = calculateTotalCost(poDetails);
        BigDecimal totalPrice = calculateTotalPrice(poDetails);

        poH.setTotalCost(totalCost);
        poH.setTotalPrice(totalPrice);
        poH.setDetails(poDetails);

        PurchaseOrderHeader savedPoH = poHRepository.save(poH);
        return convertToDTO(savedPoH);
    }

    @Transactional
    public PurchaseOrderHeaderDTO updatePO(int id, PurchaseOrderHeaderDTO poHDTO) {
        PurchaseOrderHeader poH = poHRepository.findById(id)
            .orElseThrow(() -> new RuntimeException("Purchase order not found with ID: " + id));

        Map<Integer, Integer> itemQuantityMap = poHDTO.getPoDetails().stream()
            .collect(Collectors.toMap(
                PurchaseOrderDetailDTO::getItemId,
                PurchaseOrderDetailDTO::getItemQty,
                Integer::sum
            ));

        List<Integer> itemIds = new ArrayList<>(itemQuantityMap.keySet());
        List<Item> existingItems = itemRepository.findAllById(itemIds);
        if (existingItems.size() != itemIds.size()) {
            throw new RuntimeException("One or more items not found");
        }

        poH.setUpdatedBy(poHDTO.getUpdatedBy());
        poH.setDescription(poHDTO.getDescription());
        poH.setDatetime(LocalDateTime.now());

        itemQuantityMap.forEach((itemId, qty) -> {
            poDRepository.findByPurchaseOrderHeaderIdAndItemId(poH.getId(), itemId)
                .ifPresentOrElse(
                    detail -> updateExistingDetail(detail, qty),
                    () -> addNewDetail(poH, itemId, qty, existingItems)
                );
        });

        poH.getDetails().removeIf(detail -> !itemQuantityMap.containsKey(detail.getItemId()));

        BigDecimal totalCost = calculateTotalCost(poH.getDetails());
        BigDecimal totalPrice = calculateTotalPrice(poH.getDetails());
        poH.setTotalCost(totalCost);
        poH.setTotalPrice(totalPrice);

        return convertToDTO(poHRepository.save(poH));
    }

    @Transactional
    public void deletePO(int id) {
        PurchaseOrderHeader poH = poHRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Purchase order not found with ID: " + id));
        
        poDRepository.deleteAllByPurchaseOrderHeaderId(id);
        poHRepository.delete(poH);
    }

    private PurchaseOrderDetail createDetail(Integer itemId, Integer qty, PurchaseOrderHeader header, List<Item> items) {
        Item item = items.stream()
            .filter(i -> i.getId().equals(itemId))
            .findFirst()
            .orElseThrow(() -> new RuntimeException("Item not found with ID: " + itemId));

        return PurchaseOrderDetail.builder()
            .purchaseOrderHeader(header)
            .itemId(itemId)
            .itemQty(qty)
            .itemCost(item.getCost())
            .itemPrice(item.getPrice())
            .build();
    }

    private void updateExistingDetail(PurchaseOrderDetail detail, Integer qty) {
        detail.setItemQty(qty);
        poDRepository.save(detail);
    }

    private void addNewDetail(PurchaseOrderHeader header, Integer itemId, Integer qty, List<Item> items) {
        Item item = items.stream()
                .filter(i -> i.getId().equals(itemId))
                .findFirst()
                .orElseThrow(() -> new RuntimeException("Item not found with ID: " + itemId));

        PurchaseOrderDetail detail = PurchaseOrderDetail.builder()
                .purchaseOrderHeader(header)
                .itemId(itemId)
                .itemQty(qty)
                .itemCost(item.getCost())
                .itemPrice(item.getPrice())
                .build();
        
        poDRepository.save(detail);
    }

    private BigDecimal calculateTotalCost(List<PurchaseOrderDetail> details) {
        return details.stream()
                .map(d -> d.getItemCost().multiply(BigDecimal.valueOf(d.getItemQty())))
                .reduce(BigDecimal.ZERO, BigDecimal::add);
    }

    private BigDecimal calculateTotalPrice(List<PurchaseOrderDetail> details) {
        return details.stream()
                .map(d -> d.getItemPrice().multiply(BigDecimal.valueOf(d.getItemQty())))
                .reduce(BigDecimal.ZERO, BigDecimal::add);
    }

    private PurchaseOrderHeaderDTO convertToDTO(PurchaseOrderHeader poH) {
        return PurchaseOrderHeaderDTO.builder()
                .id(poH.getId())
                .datetime(poH.getDatetime())
                .description(poH.getDescription())
                .totalCost(poH.getTotalCost())
                .totalPrice(poH.getTotalPrice())
                .createdBy(poH.getCreatedBy())
                .updatedBy(poH.getUpdatedBy())
                .poDetails(convertDetailsToDTO(poH.getDetails()))
                .build();
    }

    private List<PurchaseOrderDetailDTO> convertDetailsToDTO(List<PurchaseOrderDetail> details) {
        return details.stream()
                .map(detail -> PurchaseOrderDetailDTO.builder()
                        .id(detail.getId())
                        .pohId(detail.getPurchaseOrderHeader().getId())
                        .itemId(detail.getItemId())
                        .itemQty(detail.getItemQty())
                        .itemCost(detail.getItemCost())
                        .itemPrice(detail.getItemPrice())
                        .build())
                .toList();
    }
}
