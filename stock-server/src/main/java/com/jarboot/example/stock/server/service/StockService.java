package com.jarboot.example.stock.server.service;

import com.jarboot.example.stock.model.ProductStock;

/**
 * @author jianzhengma
 */
public interface StockService {

    /**
     * 测试方法hello
     * @return 结果
     */
    String hello();

    /**
     * 测试——获取库存
     * @param stock 库存信息
     * @return 结果
     */
    String updateProductInventory(ProductStock stock);

    /**
     * 测试——获取库存
     * @param id 商品id
     * @return 库存信息
     */
    ProductStock getProductInventory(String id);
}
