package com.example.firstapi.Services;

import com.example.firstapi.Exceptions.OrderItemNotFoundException;
import com.example.firstapi.Exceptions.OrderItemValidationException;
import com.example.firstapi.Exceptions.OrderModificationNotAllowedException;
import com.example.firstapi.Exceptions.DuplicateToolInOrderException;
import com.example.firstapi.Exceptions.OrderNotFoundException;
import com.example.firstapi.Exceptions.ToolNotFoundException;
import com.example.firstapi.Exceptions.ToolAlreadyReservedException;
import com.example.firstapi.Models.Order;
import com.example.firstapi.Models.OrderItem;
import com.example.firstapi.Models.Tool;
import com.example.firstapi.Repositories.OrderItemRepository;
import com.example.firstapi.Repositories.OrderRepository;
import com.example.firstapi.Repositories.ToolRepository;
import com.example.firstapi.dtos.OrderItemDTO;
import lombok.AllArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;

@Service
@AllArgsConstructor
public class OrderItemService {

    private final OrderItemRepository orderItemRepository;
    private final OrderRepository orderRepository;
    private final ToolRepository toolRepository;

    public OrderItemDTO.GetOrderItemDTO getOrderItem(Long id) {
        return toDto(getOrderItemOrThrow(id));
    }

    public Page<OrderItemDTO.GetOrderItemDTO> getOrderItems(Pageable pageable) {
        return orderItemRepository.findAll(pageable).map(this::toDto);
    }

    public Page<OrderItemDTO.GetOrderItemDTO> getOrderItemsByOrderId(Long orderId, Pageable pageable) {
        return orderItemRepository.findByOrderId(orderId, pageable).map(this::toDto);
    }

    public Page<OrderItemDTO.GetOrderItemDTO> getOrderItemsByToolId(Long toolId, Pageable pageable) {
        return orderItemRepository.findByToolId(toolId, pageable).map(this::toDto);
    }

    public Page<OrderItemDTO.GetOrderItemDTO> getOrderItemsByOrderIdAndToolId(Long orderId, Long toolId, Pageable pageable) {
        return orderItemRepository.findByOrderIdAndToolId(orderId, toolId, pageable).map(this::toDto);
    }

    @Transactional
    public OrderItemDTO.GetOrderItemDTO createOrderItem(OrderItemDTO.PostOrderItemDTO dto) {
        validateDates(dto.startDate(), dto.endDate());

        Order order = getOrderOrThrow(dto.orderId());
        validateOrderIsEditable(order);

        Tool tool = getToolOrThrow(dto.toolId());
        validateToolAvailability(dto.toolId(), dto.startDate(), dto.endDate());
        validateDuplicateToolInOrder(dto.orderId(), dto.toolId());

        OrderItem orderItem = new OrderItem();
        orderItem.setOrder(order);
        orderItem.setTool(tool);
        orderItem.setStartDate(dto.startDate());
        orderItem.setEndDate(dto.endDate());
        orderItem.setPrice(dto.price());

        return toDto(orderItemRepository.save(orderItem));
    }

    @Transactional
    public OrderItemDTO.GetOrderItemDTO updateOrderItem(OrderItemDTO.UpdateOrderItemDTO dto) {
        validateDates(dto.startDate(), dto.endDate());

        OrderItem orderItem = getOrderItemOrThrow(dto.id());
        Order order = getOrderOrThrow(dto.orderId());
        validateOrderIsEditable(order);

        Tool tool = getToolOrThrow(dto.toolId());
        validateToolAvailabilityForUpdate(dto.id(), dto.toolId(), dto.startDate(), dto.endDate());
        validateDuplicateToolInOrderForUpdate(dto.id(), dto.orderId(), dto.toolId());

        orderItem.setOrder(order);
        orderItem.setTool(tool);
        orderItem.setStartDate(dto.startDate());
        orderItem.setEndDate(dto.endDate());
        orderItem.setPrice(dto.price());

        return toDto(orderItemRepository.save(orderItem));
    }

    @Transactional
    public void deleteOrderItem(Long id) {
        OrderItem orderItem = getOrderItemOrThrow(id);
        validateOrderIsEditable(orderItem.getOrder());
        orderItemRepository.delete(orderItem);
    }

    private OrderItem getOrderItemOrThrow(Long id) {
        return orderItemRepository.findById(id)
                .orElseThrow(() -> new OrderItemNotFoundException(id));
    }

    private Order getOrderOrThrow(Long id) {
        return orderRepository.findById(id)
                .orElseThrow(() -> new OrderNotFoundException(id));
    }

    private Tool getToolOrThrow(Long id) {
        return toolRepository.findById(id)
                .orElseThrow(() -> new ToolNotFoundException(id));
    }

    private void validateDates(LocalDate startDate, LocalDate endDate) {
        if (startDate.isAfter(endDate)) {
            throw OrderItemValidationException.invalidDates();
        }
    }

    private void validateOrderIsEditable(Order order) {
        if (order.isApproved()) {
            throw OrderModificationNotAllowedException.approvedOrder(order.getId());
        }
    }

    private void validateToolAvailability(Long toolId, LocalDate startDate, LocalDate endDate) {
        if (orderItemRepository.existsOverlappingByToolId(toolId, startDate, endDate)) {
            throw new ToolAlreadyReservedException(toolId, startDate, endDate);
        }
    }

    private void validateToolAvailabilityForUpdate(Long orderItemId, Long toolId, LocalDate startDate, LocalDate endDate) {
        if (orderItemRepository.existsOverlappingByToolIdExcludingId(toolId, orderItemId, startDate, endDate)) {
            throw new ToolAlreadyReservedException(toolId, startDate, endDate);
        }
    }

    private void validateDuplicateToolInOrder(Long orderId, Long toolId) {
        if (orderItemRepository.existsByOrderIdAndToolId(orderId, toolId)) {
            throw new DuplicateToolInOrderException(orderId, toolId);
        }
    }

    private void validateDuplicateToolInOrderForUpdate(Long orderItemId, Long orderId, Long toolId) {
        OrderItem existingOrderItem = getOrderItemOrThrow(orderItemId);
        boolean sameOrderAndTool = existingOrderItem.getOrder().getId().equals(orderId)
                && existingOrderItem.getTool().getId() == (toolId);

        if (!sameOrderAndTool && orderItemRepository.existsByOrderIdAndToolId(orderId, toolId)) {
            throw new DuplicateToolInOrderException(orderId, toolId);
        }
    }

    private OrderItemDTO.GetOrderItemDTO toDto(OrderItem orderItem) {
        return new OrderItemDTO.GetOrderItemDTO(
                orderItem.getId(),
                orderItem.getOrder().getId(),
                orderItem.getTool().getId(),
                orderItem.getStartDate(),
                orderItem.getEndDate(),
                orderItem.getPrice()
        );
    }
}