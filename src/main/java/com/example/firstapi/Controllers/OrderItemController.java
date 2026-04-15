package com.example.firstapi.Controllers;

import com.example.firstapi.Services.OrderItemService;
import lombok.AllArgsConstructor;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Validated
@RestController
@AllArgsConstructor
@RequestMapping("/api/v1/orderitem")
public class OrderItemController {
    private final OrderItemService orderItemService;

}
