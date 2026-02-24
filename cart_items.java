package com.kodnest.project.app.cart;

import com.kodnest.project.app.Product.Product;
import com.kodnest.project.app.USER.user;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import jakarta.persistence.OneToOne;
import jakarta.persistence.Table;
@Entity
@Table
public class cart_items {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
int id;
	@ManyToOne
	@JoinColumn(name="user_id")
user user;
	@ManyToOne
	@JoinColumn(name="product_id")
Product product;
	@Column
int quantity;
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public user getUser() {
		return user;
	}
	public void setUser(user user) {
		this.user = user;
	}
	public Product getProduct() {
		return product;
	}
	public void setProduct(Product product) {
		this.product = product;
	}
	public int getQuantity() {
		return quantity;
	}
	public void setQuantity(int quantity) {
		this.quantity = quantity;
	}
	public cart_items(com.kodnest.project.app.USER.user user, Product product, int quantity) {
		super();
		this.user = user;
		this.product = product;
		this.quantity = quantity;
	}
	public cart_items(int id, com.kodnest.project.app.USER.user user, Product product, int quantity) {
		super();
		this.id = id;
		this.user = user;
		this.product = product;
		this.quantity = quantity;
	}
	public cart_items() {
		super();
		// TODO Auto-generated constructor stub
	}

}
