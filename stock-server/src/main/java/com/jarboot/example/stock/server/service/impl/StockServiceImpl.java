package com.jarboot.example.stock.server.service.impl;

import com.alibaba.csp.sentinel.annotation.SentinelResource;
import com.alibaba.csp.sentinel.slots.block.BlockException;
import com.jarboot.example.stock.model.ProductStock;
import com.jarboot.example.stock.server.service.StockService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.cloud.context.config.annotation.RefreshScope;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.*;

import java.math.BigDecimal;

@RefreshScope
@Service
public class StockServiceImpl implements StockService {
    private final Logger logger = LoggerFactory.getLogger(this.getClass());

    @Value("${product-id:1}")
    private String id;

    @Value("${product-name:product-name}")
    private String name;

    @Value("${product-quantity:10000}")
    private BigDecimal quantity;

    // Fallback 函数，函数签名与原函数一致或加一个 Throwable 类型的参数.
    public String helloFallback(long s) {
        logger.debug("Stock helloFallback >>>>>");
        return String.format("Fallback %d", s);
    }

    // Block 异常处理函数，参数最后多一个 BlockException，其余与原函数一致.
    public String exceptionHandler(long s, BlockException ex) {
        logger.debug("Stock s: {}", s);
        logger.debug(ex.getMessage(), ex);
        return "Oops, error occurred at " + s;
    }

    @SentinelResource(value = "stock-hello", blockHandler = "exceptionHandler", fallback = "helloFallback")
    @Override
    public String hello() {
        return "Hello, how are you.";
    }

    @SentinelResource
    @Override
    public String updateProductInventory(@RequestBody ProductStock stock) {
        logger.debug("Update product inventory.");
        this.id = stock.getId();
        this.quantity = stock.getQuantity();
        this.name = stock.getName();
        return "ok";
    }

    @SentinelResource
    @Override
    public ProductStock getProductInventory(@RequestParam("id") String id) {
        logger.debug("Demo ignore id: {}", id);
        ProductStock productStock = new ProductStock();
        // 使用配置中心
        productStock.setId(this.id);
        productStock.setName(this.name);
        productStock.setQuantity(this.quantity);
        return productStock;
    }
}
