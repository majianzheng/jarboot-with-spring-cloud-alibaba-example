package com.jarboot.example.stock.server.controller;

import com.jarboot.example.stock.model.ProductStock;
import com.jarboot.example.stock.server.service.StockService;
import com.mz.jarboot.JarbootTemplate;
import com.mz.jarboot.client.command.CommandResult;
import com.mz.jarboot.common.AnsiLog;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.concurrent.ExecutionException;
import java.util.concurrent.Future;

/**
 * @author jianzhengma
 */
@RequestMapping(value = "/stock/demo")
@RestController
public class StockController {
    @Autowired
    private StockService stockService;
    @Autowired
    private JarbootTemplate jarbootTemplate;

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

    @GetMapping("/command")
    @ResponseBody
    public String command(String sid, String command) throws ExecutionException, InterruptedException {
        Future<CommandResult> future = jarbootTemplate
                .execute(sid, command, event -> AnsiLog.info("jarboot notify: {}", event));
        return future.get().toString();
    }

    @GetMapping("/command/cancel")
    @ResponseBody
    public String cancel(String sid) {
        jarbootTemplate.forceCancel(sid);
        return "ok";
    }
}
