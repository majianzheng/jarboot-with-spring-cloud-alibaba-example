package com.jarboot.example.stock.server;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;

/**
 * @author jianzhengma
 */
@SpringBootApplication(scanBasePackages = "com.jarboot.example.stock.server")
@EnableDiscoveryClient
public class StockServerApplication {
	public static void main(String[] args) {
		SpringApplication.run(StockServerApplication.class, args);
	}
}
