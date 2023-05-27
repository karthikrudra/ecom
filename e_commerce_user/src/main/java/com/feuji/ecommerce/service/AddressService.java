package com.feuji.ecommerce.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.feuji.ecommerce.repository.AddressRepository;

@Service
public class AddressService {

	@Autowired
	AddressRepository addressRepository;


}
