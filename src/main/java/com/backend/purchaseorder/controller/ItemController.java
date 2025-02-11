package com.backend.purchaseorder.controller;

import com.backend.purchaseorder.dto.item.CreateItemRequestDTO;
import com.backend.purchaseorder.dto.item.ItemResponseDTO;
import com.backend.purchaseorder.dto.item.UpdateItemRequestDTO;
import com.backend.purchaseorder.service.ItemService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/items")
@Slf4j
@RequiredArgsConstructor
public class ItemController {

    private final ItemService itemService;

    @GetMapping
    public ResponseEntity<List<ItemResponseDTO>> getAllItems() {
        log.info("Fetching all items");
        return ResponseEntity.ok(itemService.getAllItems());
    }

    @GetMapping("/{id}")
    public ResponseEntity<ItemResponseDTO> getItemById(@PathVariable Integer id) {
        log.info("Fetching item by id: {}", id);
        return ResponseEntity.ok(itemService.getItemById(id));
    }

    @PostMapping
    public ResponseEntity<ItemResponseDTO> createItem(@Valid @RequestBody CreateItemRequestDTO request) {
        log.info("Creating item with name: {}", request.getName());
        return ResponseEntity.status(HttpStatus.CREATED).body(itemService.createItem(request));
    }

    @PutMapping("/{id}")
    public ResponseEntity<ItemResponseDTO> updateItem(
        @PathVariable Integer id,
        @Valid @RequestBody UpdateItemRequestDTO request
    ) {
        log.info("Updating item with id: {}", id);
        return ResponseEntity.ok(itemService.updateItem(id, request));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteItem(@PathVariable Integer id) {
        log.info("Deleting item with id: {}", id);
        itemService.deleteItem(id);
        return ResponseEntity.noContent().build();
    }
}
