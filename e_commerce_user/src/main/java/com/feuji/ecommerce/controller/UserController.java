package com.feuji.ecommerce.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.feuji.ecommerce.dto.RegisterAddress;
import com.feuji.ecommerce.dto.User;
import com.feuji.ecommerce.dto.UserAddress;
import com.feuji.ecommerce.service.UserService;

@RestController
public class UserController {
	
	@Autowired
	UserService userService;
	
	@PostMapping("/saveUser")
	public void saveUser(@RequestBody RegisterAddress userAddress) {
		System.err.println(userAddress);
		userService.saveUser(userAddress);
		System.out.println(userAddress);
	}
	
	@GetMapping("/login")
	public byte[] login(@RequestParam String mailId,@RequestParam String password) {
		int logindata = userService.login(mailId, password);
		ObjectMapper objectMapper = new ObjectMapper();
	    byte[] json = null;
	    try {
	        json = objectMapper.writeValueAsBytes(logindata);
	    } catch (JsonProcessingException e) {
	        e.printStackTrace();
	    }
	    
	    return json;
	}
	

	
	@PostMapping("/addAddress")
	public void addAddress(@RequestBody UserAddress address,@RequestParam int userId) {
		userService.addAddress(address,userId);
	}
	
	@GetMapping("/userbyid") 
	public ResponseEntity<User> findUserById(@RequestParam int userId) {
		User user = userService.findUserById(userId);
		return ResponseEntity.ok(user);
	}
 
}
