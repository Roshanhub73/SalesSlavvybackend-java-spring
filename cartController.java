package com.kodnest.project.app.cart;

import java.util.Map;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.kodnest.project.app.USER.user;
import com.kodnest.project.app.USER.userRepo;

import jakarta.servlet.http.HttpServletRequest;
@RestController
@RequestMapping("/api/cart")
@CrossOrigin(origins = "http://localhost:5173", allowCredentials = "true")
public class cartController {
	userRepo userrepo;
	cartService cartservice;
public cartController(userRepo userrepo, cartService cartservice) {
		super();
		this.userrepo = userrepo;
		this.cartservice = cartservice;
	}
@PostMapping("/add")
@CrossOrigin(origins = "http://localhost:5173", allowCredentials = "true")
public ResponseEntity<Void> addtocart(@RequestBody Map<String, Object>request,HttpServletRequest req){
	user ref = (user) req.getAttribute("authenticatedUser");
	String username = (String)request.get("username");
	int productid = (int)request.get("productid");
	int quantity = request.containsKey("quantity")?(int)request.get("quantity"):1;
	cartservice.addtoCart(ref, productid, quantity);
	return ResponseEntity.status(HttpStatus.CREATED).build();
}
@GetMapping("/items")
public ResponseEntity<Map<String,Object>>getCartItems(HttpServletRequest req){
	user ref = (user) req.getAttribute("authenticatedUser");
	Map<String, Object>cartItems = cartservice.getcartitems(ref.getUserid());
	return ResponseEntity.ok(cartItems);
}

@PutMapping("/update")
public ResponseEntity<Void>updateCartItemQuantity(@RequestBody Map<String, Object>request,HttpServletRequest req){
	String username = (String) request.get("username");
	int productid = (int) request.get("productid");
	int quantity = (int) request.get("quantity");
	user ref = (user) req.getAttribute("authenticatedUser");
	cartservice.updateCartItemQuantity(ref, productid, quantity);
	return ResponseEntity.ok().build();
}


@DeleteMapping("/delete")
public ResponseEntity<Void>deleteCartItem(@RequestBody Map<String,Object>request , HttpServletRequest req){
	int productid = (int) request.get("productid");
	
	user ref = (user) req.getAttribute("authenticatedUser");
	cartservice.deletecartitem(ref.getUserid(), productid);
	return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
}
@GetMapping("/count")
public ResponseEntity<Integer>getCartCount(HttpServletRequest req){
	user ref = (user) req.getAttribute("authenticatedUser");
	int userid = ref.getUserid();
	int cartcount = cartservice.getCartItem(userid); 
	return ResponseEntity.ok(cartcount);
}
}
