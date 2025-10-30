package com.arka.AceleraTI_ArkaStore.controller;

import com.arka.AceleraTI_ArkaStore.model.Product;
import com.arka.AceleraTI_ArkaStore.service.ReportService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/reports")
public class ReportController {

    private final ReportService reportService;

    public ReportController(ReportService reportService){
        this.reportService = reportService;
    }


    @GetMapping("/lowstock")
    public ResponseEntity<List<Product>> lowStock(@RequestParam(defaultValue = "10") int threshold){
        return ResponseEntity.ok(reportService.productsToRestock(threshold));
    }


    @GetMapping("/top-products")
    public ResponseEntity<List<Map<String, Object>>> topProducts(@RequestParam(defaultValue = "10") int top){
        return ResponseEntity.ok(reportService.topProducts(top));
    }


    @GetMapping("/weekly-sales")
    public ResponseEntity<Map<String, Object>> weeklySalesReport(){
        return ResponseEntity.ok(reportService.weeklySalesReport());
    }


    @GetMapping("/export")
    public ResponseEntity<byte[]> exportReport(@RequestParam String type) {
        byte[] file = reportService.exportReport(type);
        String contentType = type.equalsIgnoreCase("pdf") ? "application/pdf" : "text/csv";

        return ResponseEntity.ok()
                .header("Content-Disposition", "attachment; filename=reporte." + type)
                .header("Content-Type", contentType)
                .body(file);
    }
}
