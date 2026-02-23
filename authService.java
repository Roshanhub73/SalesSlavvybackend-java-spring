package com.kodnest.project.app.USER;

import java.nio.charset.StandardCharsets;
import java.security.Key;
import java.time.LocalDateTime;
import java.util.Date;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.security.Keys;


@Service
public class authService implements AuthServiceContract{
	private Key SIGNING_KEY;
	private  userRepo userrepo;
	private JWTTokenRepository JwtTokenRepository;
	private BCryptPasswordEncoder passwordencode;
	
@Autowired
	public authService(userRepo userrepo, JWTTokenRepository jwtTokenRepository,
			@Value("${jwt.secret}")String jwtsecret) {
		this.userrepo = userrepo;
	    this.JwtTokenRepository = jwtTokenRepository;
		this.passwordencode = new BCryptPasswordEncoder();
	
if(jwtsecret.getBytes(StandardCharsets.UTF_8).length<64){
	throw new IllegalArgumentException("jwt_Secret in application.properties must be atleast 64 bytes");
}
this.SIGNING_KEY=Keys.hmacShaKeyFor(jwtsecret.getBytes(StandardCharsets.UTF_8));
}
	@Override
	public user authenticate(String username, String password) {
		// TODO Auto-generated method stub
		user user = userrepo.findByusername(username).orElseThrow(()->new RuntimeException("Invalid username or Password!"));
	if(!passwordencode.matches(password, user.getPassword())) {
		throw new RuntimeException("Invalid Credentials!");
	}
		return user;
	}

	@Override
	public String generateToken(user user) {
		// TODO Auto-generated method stub
		String token;
		LocalDateTime now = LocalDateTime.now();
		tokens existingtoken = JwtTokenRepository.findByUserId(user.getUserid());
		if(existingtoken != null && now.isBefore(existingtoken.getExpiresat())) {
			token = existingtoken.getToken();
			}else {
				token = generateNewToken(user);
				if(existingtoken != null) {
					JwtTokenRepository.delete(existingtoken);
				}
				saveToken(user, token);
			}
		return 	token;
	}

	@Override
	public String generateNewToken(user user) {
		// TODO Auto-generated method stub
		return Jwts.builder()
		.setSubject(user.getUsername())
		.claim("role", user.getRole().name())
		.setIssuedAt(new Date()).setExpiration(new Date(System.currentTimeMillis()+3600000))
		.signWith(SIGNING_KEY,SignatureAlgorithm.HS512).compact();
	}

	@Override
	public void saveToken(user user, String token) {
		// TODO Auto-generated method stub
		tokens t1 = new tokens(user,token,LocalDateTime.now().plusHours(1));
		JwtTokenRepository.save(t1);
	}
	
	
	
	public boolean validateToken(String token) {
		try {
		System.err.println("VALIDATING TOKEN...");
		// Parse and validate the token
		Jwts.parserBuilder()
		.setSigningKey (SIGNING_KEY)
		.build()
		.parseClaimsJws(token);
		// Check if the token exists in the database and is not expired
		Optional<tokens> jwtToken = JwtTokenRepository.findByToken(token);
		if (jwtToken.isPresent()) {
		System.err.println("Token Expiry: " + jwtToken.get().getExpiresat());
		System.err.println("Current Time: " + LocalDateTime.now());
		return jwtToken.get().getExpiresat().isAfter (LocalDateTime.now());
		}
		return false;
		} catch (Exception e) {
		System.err.println("Token validation failed: " + e.getMessage());
		return false;
		}
		}
	public String extractUsername(String token) {
		return Jwts.parserBuilder()
		.setSigningKey (SIGNING_KEY)
		.build()
		.parseClaimsJws(token)
		.getBody()
		.getSubject();
		}
		}

