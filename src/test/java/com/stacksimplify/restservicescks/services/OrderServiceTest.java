package com.stacksimplify.restservicescks.services;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.when;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import com.stacksimplify.restservices.SpringbootBuildingblocksApplication;
import com.stacksimplify.restservices.controller.OrderController;
import com.stacksimplify.restservices.entities.Orders;
import com.stacksimplify.restservices.entities.User;
import com.stacksimplify.restservices.exceptions.OrderNotFoundException;
import com.stacksimplify.restservices.exceptions.UserNotFoundException;
import com.stacksimplify.restservices.repositories.OrderRepository;
import com.stacksimplify.restservices.services.OrderServiceImpl;
import com.stacksimplify.restservices.services.UserServiceImpl;

@SpringBootTest(classes = { SpringbootBuildingblocksApplication.class })
@ExtendWith(SpringExtension.class)
@ExtendWith(MockitoExtension.class)
public class OrderServiceTest {

	@InjectMocks
	private OrderServiceImpl orderService;

	@MockBean
	private OrderRepository orderRepository;

	@MockBean
	private UserServiceImpl userService;

	private User user;
	private List<Orders> orders;
	private Orders firstOrder;
	private Orders secondOrder;

	@BeforeEach
	public void setUp() {
		user = new User("savanchi", "Sriharsha", "Avanchi", "harsha@zyz.com", "admin", "4412");
		user.setId(2000L);
		orders = new ArrayList<Orders>();
		firstOrder = new Orders();
		firstOrder.setOrderId(1000L);
		firstOrder.setOrderDesc("testOrder-1");
		secondOrder = new Orders();
		secondOrder.setOrderId(10001L);
		secondOrder.setOrderDesc("testOrder-2");
		orders.add(firstOrder);
		orders.add(secondOrder);
		firstOrder.setUser(user);
		secondOrder.setUser(user);

	}

	@Test
	@DisplayName("Create Order for a User")
	public void testcreateOrder() throws UserNotFoundException {
		when(userService.getUserById(2000L)).thenReturn(Optional.of(user));
		when(orderRepository.save(firstOrder)).thenReturn(firstOrder);
		Orders createOrder = orderService.createOrder(2000L, firstOrder);
		assertEquals(createOrder.getOrderDesc(), "testOrder-1");
	}
	
	@Test
	@DisplayName("Get Order for a User by Order Id")
	public void testFindOrderByid() throws UserNotFoundException, OrderNotFoundException {
		when(userService.getUserById(2000L)).thenReturn(Optional.of(user));
		when(orderRepository.findById(10001L)).thenReturn(Optional.of(secondOrder));
		Orders userOrder = orderService.findOrderByid(2000L, 10001L);
		assertEquals(userOrder.getOrderDesc(), "testOrder-2");
	}

}
