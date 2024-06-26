package com.example.api;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.*;

import java.util.ArrayList;
import java.util.List;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

public class CustomerServiceTest {

	@Mock
	private CustomerMapper mapper;

	@InjectMocks
	private CustomerServiceImpl service;

	@BeforeEach
	public void setup() {
		MockitoAnnotations.openMocks(this);
	}

	@DisplayName("CREATE TEST: Check if registeration succeeded.")
	@Test
	public void testRegister() {
		Customer customer = new Customer();
		customer.setId("100");
		customer.setUsername("user100");
		customer.setEmail("test.user.100@EXAMPLE.com");
		customer.setPhoneNumber("01234567890");
		customer.setPostCode("1234567");

		when(mapper.insert(any(Customer.class))).thenReturn(1);
		Customer actual = service.register(customer);
		assertEquals(customer.getId(), actual.getId());
		assertEquals(customer.getUsername(), actual.getUsername());
		assertEquals("test.user.100@example.com", actual.getEmail());
		assertEquals(customer.getPhoneNumber(), actual.getPhoneNumber());
		assertEquals(customer.getPostCode(), actual.getPostCode());
		verify(mapper, times(1)).insert(customer);
	}

	@DisplayName("READ TEST: Check if all customer information retrieved.")
	@Test
	public void testRetrieveAll() {
		Customer customer2 = new Customer();
		customer2.setId("002");
		customer2.setUsername("user002");
		customer2.setEmail("test.user.002@example.com");
		customer2.setPhoneNumber("23456789012");
		customer2.setPostCode("2345671");
		Customer customer3 = new Customer();
		customer3.setId("003");
		customer3.setUsername("user003");
		customer3.setEmail("test.user.003@example.com");
		customer3.setPhoneNumber("34567890123");
		customer3.setPostCode("3456712");
		Customer customer4 = new Customer();
		customer4.setId("004");
		customer4.setUsername("user004");
		customer4.setEmail("test.user.004@example.com");
		customer4.setPhoneNumber("45678901234");
		customer4.setPostCode("4567123");

		List<Customer> customers = new ArrayList<>();
		customers.add(customer2);
		customers.add(customer3);
		customers.add(customer4);
		when(mapper.selectAll()).thenReturn(customers);

		List<Customer> actuals = service.retrieve();
		assertEquals(3, actuals.size());
		verify(mapper, times(1)).selectAll();
	}

	@DisplayName("READ TEST: Check if expected customer information retrieved.")
	@Test
	public void testRetrieve() {
		Customer customer = new Customer();
		customer.setId("002");
		customer.setUsername("user002");
		customer.setEmail("test.user.002@example.com");
		customer.setPhoneNumber("23456789012");
		customer.setPostCode("2345671");

		when(mapper.select(customer.getId())).thenReturn(customer);
		Customer actual = service.retrieve(customer.getId());
		assertEquals(customer, actual);
		verify(mapper, times(1)).select(customer.getId());
	}

	@DisplayName("UPDATE TEST: Check if update succeeded.")
	@Test
	public void testUpdate() {
		//String customerId = "002";
		Customer customer = new Customer();
		customer.setId("002");
		customer.setUsername("user002");
		customer.setEmail("modified.test.user.002@example.com");
		customer.setPhoneNumber("23456789012");
		customer.setPostCode("2345671");

		when(mapper.update(customer)).thenReturn(customer); //`mapper.update`メソッドが呼び出された際に、1（更新成功の意味）を返すようにモックします
		Customer actual = service.update(customer); //結果を`actual`に格納
		assertEquals(customer, actual);
		verify(mapper, times(1)).update(customer);
	}

	@DisplayName("DELETE TEST: Check if deletion succeeded.")
	@Test
	public void testDelete() {
		String customerId = "002";
		when(mapper.delete(customerId)).thenReturn(1);

		String actual = service.delete(customerId);
		assertEquals(customerId, actual);
		verify(mapper, times(1)).delete(customerId);
	}
}
