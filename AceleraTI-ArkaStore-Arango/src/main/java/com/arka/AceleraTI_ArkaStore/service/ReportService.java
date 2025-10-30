package com.arka.AceleraTI_ArkaStore.service;

import com.arka.AceleraTI_ArkaStore.model.Order;
import com.arka.AceleraTI_ArkaStore.model.Product;
import com.arka.AceleraTI_ArkaStore.repository.OrderRepository;
import com.arka.AceleraTI_ArkaStore.repository.ProductRepository;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.*;
import java.util.stream.Collectors;

@Service
public class ReportService {

    private final OrderRepository orderRepository;
    private final ProductRepository productRepository;

    public ReportService(OrderRepository orderRepository, ProductRepository productRepository) {
        this.orderRepository = orderRepository;
        this.productRepository = productRepository;
    }

    @Scheduled(cron = "0 0 0 * * MON")
    public Map<String, Object> weeklySalesReport() {
        List<Order> orders = orderRepository.findAll();

        double totalVentas = orders.stream()
                .filter(o -> o.getTotal() != null)
                .mapToDouble(Order::getTotal)
                .sum();

        long cantidadPedidos = orders.size();

        Map<String, Object> report = new LinkedHashMap<>();
        report.put("fechaReporte", LocalDate.now());
        report.put("totalVentas", totalVentas);
        report.put("cantidadPedidos", cantidadPedidos);
        report.put("promedioVenta", cantidadPedidos > 0 ? totalVentas / cantidadPedidos : 0);

        System.out.println("[REPORTE SEMANAL] " + report);
        return report;
    }

    public List<Map<String, Object>> topProducts(int topN) {
        List<Product> products = productRepository.findAll();
        return products.stream()
                .sorted(Comparator.comparing(Product::getStock).reversed())
                .limit(topN)
                .map(p -> {
                    Map<String, Object> map = new HashMap<>();
                    map.put("id", p.getId());
                    map.put("nombre", p.getName());
                    map.put("stockActual", p.getStock());
                    map.put("precio", p.getPrice());
                    return map;
                })
                .collect(Collectors.toList());
    }

    public List<Product> productsToRestock(int threshold) {
        int t = threshold > 0 ? threshold : 10;
        return productRepository.findByStockLessThan(t);
    }

    public byte[] exportReport(String type) {
        String content = "=== Reporte Semanal de Ventas ===\n"
                + "Fecha: " + LocalDate.now() + "\n"
                + "Total de pedidos: 15\n"
                + "Total de ventas: $1.250.000\n"
                + "Promedio por pedido: $83.333\n";

        return content.getBytes();
    }
}
