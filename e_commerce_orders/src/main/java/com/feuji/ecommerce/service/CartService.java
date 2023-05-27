package com.feuji.ecommerce.service;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.client.RestTemplate;

import com.feuji.ecommerce.dto.Cart;
import com.feuji.ecommerce.dto.CartProduct;
import com.feuji.ecommerce.dto.Product;
import com.feuji.ecommerce.repository.CartRepository;
import com.feuji.ecommerce.repository.ProductRepository;

@Service
public class CartService {
	
	@Autowired
	private CartRepository cartRepository;
	@Autowired
	private ProductRepository productRepository;

	@Autowired
	private RestTemplate restTemplate;
	

	
	
	
	public List<Cart> findAllCarts() {
		List<Cart> list = cartRepository.findAll();
		return list;
	}
	



	public String saveCart(int productId,int userId,int quantity,String size) {
		
		boolean flag = true;
		
		List<Cart> userCart =  cartRepository.findCartProductIdByUserId(userId);
		System.out.println(userCart);
		Cart cart = new Cart();
		
		for(Cart cart2:userCart) {
		if(cart2.getProductSize().equals(size) && cart2.getProductId() == productId) {
	        flag = false;
			break;
			
		}
		
	}
		if(flag) {
		cart.setProductId(productId);
		cart.setUserId(userId);
		cart.setCartNumber(userId);
		cart.setOrderQuantity(quantity);
		cart.setProductSize(size);
		
		cartRepository.save(cart);
		}
		return "added to cart";
	}

	

	public String deleteCart(int userId,int productId,String size) {
		Cart cart = cartRepository.findCartBasedOnIdAndSize(userId, productId, size);
		
		cartRepository.delete(cart);
		
		return "removed";
	}


	
	public List<Product> findCartByUserId(int userId) {
		return productRepository.findProductfromCart(userId);
	}
	
	public List<CartProduct> findCartProductByUserId(int userId) {
		List<Cart> cartList = cartRepository.findCartProductIdByUserId(userId);
		List<CartProduct> cartProductList = new ArrayList<>();
		cartList.forEach(list -> {
				CartProduct cartProduct = new CartProduct();
				cartProduct.setCartId(list.getCartId());
				cartProduct.setCartNumber(list.getCartNumber());
				cartProduct.setProductId(list.getProductId());
				int productId = list.getProductId();
				cartProduct.setUserId(list.getUserId());
				Product product = productRepository.findById(productId).get();
				cartProduct.setProductName(product.getProductName());
				cartProduct.setPrice(product.getPrice());
				cartProduct.setTotalPrice(product.getFinalPrice());
				cartProduct.setDiscription(product.getDiscription());
				cartProduct.setProductQuantity(product.getProductQuantity());
				cartProduct.setCategory(product.getCategory());
				cartProduct.setProductImage(product.getProductImage());
				cartProduct.setSeller(product.getSeller());
				cartProduct.setOrderQuantity(list.getOrderQuantity());
				cartProduct.setProductSize(list.getProductSize());
				cartProductList.add(cartProduct);
		});
		return cartProductList;
	}
	

	
	public void deleteCartWhenOrderPlaced(int userId) {
		 List<Cart> cartList = cartRepository.findCartProductIdByUserId(userId);
		 cartRepository.deleteAll(cartList);
	}
	
	//this method is used to delete the cart based on product id.
	
		public void deleteCartByProductId(int productId) {
			List<Cart> cart = cartRepository.findCartByProductId(productId);
			if(cart!=null) {
				cartRepository.deleteAll(cart);
			}
		}
	
}
