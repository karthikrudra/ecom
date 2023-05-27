package com.feuji.ecommerce.service;

import java.util.ArrayList;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.feuji.ecommerce.dto.RegisterAddress;
import com.feuji.ecommerce.dto.User;
import com.feuji.ecommerce.dto.UserAddress;
import com.feuji.ecommerce.repository.AddressRepository;
import com.feuji.ecommerce.repository.UserRepository;

@Service
public class UserService {

	@Autowired
	UserRepository userRepository;
	@Autowired
	AddressRepository addressRepository;
	
	

	public void saveUser(RegisterAddress userAddress) {
		User user = new User();
		user.setUserName(userAddress.getUserName());
		user.setGender(userAddress.getGender());
		user.setMailId(userAddress.getMailId());
		user.setPassword(userAddress.getPassword());
		user.setMobileNumber(userAddress.getMobileNumber());
		
		UserAddress address = new UserAddress();
		address.setLocation(userAddress.getLocation());
		address.setPincode(userAddress.getPincode());
		address.setCity(userAddress.getCity());
		address.setLandmark(userAddress.getLandmark());
		address.setUser(user);
		List<UserAddress> list = new ArrayList<>();
		list.add(address);
		user.setAddress(list);
		userRepository.save(user);
	}

	public List<User> getAllUsers() {
		return userRepository.findAll();
	}
	
	public int login(String mailId,String password) {
		 User user = userRepository.findByMailIdAndPassword(mailId, password);
		 if(user!=null) {
			 return user.getUserId();
		 }
		 else {
			 return 0;
		 }
	}

	
	public void addAddress(UserAddress address,int userid) {
		User user = userRepository.findById(userid).get();
		
		address.setUser(user);
		addressRepository.save(address);
	}
	
	public User findUserById(int userId) {
		User user = userRepository.findById(userId).get();
		return user;
	}
	
	


}
