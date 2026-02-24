package com.kodnest.project.app.cart;
import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.RequestParam;

import com.kodnest.project.app.Product.Product;
import com.kodnest.project.app.Product.ProductImage;
import com.kodnest.project.app.Product.productImageRepo;
import com.kodnest.project.app.Product.productRepo;
import com.kodnest.project.app.USER.user;
import com.kodnest.project.app.USER.userRepo;
import java.util.List;
import java.util.ArrayList;
@Service
public class cartService {
	productRepo productrepo;
	cartRepo cartrepo;
	userRepo userrepo;
	productImageRepo imagerepo;
	

public cartService(productRepo productrepo, cartRepo cartrepo, userRepo userrepo, productImageRepo imagerepo) {
		super();
		this.productrepo = productrepo;
		this.cartrepo = cartrepo;
		this.userrepo = userrepo;
		this.imagerepo = imagerepo;
	}


public void addtoCart(user user, int productid, int quantity) {
	Product product = productrepo.findById(productid).orElseThrow(()->new IllegalArgumentException("Product not found with id"+productid));
	Optional<cart_items>existingItem = cartrepo.findByuserandProduct(user.getUserid(), productid);
	if(existingItem.isPresent()) {
		cart_items cartitem = existingItem.get();
		cartitem.setQuantity(cartitem.getQuantity()+quantity);
		cartrepo.save(cartitem);
	}else {
		cart_items newitem = new cart_items(user, product, quantity);
		cartrepo.save(newitem);
	}
}


public Map<String, Object>getcartitems(int userid){
	List<cart_items>cartItem = cartrepo.findCartItemsWithProductDetails(userid);
	Map<String, Object>response = new HashMap<>();
	user ref = userrepo.findById(userid).orElseThrow(()->new IllegalArgumentException("User not found with id"+userid));
	response.put("username", ref.getUsername());
	response.put("role", ref.getRole().toString());
	
	List<Map<String,Object>>products = new ArrayList<>();
	int overallprice=0;
	
	for(cart_items crtitm :cartItem) {
		Map<String, Object>productDetail = new HashMap<>();
		
		Product product = crtitm.getProduct();
		List<ProductImage>productimage = imagerepo.findByProduct_Productid(product.getProductid());
		String imageUrl = (productimage != null && !productimage.isEmpty())? productimage.get(0).getImageurl():"default-image-url";
		productDetail.put("product_id", product.getProductid());
		productDetail.put("image_url", imageUrl);
		productDetail.put("name", product.getName());
		productDetail.put("Description", product.getDescription());
		productDetail.put("price-per-unit", product.getPrice());
		productDetail.put("Quantity", crtitm.getQuantity());
		productDetail.put("Total_price", crtitm.getQuantity()*product.getPrice().doubleValue());
	products.add(productDetail);
	overallprice += crtitm.getQuantity()*product.getPrice().doubleValue();
	}
	Map<String,Object>cart = new HashMap<>();
	cart.put("Products", products);
	cart.put("Total_price", overallprice);
	response.put("cart", cart);
	return response;
}
public void updateCartItemQuantity(user authenticatedUser, int productid, int quantity) {
	user ref = userrepo.findById(authenticatedUser.getUserid()).orElseThrow(()-> new RuntimeException("User not found!"));
	productrepo.findById(productid).orElseThrow(()-> new RuntimeException("Product not found"));
	Optional<cart_items>exsistingitem = cartrepo.findByuserandProduct(authenticatedUser.getUserid(), productid);
	if(exsistingitem.isPresent()) {
		cart_items item = exsistingitem.get();
		if(quantity == 0){
			deletecartitem(authenticatedUser.getUserid(), productid);
		}else {
			item.setQuantity(quantity);
			cartrepo.save(item);
		}
	}else {
		throw new RuntimeException("cart not Found with user ");
	}
}

public void deletecartitem(int userid, int productid) {
	Product product = productrepo.findById(productid).orElseThrow(()-> new IllegalArgumentException("Product not found!"));
	cartrepo.deletcartItems(userid, productid);
}
public int getCartItem(int userid) {
	int count = cartrepo.countTotalItems(userid);
	return count;
}
}