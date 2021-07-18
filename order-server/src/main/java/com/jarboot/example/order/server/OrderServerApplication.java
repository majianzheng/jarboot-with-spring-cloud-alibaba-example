package com.jarboot.example.order.server;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.cloud.openfeign.EnableFeignClients;

@SpringBootApplication(scanBasePackages = "com.jarboot.example")
@EnableDiscoveryClient
@EnableFeignClients(basePackages = "com.jarboot.example.stock.client")
public class OrderServerApplication {
	public static void main(String[] args) {
		SpringApplication.run(OrderServerApplication.class, args);
	}
}
