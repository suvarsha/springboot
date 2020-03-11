package com.example.demo.stockprice.Controller;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.demo.stockprice.JSONStockPriceClient;
import com.example.demo.stockprice.dao.StockPriceRepository;
import com.example.demo.stockprice.pojos.StockPrice;


@CrossOrigin(origins="*",allowedHeaders="*")

@RestController
@RequestMapping("/stockprice")
public class StockPriceController {

	@Autowired
	JSONStockPriceClient jstockprice;
	
	@Autowired
	private StockPriceRepository stockPriceRepository;
	@RequestMapping("/getAllStock")
	public Iterable<StockPrice>getAllUser()
	{
		return stockPriceRepository.findAll();
	}
	@PostMapping("/saveStock")
	public StockPrice saveStock(@RequestBody StockPrice stock)
	{
		System.out.println(stock);
	stockPriceRepository.save(stock);
		return stock;
	}
	@PutMapping("/putstock/{StockPrice}")
	public StockPrice updateStock(@RequestBody StockPrice stock,@PathVariable("StockPrice") String stockExchange)
	{
		
		stock.setStockExchange(stockExchange);
		System.out.println(stock);
		
		stockPriceRepository.save(stock);
		return stock;
	}
	@DeleteMapping("/deletestock/{StockPrice}")
	public boolean deleteStock(@PathVariable("StockPrice") String stockExchange)
	{
		
	
		System.out.println(stockExchange);
		
		stockPriceRepository.deleteById(stockExchange);;
		return true;
	}
	@GetMapping("/findstock/{stockExchange}")
	public StockPrice findAllinOne(@PathVariable("stockExchange") String stockExchange)
	{
	
	Optional<StockPrice> stock=stockPriceRepository.findById(stockExchange);
	StockPrice st=stock.get();
	st.setStockExchangeList(jstockprice.findByStockExchange(stockExchange));
	
		return stock.get();
	}
	
}