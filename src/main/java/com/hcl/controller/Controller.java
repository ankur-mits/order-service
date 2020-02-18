package com.hcl.controller;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.hcl.entity.Order;
import com.hcl.service.OrderService;


@RestController
@RequestMapping(value = "/order-service")
public class Controller {
	
	@Autowired
	private OrderService orderService;
	
	@GetMapping("/orders")
	public ResponseEntity<List<Order>> getAllOrderDetails() {
		return new ResponseEntity<List<Order>>(orderService.getAllOrderDetails(), HttpStatus.OK);
	}

	@GetMapping("/order/{id}")
	public ResponseEntity<Order> getOrderDetail(@PathVariable Integer id) {
		return new ResponseEntity<Order>(orderService.getOrderDetail(id), HttpStatus.OK);
	}

	@PutMapping("/order")
	public ResponseEntity<Order> updateOrder(@RequestBody Order order) {
		//TODO use optional to for update
		return new ResponseEntity<Order>(orderService.updateOrder(order), HttpStatus.OK);
	}

	@PostMapping("/order")
	public ResponseEntity<Order> placeOrder(@RequestBody Order order) {
		return new ResponseEntity<Order>(orderService.placeOrder(order), HttpStatus.CREATED);
	}

	@DeleteMapping(value = "/order/{id}")
	public ResponseEntity<Order> deleteOrder(@PathVariable Integer id) {
		Optional<Order> order = orderService.deleteById(id);
		if (!order.isPresent()) {
			return new ResponseEntity<>(HttpStatus.NOT_FOUND);
		}
		return new ResponseEntity<>(order.get(), HttpStatus.OK);
	}
	
	
	@GetMapping("/placeOrderForAllProduct")
	public ResponseEntity<List<Order>> placeOrderForAllProduct() {
		return new ResponseEntity<List<Order>>(orderService.placeOrderForAllProduct(), HttpStatus.CREATED);
	}

}
