package com.kodnest.project.app.USER;

import java.util.Map;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/user")
public class registrationContoller {
registrationService userinterface;

public registrationContoller(registrationService userinterface) {
	super();
	this.userinterface = userinterface;
}
@PostMapping("/register")
public ResponseEntity<?>registeruser(@RequestBody user user){
	try {
	user registered = userinterface.registartion(user);
	return ResponseEntity.ok(Map.of("message", "User Registerd successfully","user details",registered));
	
	}catch(RuntimeException e) {
		return ResponseEntity.badRequest().body(Map.of("error", e.getMessage()));
	}
}
}
