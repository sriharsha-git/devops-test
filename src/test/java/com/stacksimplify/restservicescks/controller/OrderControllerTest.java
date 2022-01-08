package com.stacksimplify.restservicescks.controller;

import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.CoreMatchers.notNullValue;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.jupiter.api.parallel.Execution;
import org.junit.jupiter.api.parallel.ExecutionMode;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.stacksimplify.restservices.SpringbootBuildingblocksApplication;
import com.stacksimplify.restservices.controller.OrderController;
import com.stacksimplify.restservices.entities.Orders;
import com.stacksimplify.restservices.entities.User;
import com.stacksimplify.restservices.exceptions.UserNotFoundException;
import com.stacksimplify.restservices.services.OrderServiceImpl;
import com.stacksimplify.restservices.services.UserServiceImpl;

@ExtendWith(SpringExtension.class)
@SpringBootTest(classes = { OrderController.class, SpringbootBuildingblocksApplication.class })
@AutoConfigureMockMvc(addFilters = false)
@Execution(ExecutionMode.CONCURRENT)
public class OrderControllerTest {

	@MockBean
	private OrderServiceImpl orderService;

	@MockBean
	private UserServiceImpl userService;

	@Autowired
	MockMvc mockMvc;

	@Autowired
	private ObjectMapper mapper;

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
		user.setOrders(orders);

	}

	@Test
	@DisplayName("/userid/orders Get All Orders for a User")
	public void testgetAllOrders() throws Exception {
		Mockito.when(userService.getUserById(2000L)).thenReturn(Optional.of(user));
		Mockito.when(orderService.getAllOrders(2000L)).thenReturn(orders);
		mockMvc.perform(get("/users/2000/orders").accept(MediaType.APPLICATION_JSON)).andExpect(status().isOk())
				.andExpect(jsonPath("$", notNullValue()))
				.andExpect(jsonPath("$.content[0].orderDesc", is("testOrder-1")))
				.andExpect(jsonPath("$.content[1].orderDesc", is("testOrder-2"))).andReturn();
	}

	@Test
	@DisplayName("/userid/orders Create Order for a User")
	public void testCreateOrder() throws Exception {
		User otherUser = new User("savanchi", "Sriharsha", "Avanchi", "harsha@zyz.com", "admin", "4412");
		otherUser.setId(3000L);
		Orders firstOrder = new Orders();
		firstOrder.setOrderId(2000L);
		firstOrder.setOrderDesc("testOrder-1");

		Mockito.when(userService.getUserById(3000L)).thenReturn(Optional.of(otherUser));
		Mockito.when(orderService.createOrder(3000L, firstOrder)).thenReturn(firstOrder);

		MvcResult result = mockMvc
				.perform(post("/users/3000/orders").accept(MediaType.APPLICATION_JSON)
						.contentType(MediaType.APPLICATION_JSON)
						.content(mapper.writeValueAsString(firstOrder).getBytes(StandardCharsets.UTF_8)))
				.andExpect(status().isOk()).andExpect(jsonPath("$", notNullValue()))
				.andExpect(jsonPath("$.orderDesc", is("testOrder-1"))).andReturn();

		String response = result.getResponse().getContentAsString();
		System.out.println(response);

	}

	@Test
	@DisplayName("/{userid}/orders/{orderid} Create Order for a User")
	public void testgetOrderByOrderId() throws Exception {
		Mockito.when(userService.getUserById(2000L)).thenReturn(Optional.of(user));
		Mockito.when(orderService.findOrderByid(user.getId(), firstOrder.getOrderId())).thenReturn(firstOrder);

		MvcResult result = mockMvc.perform(get("/users/2000/orders/1000").accept(MediaType.APPLICATION_JSON))
				.andExpect(status().isOk()).andExpect(jsonPath("$", notNullValue()))
				.andExpect(jsonPath("$.orderDesc", is("testOrder-1"))).andReturn();

		String response = result.getResponse().getContentAsString();
		System.out.println(response);
	}
}
