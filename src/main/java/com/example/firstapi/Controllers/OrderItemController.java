package com.example.firstapi.Controllers;

import com.example.firstapi.Services.OrderItemService;
import com.example.firstapi.dtos.OrderItemDTO;
import jakarta.validation.Valid;
import jakarta.validation.constraints.Positive;
import lombok.AllArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@Validated
@RestController
@AllArgsConstructor
@RequestMapping("/api/v1/order-items")
public class OrderItemController {

    private final OrderItemService orderItemService;

    @GetMapping("/{id}")
    public OrderItemDTO.GetOrderItemDTO getOrderItem(@PathVariable @Positive Long id) {
        return orderItemService.getOrderItem(id);
    }

    @GetMapping
    public Page<OrderItemDTO.GetOrderItemDTO> getOrderItems(
            @RequestParam(required = false) @Positive Long orderId,
            @RequestParam(required = false) @Positive Long toolId,
            Pageable pageable
    ) {
        if (orderId != null && toolId != null) {
            return orderItemService.getOrderItemsByOrderIdAndToolId(orderId, toolId, pageable);
        }

        if (orderId != null) {
            return orderItemService.getOrderItemsByOrderId(orderId, pageable);
        }

        if (toolId != null) {
            return orderItemService.getOrderItemsByToolId(toolId, pageable);
        }

        return orderItemService.getOrderItems(pageable);
    }

    @PostMapping
    public OrderItemDTO.GetOrderItemDTO createOrderItem(@RequestBody @Valid OrderItemDTO.PostOrderItemDTO dto) {
        return orderItemService.createOrderItem(dto);
    }

    @PutMapping
    public OrderItemDTO.GetOrderItemDTO updateOrderItem(@RequestBody @Valid OrderItemDTO.UpdateOrderItemDTO dto) {
        return orderItemService.updateOrderItem(dto);
    }

    @DeleteMapping("/{id}")
    public void deleteOrderItem(@PathVariable @Positive Long id) {
        orderItemService.deleteOrderItem(id);
    }


}


