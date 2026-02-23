package com.kodnest.project.app.USER;

import java.util.HashMap;
import java.util.Map;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletResponse;

@RestController
//@CrossOrigin(origins="http://localhost:5173", allowCredentials="true")
@RequestMapping("/api/user")
public class loginController {
	authService authservice;
public loginController(authService authservice) {
		super();
		this.authservice = authservice;
	}
@PostMapping("/login")

public ResponseEntity<?>login(@RequestBody LoginDto Login,HttpServletResponse response){
	try {
	user ref = authservice.authenticate(Login.getUsername(),Login.getPassword() );
	String token =authservice.generateToken(ref);
	
	Cookie cookie = new Cookie("authToken",token);
	cookie.setHttpOnly(true);
	cookie.setSecure(false);
	cookie.setPath("/");
	cookie.setMaxAge(3600);
	cookie.setDomain("localhost");
	response.addCookie(cookie);
	
	response.addHeader("set-cookie", String.format("auth Token=%s;HttpOly;path=/;Max-Age=360;SameSite=None",token));
	
	Map<String, Object> responseBody = new HashMap<>();
	responseBody.put("message", "Login Successfull");
	responseBody.put("role",ref.getRole().name());
	responseBody.put("username",ref.getUsername());
	return ResponseEntity.ok(responseBody);
	
	}
	catch(RuntimeException e) {
		return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(Map.of("error",e.getMessage()));
	}

}
}
