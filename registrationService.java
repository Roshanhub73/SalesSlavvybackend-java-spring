package com.kodnest.project.app.USER;

import java.util.Optional;

import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class registrationService implements registrationserviceinterface {
userRepo userrepo;
private final BCryptPasswordEncoder passwordencode;
	
	public registrationService(userRepo userrepo) {
	super();
	this.userrepo = userrepo;
	passwordencode = new BCryptPasswordEncoder();
}


	@Override
	public user registartion(user user) {
	if(userrepo.findByusername(user.getUsername()).isPresent()) {
		throw new RuntimeException("Username already taken!");
	}
	if(userrepo.findByEmail(user.getEmail()).isPresent()) {
		throw new RuntimeException("Email already Registered!");
	}
		// TODO Auto-generated method stub
	user.setPassword(passwordencode.encode(user.getPassword()));
		return userrepo.save(user);
	}

}
