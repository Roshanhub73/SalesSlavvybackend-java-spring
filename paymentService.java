package com.kodnest.project.app.orders;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import com.razorpay.RazorpayClient;
import com.razorpay.RazorpayException;
import com.kodnest.project.app.cart.cartRepo;
import com.kodnest.project.app.cart.cart_items;


@Service
public class paymentService {
	
cartRepo cartrepo;
OrderRepo orderrepo;
orderitemsRepo orderitemrepo;

@Autowired
public paymentService(cartRepo cartrepo, OrderRepo orderrepo, orderitemsRepo orderitemrepo) {
	super();
	this.cartrepo = cartrepo;
	this.orderrepo = orderrepo;
	this.orderitemrepo = orderitemrepo;
}

@Value("${razorpay.key_Id}")
String razorpayKeyId;

@Value("${razorpay.key_Secret}")
String razorpayKeySecret;


public String createOrder(int userid, BigDecimal Total_price,List<order_items>cartitems) throws RazorpayException {
	RazorpayClient razorpayclient = new RazorpayClient(razorpayKeyId,razorpayKeySecret);
	var orderRequest = new JSONObject();
	orderRequest.put("amount",Total_price.multiply(BigDecimal.valueOf(100)).intValue());
	orderRequest.put("currency","INR");
	orderRequest.put("receipt","txn_"+System.currentTimeMillis());
	
	com.razorpay.Order razorpayOrder = razorpayclient.orders.create(orderRequest);
	
	orders order = new orders();
	order.setOrder_id(razorpayOrder.get("id"));
	order.setTotal_price(Total_price);
	order.setUser_id(userid);
	order.setStatus(orderStatus.Pending);
	order.setCreatedat(LocalDateTime.now());
	orderrepo.save(order);
	return razorpayOrder.get("id");	
}


public boolean verifyPayment(String razorpay_Order_Id, String razorpay_Payment_Id,String razorpay_signature,int usrid) {
	try {
		JSONObject attributes = new JSONObject();
		attributes.put("razorpay_order_id", razorpay_Order_Id);
		attributes.put("razorpay_payment_id", razorpay_Payment_Id);
		attributes.put("razorpay_signature",razorpay_signature);
		boolean isSignatureValid = com.razorpay.Utils.verifyPaymentSignature(attributes, razorpayKeySecret);
		if(isSignatureValid) {
			orders order  = orderrepo.findById(razorpay_Order_Id).orElseThrow(()->new RuntimeException("Orders not found"));
			order.setStatus(orderStatus.Success);
		order.setUpdatedat(LocalDateTime.now());
			orderrepo.save(order);
			List<cart_items>cartitems = cartrepo.findCartItemsWithProductDetails(usrid);
			for(cart_items cartitem : cartitems) {
				order_items orderitem = new order_items();
				orderitem.setOrder(order);
				orderitem.setProduct_id(cartitem.getProduct().getProductid());
				orderitem.setQuantity(cartitem.getQuantity());
				orderitem.setPrice_perunit(cartitem.getProduct().getPrice());
				orderitem.setTotal_price(cartitem.getProduct().getPrice().multiply(BigDecimal.valueOf(cartitem.getQuantity())));
				orderitemrepo.save(orderitem);
			}
			
			cartrepo.deletcartItemswithId(usrid);
			return true;
		}else {			return false;
		}
	}catch(Exception e){
		e.printStackTrace();		return false;
	}
}

public void saveOrderItems(String orderid, List<order_items>items) {

		orders order = orderrepo.findById(orderid).orElseThrow(()->new RuntimeException("Order Not Found"));
for(order_items item : items) {
		item.setOrder(order);
		orderitemrepo.save(item);
	}
	
}
}
