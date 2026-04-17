package com.example.firstapi.Services;

import com.example.firstapi.Exceptions.OrderNotFoundException;
import com.example.firstapi.Exceptions.OrderValidationException;
import com.example.firstapi.Exceptions.UserNotFoundException;
import com.example.firstapi.Models.Order;
import com.example.firstapi.Models.User;
import com.example.firstapi.Repositories.OrderRepository;
import com.example.firstapi.Repositories.UserRepository;
import com.example.firstapi.dtos.OrderDTO;
import lombok.AllArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.time.LocalDate;

@Service
@AllArgsConstructor
public class OrderService {

    private final OrderRepository orderRepository;
    private final UserRepository userRepository;

    public OrderDTO.GetOrderDTO getOrder(Long id) {
        return toDto(getOrderOrThrow(id));
    }

    public Page<OrderDTO.GetOrderDTO> getOrders(Pageable pageable) {
        return orderRepository.findAll(pageable).map(this::toDto);
    }

    public Page<OrderDTO.GetOrderDTO> getOrdersByUserId(Long userId, Pageable pageable) {
        return orderRepository.findByUserId(userId, pageable).map(this::toDto);
    }

    public Page<OrderDTO.GetOrderDTO> getOrdersByApprove(Boolean approve, Pageable pageable) {
        return orderRepository.findByApproved(approve, pageable).map(this::toDto);
    }

    public Page<OrderDTO.GetOrderDTO> getOrdersByUserIdAndApprove(Long userId, Boolean approve, Pageable pageable) {
        return orderRepository.findByUserIdAndApproved(userId, approve, pageable).map(this::toDto);
    }

    public OrderDTO.GetOrderDTO createOrder(OrderDTO.PostOrderDTO dto) {
        validateDates(dto.startDate(), dto.endDate());
        User user = getUserOrThrow(dto.userId());

        Order order = new Order();
        order.setUser(user);
        order.setApproved(dto.approve());
        order.setStartDate(dto.startDate());
        order.setEndDate(dto.endDate());

        return toDto(orderRepository.save(order));
    }

    public OrderDTO.GetOrderDTO updateOrder(OrderDTO.UpdateOrderDTO dto) {
        validateDates(dto.startDate(), dto.endDate());

        Order order = getOrderOrThrow(dto.id());
        User user = getUserOrThrow(dto.userId());

        order.setUser(user);
        order.setApproved(dto.approve());
        order.setStartDate(dto.startDate());
        order.setEndDate(dto.endDate());

        return toDto(orderRepository.save(order));
    }

    public OrderDTO.GetOrderDTO updateApproveStatus(Long id, OrderDTO.UpdateOrderApproveDTO dto) {
        Order order = getOrderOrThrow(id);
        order.setApproved(dto.approve());
        return toDto(orderRepository.save(order));
    }

    public void deleteOrder(Long id) {
        orderRepository.delete(getOrderOrThrow(id));
    }

    private Order getOrderOrThrow(Long id) {
        return orderRepository.findById(id)
                .orElseThrow(() -> new OrderNotFoundException(id));
    }

    private User getUserOrThrow(Long id) {
        return userRepository.findById(id)
                .orElseThrow(() -> new UserNotFoundException(id));
    }

    private void validateDates(LocalDate startDate, LocalDate endDate) {
        if (startDate.isAfter(endDate)) {
            throw OrderValidationException.invalidDates();
        }
    }

    private OrderDTO.GetOrderDTO toDto(Order order) {
        return new OrderDTO.GetOrderDTO(
                order.getId(),
                order.getUser().getId(),
                order.isApproved(),
                order.getStartDate(),
                order.getEndDate(),
                order.getCreatedAt()
        );
    }
}