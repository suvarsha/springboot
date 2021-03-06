package com.example.demo.stockprice;

import java.util.List;



import org.springframework.cloud.openfeign.FeignClient;

import org.springframework.web.bind.annotation.GetMapping;

import org.springframework.web.bind.annotation.PathVariable;

import org.springframework.web.bind.annotation.RequestMapping;

import org.springframework.web.bind.annotation.RequestMethod;






@FeignClient(value = "jcompany", url = "http://localhost:8891/StockPrice/")

public interface JSONStockExchangeClient {



 @GetMapping("/findByStockExchange/{id}")

 public <StockPrice> List<StockPrice> findByStockExchange(@PathVariable("id") String id );
}