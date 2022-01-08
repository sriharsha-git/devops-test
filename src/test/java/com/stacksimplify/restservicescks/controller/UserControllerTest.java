package com.stacksimplify.restservicescks.controller;

import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.CoreMatchers.notNullValue;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
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
import com.stacksimplify.restservices.controller.UserController;
import com.stacksimplify.restservices.entities.Orders;
import com.stacksimplify.restservices.entities.User;
import com.stacksimplify.restservices.services.UserServiceImpl;

@ExtendWith(SpringExtension.class)
@SpringBootTest(classes = { UserController.class, SpringbootBuildingblocksApplication.class })
@AutoConfigureMockMvc(addFilters = false)
@Execution(ExecutionMode.CONCURRENT)
public class UserControllerTest {

	@MockBean
	private UserServiceImpl userService;

	@Autowired
	private MockMvc mockMvc;

	@Autowired
	private ObjectMapper mapper;

	private User user;

	@BeforeEach
	public void setUp() {
		user = new User("savanchi", "Sriharsha", "Avanchi", "harsha@zyz.com", "admin", "4412");
		user.setId(2000L);
	}

	@Test
	@DisplayName("/users Get All Users")
	public void testFindAll() throws Exception {

		List<Orders> orders = new ArrayList<Orders>();
		Orders order = new Orders();
		order.setOrderId(1000L);
		order.setOrderDesc("testOrders");
		orders.add(order);
		user.setOrders(orders);
		List<User> users = Arrays.asList(user);

		Mockito.when(userService.getAllUsers()).thenReturn(users);

		mockMvc.perform(get("/users").accept(MediaType.APPLICATION_JSON)).andExpect(status().isOk())
				.andExpect(jsonPath("$", notNullValue())).andExpect(jsonPath("$.content[0].username", is("savanchi")))
				.andExpect(jsonPath("$.content[0].email", is("harsha@zyz.com")));

	}

	@Test
	@DisplayName("/users, Create User")
	public void testCreateUser() throws Exception {
		Mockito.when(userService.createUser(Mockito.any(User.class))).thenReturn(user);

		mockMvc.perform(post("/users").accept(MediaType.APPLICATION_JSON).contentType(MediaType.APPLICATION_JSON)
				.content(mapper.writeValueAsString(user).getBytes(StandardCharsets.UTF_8)))
				.andExpect(status().isCreated());
	}

	@Test
	@DisplayName("/users/{id}, Get User By Id ")
	public void testUpdateUserById() throws Exception {
		Mockito.when(userService.getUserById(2000L)).thenReturn(Optional.of(user));

		mockMvc.perform(get("/users/2000").accept(MediaType.APPLICATION_JSON).contentType(MediaType.APPLICATION_JSON))
				.andExpect(status().isOk()).andExpect(jsonPath("$", notNullValue()))
				.andExpect(jsonPath("$.username", is("savanchi"))).andExpect(jsonPath("$.email", is("harsha@zyz.com")));
	}

	@Test
	@DisplayName("/users/{id}, Update User By Id")
	public void testGetUserById() throws Exception {

		User updatedUser = new User("savanchi", "Sriharsha", "Avanchi", "harsha@gmail.com", "admin", "442012");
		updatedUser.setId(2000L);

		Mockito.when(userService.updateUserById(user.getId(), user)).thenReturn(updatedUser);

		mockMvc.perform(put("/users/2000").accept(MediaType.APPLICATION_JSON).contentType(MediaType.APPLICATION_JSON)
				.content(mapper.writeValueAsString(updatedUser).getBytes(StandardCharsets.UTF_8)))
				.andExpect(status().isOk()).andExpect(jsonPath("$", notNullValue()))
				.andExpect(jsonPath("$.ssn", is("442012"))).andExpect(jsonPath("$.email", is("harsha@gmail.com")));
	}

	@Test
	@DisplayName("/users/{id}, Delete User By Id")
	public void testDeleteUserById() throws Exception {
		mockMvc.perform(delete("/users/2000").contentType(MediaType.APPLICATION_JSON)).andExpect(status().isOk());
	}

	@Test
	@DisplayName("/byusername/{username}, Get User By username")
	public void testGetUserByUsername() throws Exception {
		Mockito.when(userService.getUserByUsername(user.getUsername())).thenReturn(user);

		MvcResult result = mockMvc
				.perform(get("/users/byusername/savanchi").accept(MediaType.APPLICATION_JSON)
						.contentType(MediaType.APPLICATION_JSON))
				.andExpect(status().isOk()).andExpect(jsonPath("$", notNullValue()))
				.andExpect(jsonPath("$.username", is("savanchi"))).andExpect(jsonPath("$.email", is("harsha@zyz.com")))
				.andReturn();

		String response = result.getResponse().getContentAsString();
		System.out.println(response);
	}

}
