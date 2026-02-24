package com.kodnest.project.app.orders;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;
import java.math.*;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.kodnest.project.app.USER.user;
import com.razorpay.RazorpayException;

import jakarta.servlet.http.HttpServletRequest;

@RestController
@CrossOrigin(origins ="http://localhost:5173")
@RequestMapping("/api/payment")
public class paymentController {
paymentService paymentservice;
@Autowired
public paymentController(paymentService paymentservice) {
	super();
	this.paymentservice = paymentservice;
}
@PostMapping("/create")
public ResponseEntity<String>createPaymentOrder(@RequestBody Map<String,Object>requestBody,HttpServletRequest req){
	try {
		user ref = (user) req.getAttribute("authenticatedUser");
		BigDecimal totalAmount = new BigDecimal(requestBody.get("totalAmount").toString());
		List<Map<String,Object>>cartitemsRaw = (List<Map<String, Object>>)requestBody.get("cartItems");
		List<order_items> cartitems = cartitemsRaw.stream().map(item->{
			order_items orderitem = new order_items();
			orderitem.setProduct_id((Integer)item.get("productId"));
			orderitem.setQuantity((Integer)item.get("quantity"));
			BigDecimal priceperunit = new BigDecimal(item.get("price").toString());
			orderitem.setPrice_perunit(priceperunit);
			orderitem.setTotal_price(priceperunit.multiply(BigDecimal.valueOf((Integer)item.get("quantity"))));
			return orderitem;
		}).collect(Collectors.toList());
		
		String razorpayOrderId = paymentservice.createOrder(ref.getUserid(), totalAmount, cartitems);
		return ResponseEntity.ok(razorpayOrderId);
}catch(RazorpayException e) {
	e.printStackTrace();
	return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Error creating RazorpayOrder "+ e.getMessage());
}catch(Exception e) {
	e.printStackTrace();
	return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("invalid Request data"+e.getMessage());
}
}
@PostMapping("/verify")
public ResponseEntity<String>verifyPayment(@RequestBody Map<String,Object>request, HttpServletRequest req){
	try {
		user ref = (user) req.getAttribute("authenticatedUser");
		String razorpay_Order_Id = (String) request.get("razorpayOrderId");
		String razorpay_Payment_Id = (String) request.get("razorpayPaymentId");
		String razorpay_signature = (String) request.get("razorpaySignature");
		boolean isVerified = paymentservice.verifyPayment(razorpay_Order_Id, razorpay_Payment_Id, razorpay_signature, ref.getUserid());
		
		if(isVerified) {
			return ResponseEntity.ok("Payment verified Successfully");
		}else {
			return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Payment verification Failed");
		}
		
	}catch(Exception e) {
		e.printStackTrace();
		return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Error verifying payment :"+e.getMessage());
	}
}
}

