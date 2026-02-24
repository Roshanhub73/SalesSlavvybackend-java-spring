package com.kodnest.project.app.orders;

import java.util.ArrayList;
import java.util.Map;
import java.util.HashMap;
import java.util.List;

import org.springframework.stereotype.Service;

import com.kodnest.project.app.Product.Product;
import com.kodnest.project.app.Product.ProductImage;
import com.kodnest.project.app.Product.productImageRepo;
import com.kodnest.project.app.Product.productRepo;
import com.kodnest.project.app.USER.user;
@Service
public class orderService {
orderitemsRepo orderitemrepo;
productRepo productrepo;
productImageRepo imagerepo;
public orderService(orderitemsRepo orderitemrepo, productRepo productrepo, productImageRepo imagerepo) {
	super();
	this.orderitemrepo = orderitemrepo;
	this.productrepo = productrepo;
	this.imagerepo = imagerepo;
}
public Map<String, Object>getOrderforUser(user user){
	
	List<order_items>orderitems =orderitemrepo.findSucessfulOrderitemsByuserId(user.getUserid());
Map<String, Object> response = new HashMap<>();
response.put("username",user.getUsername());
response.put("role", user.getRole());


List<Map<String, Object>>products = new ArrayList<>();
	

for(order_items item: orderitems) {
Product product = productrepo.findById(item.getProduct_id()).orElse(null);
System.out.println(item);
	if(product == null) {
		continue;
	}
	
	List<ProductImage>images = imagerepo.findByProduct_Productid(product.getProductid());
	String imageUrl = images.isEmpty()?null:images.get(0).getImageurl();
	
	Map<String, Object>productdetails = new HashMap<>();
	productdetails.put("order_id", item.getOrder().getOrder_id());
	productdetails.put("quantity", item.getQuantity());
	productdetails.put("Total_price", item.getTotal_price());
	productdetails.put("ImageUrl",imageUrl);
	productdetails.put("productId", product.getProductid());
	productdetails.put("Name", product.getName());
	productdetails.put("description", product.getDescription());
	productdetails.put("price_per_unit", item.getPrice_perunit());
	products.add(productdetails);
}
response.put("products", products);
	return response;
	
}

}
