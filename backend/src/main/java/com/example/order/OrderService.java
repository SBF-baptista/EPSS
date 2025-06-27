package com.example.order;

import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class OrderService {

    private final OrderRepository repository;
    private final MoskitService moskitService;
    private final ProductInfoRepository productInfoRepository;

    public OrderService(OrderRepository repository, MoskitService moskitService,
                        ProductInfoRepository productInfoRepository) {
        this.repository = repository;
        this.moskitService = moskitService;
        this.productInfoRepository = productInfoRepository;
    }

    public List<Order> getAll() {
        return repository.findAll();
    }

    public Order save(Order order) {
        moskitService.enrichOrder(order);
        productInfoRepository.findBySku(order.getModelo())
                .ifPresent(info -> {
                    order.setRastreador(info.getRastreador());
                    order.setConfiguracao(info.getConfiguracao());
                });
        return repository.save(order);
    }

    public Order updateStatus(Long id, String status) {
        return repository.findById(id).map(o -> {
            o.setStatus(status);
            return repository.save(o);
        }).orElseThrow();
    }
}
