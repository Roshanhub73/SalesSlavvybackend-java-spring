package com.kodnest.project.app.orders;

import java.util.Map;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.kodnest.project.app.USER.user;

import jakarta.servlet.http.HttpServletRequest;

@RestController
@CrossOrigin(origins = "http://localhost:5173", allowCredentials= "true")
@RequestMapping("/api/orders")
public class orderController {
orderService orderservice;

public orderController(orderService orderservice) {
	super();
	this.orderservice = orderservice;
}
@GetMapping
public ResponseEntity<Map<String, Object>>getOrderforUser(HttpServletRequest req){
	try {
		user ref = (user) req.getAttribute("authenticatedUser");
		if (ref == null) {
			return ResponseEntity.status(401).body(Map.of("Error", "User Not Authenticated"));
		}
		Map<String, Object>response = orderservice.getOrderforUser(ref);
		return ResponseEntity.ok(response);
	}catch(IllegalArgumentException e ) {
		return ResponseEntity.status(402).body(Map.of("Error",e.getMessage()));
	}catch(Exception e) {
		e.printStackTrace();
		return ResponseEntity.status(500).body(Map.of("error", "Unexpected Error Occured"));
	}	
}
}
