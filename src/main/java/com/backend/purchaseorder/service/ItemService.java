package com.backend.purchaseorder.service;

import com.backend.purchaseorder.dto.item.CreateItemRequestDTO;
import com.backend.purchaseorder.dto.item.ItemResponseDTO;
import com.backend.purchaseorder.dto.item.UpdateItemRequestDTO;
import com.backend.purchaseorder.entity.Item;
import com.backend.purchaseorder.repository.ItemRepository;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;

@Service
@Slf4j
@RequiredArgsConstructor
public class ItemService {

    private final ItemRepository itemRepository;

    public List<ItemResponseDTO> getAllItems() {
        log.info("Fetching all items");
        return itemRepository.findAll().stream()
                .map(this::convertToResponseDTO)
                .toList();
    }

    public ItemResponseDTO getItemById(Integer id) {
        log.info("Fetching item by id: {}", id);
        Item item = itemRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Item not found with id: " + id));
        return convertToResponseDTO(item);
    }

    public ItemResponseDTO createItem(CreateItemRequestDTO request) {
        log.info("Creating item with name: {}", request.getName());
        
        if (itemRepository.existsByName(request.getName())) {
            throw new RuntimeException("Item name already exists: " + request.getName());
        }

        validatePriceAndCost(request.getPrice(), request.getCost());

        String updatedBy = (request.getUpdatedBy() != null && !request.getUpdatedBy().isEmpty()) ?
                request.getUpdatedBy() :
                request.getCreatedBy();

        Item newItem = Item.builder()
                .name(request.getName())
                .description(request.getDescription())
                .price(request.getPrice())
                .cost(request.getCost())
                .createdBy(request.getCreatedBy())
                .updatedBy(updatedBy)
                .build();

        Item savedItem = itemRepository.save(newItem);
        return convertToResponseDTO(savedItem);
    }

    public ItemResponseDTO updateItem(Integer id, UpdateItemRequestDTO request) {
        log.info("Updating item with id: {}", id);
        Item existingItem = itemRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Item not found with id: " + id));

        if (request.getName() != null && !request.getName().equals(existingItem.getName())) {
            if (itemRepository.existsByName(request.getName())) {
                throw new RuntimeException("Item name already exists: " + request.getName());
            }
            existingItem.setName(request.getName());
        }

        Optional.ofNullable(request.getDescription()).ifPresent(existingItem::setDescription);
        Optional.ofNullable(request.getPrice()).ifPresent(price -> {
            validatePrice(price);
            existingItem.setPrice(price);
        });
        Optional.ofNullable(request.getCost()).ifPresent(cost -> {
            validateCost(cost);
            existingItem.setCost(cost);
        });

        existingItem.setUpdatedBy(request.getUpdatedBy());

        Item updatedItem = itemRepository.save(existingItem);
        return convertToResponseDTO(updatedItem);
    }

    public void deleteItem(Integer id) {
        log.info("Deleting item with id: {}", id);
        if (!itemRepository.existsById(id)) {
            throw new RuntimeException("Item not found with id: " + id);
        }
        itemRepository.deleteById(id);
    }

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
