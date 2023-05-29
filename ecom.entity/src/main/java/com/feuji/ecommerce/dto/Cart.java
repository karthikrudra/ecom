package com.feuji.ecommerce.dto;


import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name="cart")
public class Cart {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name="cart_id")
	private int cartId;
	
	@Column(name="cart_number")
	private int cartNumber;
	
	
	@Column(name = "user_id")
	private int userId;
	
	@Column(name="product_id")
	private int productId;
	
	@Column(name="quantity")
	private int orderQuantity;
	
	@Column(name="size")
	private String productSize;

	
	
	public Cart() {
		super();
	}



	public Cart(int cartId, int cartNumber, int userId, int productId, int orderQuantity, String productSize) {
		super();
		this.cartId = cartId;
		this.cartNumber = cartNumber;
		this.userId = userId;
		this.productId = productId;
		this.orderQuantity = orderQuantity;
		this.productSize = productSize;
	}



	public int getCartId() {
		return cartId;
	}



	public void setCartId(int cartId) {
		this.cartId = cartId;
	}



	public int getCartNumber() {
		return cartNumber;
	}



	public void setCartNumber(int cartNumber) {
		this.cartNumber = cartNumber;
	}



	public int getUserId() {
		return userId;
	}



	public void setUserId(int userId) {
		this.userId = userId;
	}



	public int getProductId() {
		return productId;
	}



	public void setProductId(int productId) {
		this.productId = productId;
	}



	public int getOrderQuantity() {
		return orderQuantity;
	}



	public void setOrderQuantity(int orderQuantity) {
		this.orderQuantity = orderQuantity;
	}



	public String getProductSize() {
		return productSize;
	}



	public void setProductSize(String productSize) {
		this.productSize = productSize;
	}



	@Override
	public String toString() {
		return "Cart [cartId=" + cartId + ", cartNumber=" + cartNumber + ", userId=" + userId + ", productId="
				+ productId + ", orderQuantity=" + orderQuantity + ", productSize=" + productSize + "]";
	}


}
