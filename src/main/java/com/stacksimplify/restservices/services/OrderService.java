package com.stacksimplify.restservices.services;

import java.util.List;

import com.stacksimplify.restservices.entities.Orders;
import com.stacksimplify.restservices.exceptions.OrderNotFoundException;
import com.stacksimplify.restservices.exceptions.UserNotFoundException;

public interface OrderService {
	
	public List<Orders> getAllOrders(Long userid) throws UserNotFoundException;

	public Orders createOrder(Long userid, Orders order) throws UserNotFoundException;

	public Orders findOrderByid(Long userid, Long orderid) throws UserNotFoundException, OrderNotFoundException;

}
