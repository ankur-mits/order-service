package com.hcl.service;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import com.dto.Product;
import com.hcl.entity.Order;
import com.hcl.repository.OrderRepository;

@Service("orderService")
public class OrderService {

	@Autowired
	private OrderRepository orderRepository;

	@Autowired
	private RestTemplate restTemplate;

	@Value("${rest.product.base.uri}")
	private String productBaseURL;

	private static DateTimeFormatter format = DateTimeFormatter
			.ofPattern("dd-MM-yyyy HH:mm:ss");

	public List<Order> getAllOrderDetails() {
		List<Order> orders = new ArrayList<>();
		orderRepository.findAll().forEach(e -> orders.add(e));
		return orders;
	}

	public Order getOrderDetail(int id) {
		return orderRepository.findById(id).get();
	}

	public Order placeOrder(Order order) {
		order.setOrderDate(getCurrentDateTime());
		return orderRepository.save(order);
	}

	public Order updateOrder(Order order) {
		order.setOrderDate(getCurrentDateTime());
		return orderRepository.save(order);
	}

	public Optional<Order> deleteById(Integer id) {
		Optional<Order> order = orderRepository.findById(id);
		if (order.isPresent()) {
			orderRepository.delete(order.get());
			return order;
		}
		return Optional.empty();
	}

	private String getCurrentDateTime() {
		return LocalDateTime.now().format(format);
	}

	public List<Order> placeOrderForAllProduct() {
		List<Order> orders = new ArrayList<>();
		ResponseEntity<List<Product>> productResponse = restTemplate.exchange(
				productBaseURL + "/products", HttpMethod.GET, null,
				new ParameterizedTypeReference<List<Product>>() {
				});
		productResponse.getBody().forEach(
				p -> {
					final int productID = p.getId();
					orders.add(placeOrder(new Order("123", String
							.valueOf(productID), "1", "")));
				});
		return orders;
	}

}
