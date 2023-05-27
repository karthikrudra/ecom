package com.feuji.ecommerce.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import com.feuji.ecommerce.controller.ProductController;
import com.feuji.ecommerce.dto.*;
//import com.feuji.ecommerce.dto.Product;
import com.feuji.ecommerce.repository.ProductRepository;
import com.feuji.ecommerce.repository.SellerRepository;
import com.feuji.ecommerce.util.DynamicPort;

@Service
public class ProductService {

	@Autowired(required=true)
	ProductRepository productRepository;
	
	@Autowired(required = true)
	SellerRepository sellerRepository;
	
	@Autowired
	RestTemplate restTemplate;
	
	@Value("${cartUrl}")
	private String cartUrl;
	
	@Value("${orderUrl}")
	private String orderUrl;
	
	@Autowired
	private DynamicPort dynamicPort;

	
	
	public List<Product> findAllProducts() {
		List<Product> productslist = productRepository.findAll();
		productslist.forEach(p -> {
			p.setDiscount((int) (100-(p.getFinalPrice()/p.getPrice())*100));
		});
		return productslist;
	}

	public void addProduct(Product product,int sellerId) {
		
		product.setSeller(sellerRepository.findSellerBySellerId(sellerId));
		
		productRepository.save(product);
	}
	public void editProduct(Product product,int sellerId) {
		
		product.setSeller(sellerRepository.findSellerBySellerId(sellerId));
		
		productRepository.saveAndFlush(product);
	}
	


	public Product findProductById(int productId) {
	
		return productRepository.findById(productId).get();
	}

	public List<Product> findProductsBySellerId(int sellerId) {
		return productRepository.findProductsById(sellerId);
	}
	
	//this method is used to remove the product based on product id
			public void deleteProduct(int productId) {
				productRepository.deleteById(productId);
				//List<Product> productList =  productRepository.findAll();
			    restTemplate.getForObject(dynamicPort.getUrl(cartUrl)+"/findcartbyproductid?productId="+productId, Cart.class);
				restTemplate.getForObject(dynamicPort.getUrl(orderUrl)+"/findorderbyproductid?productId="+productId, Order.class);
				
			}



	
			public List<Product> findProductsBasedOnLocation(String city) {
				 List<Product> productList = productRepository.findBasedOnLocation(city);
				 return productList;
			}
	

}