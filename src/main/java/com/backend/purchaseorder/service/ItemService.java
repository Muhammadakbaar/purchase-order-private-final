package com.backend.purchaseorder.service;

import com.backend.purchaseorder.dto.item.CreateItemRequestDTO;
import com.backend.purchaseorder.dto.item.ItemResponseDTO;
import com.backend.purchaseorder.dto.item.UpdateItemRequestDTO;
import com.backend.purchaseorder.entity.Item;
import com.backend.purchaseorder.repository.ItemRepository;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@Slf4j
@RequiredArgsConstructor
public class ItemService {

    private final ItemRepository itemRepository;

    // ----------------------- GET ALL ITEMS -----------------------
    public List<ItemResponseDTO> getAllItems() {
        log.info("Fetching all items");
        return itemRepository.findAll().stream()
                .map(this::convertToResponseDTO)
                .toList();
    }

    // ----------------------- GET ITEM BY ID -----------------------
    public ItemResponseDTO getItemById(Integer id) {
        log.info("Fetching item by id: {}", id);
        Item item = itemRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Item not found with id: " + id));
        return convertToResponseDTO(item);
    }

    // ----------------------- CREATE ITEM -----------------------
    public ItemResponseDTO createItem(CreateItemRequestDTO request) {
        log.info("Creating item with name: {}", request.getName());
        
        // Validasi duplikasi nama
        if (itemRepository.existsByName(request.getName())) {
            throw new RuntimeException("Item name already exists: " + request.getName());
        }

        // Validasi harga dan biaya
        validatePriceAndCost(request.getPrice(), request.getCost());

        // Jika updatedBy tidak diisi, gunakan createdBy
        String updatedBy = (request.getUpdatedBy() != null && !request.getUpdatedBy().isEmpty()) ?
                request.getUpdatedBy() :
                request.getCreatedBy();

        Item newItem = Item.builder()
                .name(request.getName())
                .description(request.getDescription())
                .price(request.getPrice())
                .cost(request.getCost())
                .createdBy(request.getCreatedBy()) // Wajib dari request
                .updatedBy(updatedBy) // Default ke createdBy jika tidak diisi
                .build();

        Item savedItem = itemRepository.save(newItem);
        return convertToResponseDTO(savedItem);
    }

    // ----------------------- UPDATE ITEM -----------------------
    public ItemResponseDTO updateItem(Integer id, UpdateItemRequestDTO request) {
        log.info("Updating item with id: {}", id);
        Item existingItem = itemRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Item not found with id: " + id));

        // Validasi nama jika diubah
        if (request.getName() != null && !request.getName().equals(existingItem.getName())) {
            if (itemRepository.existsByName(request.getName())) {
                throw new RuntimeException("Item name already exists: " + request.getName());
            }
            existingItem.setName(request.getName());
        }

        // Update field lainnya (jika diisi)
        Optional.ofNullable(request.getDescription()).ifPresent(existingItem::setDescription);
        Optional.ofNullable(request.getPrice()).ifPresent(price -> {
            validatePrice(price);
            existingItem.setPrice(price);
        });
        Optional.ofNullable(request.getCost()).ifPresent(cost -> {
            validateCost(cost);
            existingItem.setCost(cost);
        });

        // Update audit field (updatedBy wajib dari request)
        existingItem.setUpdatedBy(request.getUpdatedBy());

        Item updatedItem = itemRepository.save(existingItem);
        return convertToResponseDTO(updatedItem);
    }

    // ----------------------- DELETE ITEM -----------------------
    public void deleteItem(Integer id) {
        log.info("Deleting item with id: {}", id);
        if (!itemRepository.existsById(id)) {
            throw new RuntimeException("Item not found with id: " + id);
        }
        itemRepository.deleteById(id);
    }

    // ----------------------- VALIDASI -----------------------
    private void validatePriceAndCost(BigDecimal price, BigDecimal cost) {
        validatePrice(price);
        validateCost(cost);
    }

    private void validatePrice(BigDecimal price) {
        if (price.compareTo(BigDecimal.ZERO) < 0) {
            throw new RuntimeException("Price cannot be negative: " + price);
        }
    }

    private void validateCost(BigDecimal cost) {
        if (cost.compareTo(BigDecimal.ZERO) < 0) {
            throw new RuntimeException("Cost cannot be negative: " + cost);
        }
    }

    // ----------------------- KONVERSI KE DTO -----------------------
    private ItemResponseDTO convertToResponseDTO(Item item) {
        return ItemResponseDTO.builder()
                .id(item.getId())
                .name(item.getName())
                .description(item.getDescription())
                .price(item.getPrice())
                .cost(item.getCost())
                .createdBy(item.getCreatedBy())
                .updatedBy(item.getUpdatedBy())
                .createdDatetime(item.getCreatedDatetime())
                .updatedDatetime(item.getUpdatedDatetime())
                .build();
    }
}