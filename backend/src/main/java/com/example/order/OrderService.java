package com.example.order;

import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class OrderService {

    private final OrderRepository repository;

    public OrderService(OrderRepository repository) {
        this.repository = repository;
    }

    public List<Order> getAll() {
        return repository.findAll();
    }

    public Order save(Order order) {
        return repository.save(order);
    }

    public Order updateStatus(Long id, String status) {
        return repository.findById(id).map(o -> {
            o.setStatus(status);
            return repository.save(o);
        }).orElseThrow();
    }
}
