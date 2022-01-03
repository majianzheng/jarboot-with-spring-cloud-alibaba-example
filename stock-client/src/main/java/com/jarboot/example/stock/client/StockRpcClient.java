package com.jarboot.example.stock.client;

import com.jarboot.example.stock.model.ProductStock;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.*;

/**
 * @author jianzhengma
 */
@FeignClient(value = "stock-server", path = "/stock/demo")
public interface StockRpcClient {
    /**
     * 测试接口hello
     * @return 测试输出
     */
    @GetMapping("/hello")
    String hello();

    /**
     * 测试——获取库存
     * @param id 商品id
     * @return 库存信息
     */
    @GetMapping
    ProductStock getProductInventory(@RequestParam("id") String id);

    /**
     * 测试——更新库存
     * @param stock 库存信息
     * @return 执行结果
     */
    @PutMapping
    String updateProductInventory(@RequestBody ProductStock stock);
}
