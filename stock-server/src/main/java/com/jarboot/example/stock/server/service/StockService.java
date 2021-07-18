package com.jarboot.example.stock.server.service;

import com.jarboot.example.stock.model.ProductStock;

public interface StockService {

    String hello();

    String updateProductInventory(ProductStock stock);

    ProductStock getProductInventory(String id);
}
