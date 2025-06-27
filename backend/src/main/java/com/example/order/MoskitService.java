package com.example.order;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.*;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.*;

@Service
public class MoskitService {

    private final RestTemplate restTemplate;
    private final String baseUrl;
    private final String apiKey;

    public MoskitService(@Value("${moskit.api.base-url:https://api.ms.prod.moskit.services/v2}") String baseUrl,
                         @Value("${moskit.api.key:}") String apiKey) {
        this.baseUrl = baseUrl;
        this.apiKey = apiKey;
        this.restTemplate = new RestTemplate();
    }

    public void enrichOrder(Order order) {
        if (order == null || order.getModelo() == null) {
            return;
        }
        MoskitProduct product = findProductBySku(order.getModelo());
        if (product == null) {
            return;
        }
        String name = product.name();
        String sku = product.sku();
        if (name != null && sku != null && name.endsWith(sku)) {
            order.setMarca(name.substring(0, name.length() - sku.length()).trim());
        } else {
            order.setMarca(name);
        }
        order.setModelo(sku);
        if (product.quantity() != null) {
            order.setQuantidade(product.quantity());
        }
        if (product.vehicleType() != null) {
            order.setTipoVeiculo(product.vehicleType());
        }
    }

    private MoskitProduct findProductBySku(String sku) {
        String url = baseUrl + "/products/search";

        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        headers.set("apikey", apiKey);

        Map<String, Object> req = new HashMap<>();
        List<Map<String, Object>> conditions = new ArrayList<>();
        conditions.add(Map.of("field", "sku", "operator", "contains", "value", sku));
        conditions.add(Map.of("field", "active", "operator", "equals", "value", true));
        req.put("conditions", conditions);
        req.put("limit", 1);
        req.put("offset", 0);
        req.put("orders", List.of(Map.of("field", "name", "order", "ASC")));

        HttpEntity<Map<String, Object>> entity = new HttpEntity<>(req, headers);
        @SuppressWarnings("unchecked")
        Map<String, Object> resp = restTemplate.postForObject(url, entity, Map.class);
        if (resp == null) {
            return null;
        }
        Object dataObj = resp.get("data");
        if (!(dataObj instanceof List<?> list) || list.isEmpty()) {
            return null;
        }
        Object prodObj = list.get(0);
        if (!(prodObj instanceof Map<?, ?> prod)) {
            return null;
        }
        Long id = prod.get("id") instanceof Number n ? n.longValue() : null;
        String name = prod.get("name") != null ? prod.get("name").toString() : null;
        String outSku = prod.get("sku") != null ? prod.get("sku").toString() : null;
        Integer qty = prod.get("quantity") instanceof Number n2 ? n2.intValue() : null;
        String vehicleType = prod.get("vehicleType") != null ? prod.get("vehicleType").toString() : null;
        return new MoskitProduct(id, name, outSku, qty, vehicleType);
    }
}
