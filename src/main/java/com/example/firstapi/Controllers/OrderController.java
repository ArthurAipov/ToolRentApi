package com.example.firstapi.Controllers;

import com.example.firstapi.Services.OrderService;
import com.example.firstapi.dtos.OrderDTO;
import jakarta.validation.Valid;
import jakarta.validation.constraints.Positive;
import lombok.AllArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
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
@RequestMapping("/api/v1/orders")
public class OrderController {

    private final OrderService orderService;

    @GetMapping("/{id}")
    public OrderDTO.GetOrderDTO getOrder(@PathVariable @Positive Long id) {
        return orderService.getOrder(id);
    }

    @GetMapping
    public Page<OrderDTO.GetOrderDTO> getOrders(
            @RequestParam(required = false) @Positive Long userId,
            @RequestParam(required = false) Boolean approve,
            Pageable pageable
    ) {
        if (userId != null && approve != null) {
            return orderService.getOrdersByUserIdAndApprove(userId, approve, pageable);
        }

        if (userId != null) {
            return orderService.getOrdersByUserId(userId, pageable);
        }

        if (approve != null) {
            return orderService.getOrdersByApprove(approve, pageable);
        }

        return orderService.getOrders(pageable);
    }

    @PostMapping
    public OrderDTO.GetOrderDTO createOrder(@RequestBody @Valid OrderDTO.PostOrderDTO dto) {
        return orderService.createOrder(dto);
    }

    @PutMapping
    public OrderDTO.GetOrderDTO updateOrder(@RequestBody @Valid OrderDTO.UpdateOrderDTO dto) {
        return orderService.updateOrder(dto);
    }

    @PatchMapping("/{id}/approve")
    public OrderDTO.GetOrderDTO updateApproveStatus(
            @PathVariable @Positive Long id,
            @RequestBody @Valid OrderDTO.UpdateOrderApproveDTO dto
    ) {
        return orderService.updateApproveStatus(id, dto);
    }

    @DeleteMapping("/{id}")
    public void deleteOrder(@PathVariable @Positive Long id) {
        orderService.deleteOrder(id);
    }
}