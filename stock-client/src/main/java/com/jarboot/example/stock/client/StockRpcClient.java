package com.jarboot.example.stock.client;

import com.jarboot.example.stock.model.ProductStock;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.*;

@FeignClient(value = "stock-server")
@RequestMapping(value = "/stock/demo")
public interface StockRpcClient {
    @GetMapping("/hello")
    String hello();

    @GetMapping
    ProductStock getProductInventory(@RequestParam("id") String id);

    @PutMapping
    String updateProductInventory(@RequestBody ProductStock stock);
}
