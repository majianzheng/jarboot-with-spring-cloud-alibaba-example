package com.jarboot.example.order.server.controller;

import com.alibaba.csp.sentinel.annotation.SentinelResource;
import com.alibaba.csp.sentinel.slots.block.BlockException;
import com.jarboot.example.stock.client.StockRpcClient;
import com.jarboot.example.stock.model.ProductStock;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.math.BigDecimal;

@Api(tags="订单接口")
@RequestMapping(value = "/order/demo")
@Controller
public class OrderController {
    private final Logger logger = LoggerFactory.getLogger(this.getClass());

    @Autowired
    private StockRpcClient stockRpcClient;

    // Fallback 函数，函数签名与原函数一致或加一个 Throwable 类型的参数.
    public String helloFallback(long s) {
        logger.debug("Order helloFallback >>>>>");
        return String.format("Fallback %d", s);
    }

    // Block 异常处理函数，参数最后多一个 BlockException，其余与原函数一致.
    public String exceptionHandler(long s, BlockException ex) {
        logger.debug("Order s: {}", s);
        logger.debug(ex.getMessage(), ex);
        return "Oops, error occurred at " + s;
    }

    @ApiOperation(value = "Hello", httpMethod = "GET")
    @SentinelResource(value = "order-hello", blockHandler = "exceptionHandler", fallback = "helloFallback")
    @GetMapping("hello")
    @ResponseBody
    public String hello() {
        logger.debug("Order hello >>>");
        String resp = stockRpcClient.hello();
        logger.debug("Stock resp {} >>>", resp);
        return "Stock resp: " + resp;
    }

    @ApiOperation(value = "创建订单", httpMethod = "POST")
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

    @ApiOperation(value = "完成订单", httpMethod = "PUT")
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
            productStock.setQuantity(quantity.add(new BigDecimal(-1.0)));
        }
        stockRpcClient.updateProductInventory(productStock);
        logger.debug("finishOrder >>>");
        return "ok";
    }

    @ApiOperation(value = "获取订单", httpMethod = "GET")
    @GetMapping
    @SentinelResource
    @ResponseBody
    public String getOrder(String id) {
        logger.debug("To do get order info {} >>>", id);
        return "ok";
    }

    @ApiOperation(value = "作废订单", httpMethod = "DELETE")
    @DeleteMapping
    @SentinelResource
    @ResponseBody
    public String abandonOrder(String id) {
        logger.debug("To do abandon order {} >>>", id);
        return "ok";
    }
}
