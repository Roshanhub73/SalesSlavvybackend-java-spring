package com.kodnest.project.app.orders;

import java.math.BigDecimal;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;

@Entity
@Table
public class order_items {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
int id;
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name="order_id")
orders order;
	@Column
	int product_id;
	@Column
	int quantity;
	@Column
	BigDecimal price_perunit;
	@Column
	BigDecimal total_price;
	public order_items() {
		super();
		// TODO Auto-generated constructor stub
	}
	public order_items(int id, orders order, int product_id, int quantity, BigDecimal price_perunit,
			BigDecimal Total_price) {
		super();
		this.id = id;
		this.order = order;
		this.product_id = product_id;
		this.quantity = quantity;
		this.price_perunit = price_perunit;
		this.total_price = Total_price;
	}
	public order_items(orders order, int product_id, int quantity, BigDecimal price_perunit, BigDecimal Total_price) {
		super();
		this.order = order;
		this.product_id = product_id;
		this.quantity = quantity;
		this.price_perunit = price_perunit;
		this.total_price = Total_price;
	}
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public orders getOrder() {
		return order;
	}
	public void setOrder(orders order) {
		this.order = order;
	}
	public int getProduct_id() {
		return product_id;
	} 
	public void setProduct_id(int product_id) {
		this.product_id = product_id;
	}
	public int getQuantity() {
		return quantity;
	}
	public void setQuantity(int quantity) {
		this.quantity = quantity;
	}
	public BigDecimal getPrice_perunit() {
		return price_perunit;
	}
	public void setPrice_perunit(BigDecimal price_perunit) {
		this.price_perunit = price_perunit;
	}
	public BigDecimal getTotal_price() {
		return total_price;
	}
	public void setTotal_price(BigDecimal total_price) {
		this.total_price = total_price;
	}

}
