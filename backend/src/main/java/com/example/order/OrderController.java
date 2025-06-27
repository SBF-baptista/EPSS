package com.example.order;

import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/orders")
public class OrderController {

    private final OrderService service;

    public OrderController(OrderService service) {
        this.service = service;
    }

    @GetMapping
    public List<Order> list() {
        return service.getAll();
    }

    @PostMapping
    public Order create(@RequestBody Order order) {
        order.setStatus("Novos pedidos");
        return service.save(order);
    }

    @PatchMapping("/{id}/status")
    public Order updateStatus(@PathVariable Long id, @RequestBody String status) {
        return service.updateStatus(id, status);
    }
}
