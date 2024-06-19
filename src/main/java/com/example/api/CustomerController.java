package com.example.api;

import java.util.List;

import org.springframework.validation.Errors;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/customers")
public class CustomerController {

	private CustomerService customerService;

	public CustomerController(CustomerService customerService) {
		this.customerService = customerService;
	}

	@PostMapping
	public Customer post(@Validated @RequestBody Customer customer, Errors errors) {
		// 要求された顧客オブジェクトのフィールドが無効な場合
		// 検証エラーを伴うランタイム例外をスローします。
		// 注: エラーをスローする代わりに、HTTP ステータス コードを設定してそれを返すことができます。
		if (errors.hasErrors()) {
			throw new RuntimeException((Throwable) errors);
		}
		// 注: ここで、挿入が成功したかどうかを検証することもできます。
		return customerService.register(customer);
	}

	@GetMapping
	public List<Customer> get() {
		return customerService.retrieve();
	}

	@GetMapping("/{id}")
	public Customer get(@PathVariable String id) {
		return customerService.retrieve(id);
	}

	@PatchMapping
	public Customer patch(@Validated @RequestBody Customer customer, Errors errors) {
		if (errors.hasErrors()) {
			throw new RuntimeException((Throwable) errors);
		}
		return customerService.update(customer);
	}

	@DeleteMapping("/{id}")
	public String delete(@PathVariable String id) {
		return customerService.delete(id);
	}
}