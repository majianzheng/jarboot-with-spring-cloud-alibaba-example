package com.jarboot.example.order.server.controller;

import com.alibaba.csp.sentinel.annotation.SentinelResource;
import com.alibaba.csp.sentinel.slots.block.BlockException;
import com.jarboot.example.stock.client.StockRpcClient;
import com.jarboot.example.stock.model.ProductStock;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.math.BigDecimal;

/**
 * @author jianzhengma
 */
@RequestMapping(value = "/order/demo")
@RestController
public class OrderController {
    private final Logger logger = LoggerFactory.getLogger(this.getClass());

    @Autowired
    private StockRpcClient stockRpcClient;

    /**
     * 快速失败
     * @param s 输入
     * @return 快速失败
     */
    public String helloFallback(long s) {
        logger.debug("Order helloFallback >>>>>");
        return String.format("Fallback %d", s);
    }

    /**
     * Block 异常处理函数，参数最后多一个 BlockException，其余与原函数一致.
     * @param s 输入
     * @param ex 异常
     * @return 结果
     */
    public String exceptionHandler(long s, BlockException ex) {
        logger.debug("Order s: {}", s);
        logger.debug(ex.getMessage(), ex);
        return "Oops, error occurred at " + s;
    }

    @SentinelResource(value = "order-hello", blockHandler = "exceptionHandler", fallback = "helloFallback")
    @GetMapping("hello")
    @ResponseBody
    public String hello() {
        logger.debug("Order hello >>>");
        String resp = stockRpcClient.hello();
        logger.debug("Stock resp {} >>>", resp);
        return "Stock resp: " + resp;
    }

    @PostMapping
    @SentinelResource
    @ResponseBody
    public String createOrder() {
        logger.debug("To do create order >>>");

        logger.debug("Check stock >>>");
        ProductStock productStock = stockRpcClient.getProductInventory("1");
        String quantity = "0.00";
        if (null == productStock) {
            logger.debug("Get product stock failed.");
        } else {
            quantity = "" + productStock.getQuantity();
            logger.debug("Product info, id:{}, name:{}, quantity:{}",
                    productStock.getId(), productStock.getName(), productStock.getQuantity());
        }

        logger.debug("Create order ok >>>");
        return "ok, quantity = " + quantity;
    }

    @PutMapping
    @SentinelResource
    @ResponseBody
    public String finishOrder(String id) {
        logger.debug("To do get product stock failed. {} >>>", id);
        ProductStock productStock = stockRpcClient.getProductInventory("1");
        if (null == productStock) {
            logger.debug("Get stock is null.");
            return "error";
        }
        BigDecimal quantity = productStock.getQuantity();
        if (null != quantity) {
            productStock.setQuantity(quantity.add(BigDecimal.valueOf(-1.0)));
        }
        stockRpcClient.updateProductInventory(productStock);
        logger.debug("finishOrder >>>");
        return "ok";
    }

    @GetMapping
    @SentinelResource
    @ResponseBody
    public String getOrder(String id) {
        logger.debug("To do get order info {} >>>", id);
        return "ok";
    }

    @DeleteMapping
    @SentinelResource
    @ResponseBody
    public String abandonOrder(String id) {
        logger.debug("To do abandon order {} >>>", id);
        return "ok";
    }
}
