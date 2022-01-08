package com.stacksimplify.restservices.services;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.stacksimplify.restservices.entities.Orders;
import com.stacksimplify.restservices.entities.User;
import com.stacksimplify.restservices.exceptions.OrderNotFoundException;
import com.stacksimplify.restservices.exceptions.UserNotFoundException;
import com.stacksimplify.restservices.repositories.OrderRepository;

@Service
public class OrderServiceImpl implements OrderService{

	@Autowired
	private OrderRepository orderRepository;

	@Autowired
	private UserServiceImpl userService;

	@Override
	public List<Orders> getAllOrders(Long userid) throws UserNotFoundException {
		Optional<User> userOptional = userService.getUserById(userid);
		if (userOptional.isPresent()) {
			User user = userOptional.get();
			return user.getOrders();
		} else {
			throw new UserNotFoundException("User with Id " + userid + " not found");
		}
	}

	@Override
	public Orders createOrder(Long userid, Orders order) throws UserNotFoundException {
		Optional<User> userOptional = userService.getUserById(userid);
		if (userOptional.isPresent()) {
			User user = userOptional.get();
			order.setUser(user);
			return orderRepository.save(order);
		} else {
			throw new UserNotFoundException("User with Id " + userid + " not found");
		}

	}
	
	@Override
	public Orders findOrderByid(Long userid, Long orderid) throws UserNotFoundException, OrderNotFoundException {
		Optional<User> userOptional = userService.getUserById(userid);

		if (!userOptional.isPresent()) {
			throw new UserNotFoundException("User with Id " + userid + "not found. ");
		}

		Optional<Orders> orderOptional = orderRepository.findById(orderid);

		if (!orderOptional.isPresent()) {
			throw new OrderNotFoundException("Order with Id " + orderid + "not found. ");
		}
		return orderOptional.get();
	}
}
