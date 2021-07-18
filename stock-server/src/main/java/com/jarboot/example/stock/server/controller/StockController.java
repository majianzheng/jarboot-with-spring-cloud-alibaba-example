package com.jarboot.example.stock.server.controller;

import com.jarboot.example.stock.model.ProductStock;
import com.jarboot.example.stock.server.service.StockService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

@Api(tags="库存接口")
@RequestMapping(value = "/stock/demo")
@Controller
public class StockController {
    @Autowired
    private StockService stockService;

    @ApiOperation(value = "Hello", httpMethod = "GET")
    @GetMapping("hello")
    @ResponseBody
    public String hello() {
        return stockService.hello();
    }

    @ApiOperation(value = "更新产品库存", httpMethod = "PUT")
    @PutMapping
    @ResponseBody
    public String updateProductInventory(@RequestBody ProductStock stock) {
        return stockService.updateProductInventory(stock);
    }

    @ApiOperation(value = "获取产品库存", httpMethod = "GET")
    @GetMapping
    @ResponseBody
    public ProductStock getProductInventory(@RequestParam("id") String id) {
        return stockService.getProductInventory(id);
    }
}
