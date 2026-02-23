package com.kodnest.project.app.USER;

import java.time.LocalDateTime;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
@Entity
@Table
public class tokens {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
int id;
	@ManyToOne
	@JoinColumn(name = "userid")
user user;
	@Column(nullable= false)
String token;
	@Column(nullable = false)
LocalDateTime expiresat;
	public tokens(com.kodnest.project.app.USER.user user, String token, LocalDateTime expiresat) {
		super();
		this.user = user;
		this.token = token;
		this.expiresat = expiresat;
	}
	public tokens(int token_id, com.kodnest.project.app.USER.user user, String token, LocalDateTime expiresat) {
		super();
		this.id = id;
		this.user = user;
		this.token = token;
		this.expiresat = expiresat;
	}
	public tokens() {
		super();
		// TODO Auto-generated constructor stub
	}
	public int getToken_id() {
		return id;
	}
	public void setToken_id(int token_id) {
		this.id = token_id;
	}
	public user getUser() {
		return user;
	}
	public void setUser(user user) {
		this.user = user;
	}
	public String getToken() {
		return token;
	}
	public void setToken(String token) {
		this.token = token;
	}
	public LocalDateTime getExpiresat() {
		return expiresat;
	}
	public void setExpiresat(LocalDateTime expiresat) {
		this.expiresat = expiresat;
	}
	
}
