package com.stacksimplify.restservices.controller;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.hateoas.CollectionModel;
import org.springframework.hateoas.Link;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.stacksimplify.restservices.entities.Orders;
import com.stacksimplify.restservices.services.OrderServiceImpl;
import com.stacksimplify.restservices.services.UserServiceImpl;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import com.stacksimplify.restservices.entities.User;
import com.stacksimplify.restservices.exceptions.OrderNotFoundException;
import com.stacksimplify.restservices.exceptions.UserNotFoundException;

@Api(tags = "Order-Controller", description = "Orders of User")
@RestController
@RequestMapping("/users")
public class OrderController {

	@Autowired
	private UserServiceImpl userService;

	@Autowired
	private OrderServiceImpl orderService;

	@GetMapping("/{userid}/orders")
	@ApiOperation(value = "Retrieve all orders")
	public CollectionModel<Orders> getAllOrders(@PathVariable Long userid) throws UserNotFoundException {
		Optional<User> userOptional = userService.getUserById(userid);
		if (!userOptional.isPresent()) {
			throw new UserNotFoundException("User with Id " + userid + "not found. ");
		} else {
			List<Orders> allOrders = userOptional.get().getOrders();
			for (Orders order : allOrders) {
				Long orderid = order.getOrderId();
				Link selfLink = linkTo(OrderController.class).slash(userid).slash("orders").slash(orderid)
						.withSelfRel();
				order.add(selfLink);
			}

			CollectionModel<Orders> result = CollectionModel.of(allOrders);
			return result;
		}
	}

	@PostMapping("/{userid}/orders")
	@ApiOperation(value = "Create order")
	public Orders createOrder(@PathVariable Long userid, @RequestBody Orders order) throws UserNotFoundException {
		Optional<User> userOptional = userService.getUserById(userid);
		if (!userOptional.isPresent()) {
			throw new UserNotFoundException("User with Id " + userid + "not found. ");
		} else {
			User user = userOptional.get();
			order.setUser(user);
			return orderService.createOrder(userid, order);
		}
	}

	@GetMapping("/{userid}/orders/{orderid}")
	@ApiOperation(value = "Retrieve specific order")
	public Orders getOrderByOrderId(@PathVariable Long userid, @PathVariable Long orderid)
			throws UserNotFoundException, OrderNotFoundException {

		return orderService.findOrderByid(userid, orderid);

	}

}
