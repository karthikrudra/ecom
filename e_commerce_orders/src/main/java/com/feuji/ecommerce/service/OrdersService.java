package com.feuji.ecommerce.service;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Random;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import com.feuji.ecommerce.dto.Order;
import com.feuji.ecommerce.dto.Product;
import com.feuji.ecommerce.dto.ProductOrder;
import com.feuji.ecommerce.dto.User;
import com.feuji.ecommerce.dto.UserAddress;
import com.feuji.ecommerce.repository.OrdersReposotory;
import com.feuji.ecommerce.util.DynamicPort;


@Service
public class OrdersService {
	
	@Autowired
	private OrdersReposotory ordersReposotory;
	
	@Autowired
	private RestTemplate restTemplate;
	
	@Autowired
	private JavaMailSender mailSender;
	
	@Autowired
	private  SimpleMailMessage message;
	
	@Value("${productUrl}")
	private String productUrl;
	
	@Value("${userUrl}")
	private String userUrl;
	
	@Autowired
	private DynamicPort dynamicPort;
	

//	List<UserAddress> address =  new ArrayList<>();
	
	public List<ProductOrder> findAllOrders(int sellerId) {
		List<ProductOrder> productOrderList = new ArrayList<>();
		List<UserAddress> userAddress = new ArrayList<>();
		List<Order> orderList	=  ordersReposotory.findAll();
	    Product[] products = restTemplate.getForObject(dynamicPort.getUrl(productUrl)+"/findproductbysellerid?sellerId="+sellerId, Product[].class);
	    List<Product> productList = Arrays.asList(products);
	    orderList.forEach(order -> {
	          productList.forEach(product ->{
	        	  if(order.getProductId() == product.getProductId()) {
	        		  ProductOrder productOrder = new ProductOrder();
	        		  productOrder.setOrderId(order.getOrderId());
	      			  productOrder.setOrderDate(order.getOrderDate());
	      			  productOrder.setOrderQuantity(order.getOrderQuantity());
	      			  productOrder.setProductId(order.getProductId());
	      			  productOrder.setProductSize(order.getProductSize());
	      			  productOrder.setTotalPrice(order.getTotalPrice());
	      			  productOrder.setUserId(order.getUserId());
	      			  productOrder.setProductName(product.getProductName());
	      			  productOrder.setCategory(product.getCategory());
	      			  productOrder.setDiscription(product.getDiscription());
	      		      productOrder.setPrice(product.getPrice());
	      		      productOrder.setFinalPrice(product.getFinalPrice());
	      			  productOrder.setProductImage(product.getProductImage());
	      			  productOrder.setProductQuantity(product.getProductQuantity());
	      			  productOrderList.add(productOrder);
	        	  }
	          });
	    });
	    return productOrderList;
	}


	public String saveOrders(int userId,List<ProductOrder> listProduct) {
	 
        User user = restTemplate.getForObject(dynamicPort.getUrl(userUrl)+"/userbyid?userId="+userId,User.class);
		Random random = new Random();
		int var = random.nextInt(10);
		System.out.println(var);
		if(var==9 || var == 6) {
			return "Payment Cancelled";
		} else if(var == 2 || var == 5) {

			return "Payment is in progress";
		} else {
			   listProduct.stream().forEach(p ->{
			    	Order order=new Order();
		       	 order.setProductId(p.getProductId());
		    	    order.setOrderQuantity(p.getOrderQuantity());
		    		order.setTotalPrice(p.getOrderQuantity()*p.getTotalPrice());
		    		order.setProductSize(p.getProductSize());
		    		order.setUserId(userId);
		    		ordersReposotory.save(order);
		    		message.setFrom("ecommercefashio7@gmail.com");
		    		message.setTo(user.getMailId());
		    		message.setText("Order placed successfully , your order may be delivered within 7 days");
		    		message.setSubject("Thanks for oderining in fashio");
		    		mailSender.send(message);
			    });
			return "Payment Successful";
		}
	    
	}
	

	
	public void deleteOrder(int orderId) {
			
		ordersReposotory.deleteById(orderId);
	}
    
	public List<Order> findOrdersByUserId(int userid){
		
		List<Order> orderList=ordersReposotory.findOrderByUserId(userid);
		return orderList;
		
	}
	
	
	public List<ProductOrder> viewOrders(int userId) {
		List<Order> orderList = findOrdersByUserId(userId);
		System.out.println(orderList);
		List<ProductOrder> productOrderList =  new ArrayList<>();
		orderList.forEach(order -> {
			ProductOrder productOrder = new ProductOrder();
			productOrder.setOrderId(order.getOrderId());
			productOrder.setOrderDate(order.getOrderDate());
			productOrder.setOrderQuantity(order.getOrderQuantity());
			productOrder.setProductId(order.getProductId());
			productOrder.setProductSize(order.getProductSize());
			productOrder.setTotalPrice(order.getTotalPrice());
			productOrder.setUserId(order.getUserId());
			int productId = order.getProductId();
			System.out.println(productId);
			Product product = restTemplate.getForObject(dynamicPort.getUrl(productUrl)+"/findproductbyid?productId="+productId, Product.class);
			productOrder.setProductName(product.getProductName());
			//productOrder.setBrandName(product.getBrandName());
			productOrder.setCategory(product.getCategory());
			productOrder.setDiscription(product.getDiscription());
			productOrder.setPrice(product.getPrice());
			productOrder.setFinalPrice(product.getFinalPrice());
			productOrder.setProductImage(product.getProductImage());
			productOrder.setProductQuantity(product.getProductQuantity());
			productOrderList.add(productOrder);
		});
		
		return productOrderList;
	}
	
	//this method is used to delete the cart based on product id.
	
		public void deleteOrderByProductId(int productId) {
			List<Order> order = ordersReposotory.findOrderByProductId(productId);
			if(order!=null) {
				ordersReposotory.deleteAll(order);
			}
		}
		

		public String payment() {
			return null;
		
		}

}
