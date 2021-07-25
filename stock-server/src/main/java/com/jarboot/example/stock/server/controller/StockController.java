package com.jarboot.example.stock.server.controller;

import com.jarboot.example.stock.model.ProductStock;
import com.jarboot.example.stock.server.service.StockService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

/**
 * @author jianzhengma
 */
@RequestMapping(value = "/stock/demo")
@RestController
public class StockController {
    @Autowired
    private StockService stockService;

    @GetMapping("hello")
    @ResponseBody
    public String hello() {
        return stockService.hello();
    }

    @PutMapping
    @ResponseBody
    public String updateProductInventory(@RequestBody ProductStock stock) {
        return stockService.updateProductInventory(stock);
    }

    @GetMapping
    @ResponseBody
    public ProductStock getProductInventory(@RequestParam("id") String id) {
        return stockService.getProductInventory(id);
    }
}
