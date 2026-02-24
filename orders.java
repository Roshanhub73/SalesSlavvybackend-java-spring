package com.kodnest.project.app.orders;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;

@Entity
@Table
public class orders {
	@Id
String order_id;
	@Column
int user_id;
	@Column
BigDecimal Total_price;
	@Enumerated(EnumType.STRING)
	@Column(name="status")
orderStatus status;
	@Column
	LocalDateTime createdat;
	@Column
	LocalDateTime updatedat;
	@OneToMany(mappedBy="order",cascade = CascadeType.ALL,fetch=FetchType.LAZY)
	List<order_items>orderitems;
	public orders() {
		super();
		// TODO Auto-generated constructor stub
	}
	public orders(String order_id, int user_id, BigDecimal Total_price, orderStatus status, LocalDateTime createdat,
			LocalDateTime updatedat, List<order_items> orderitems) {
		super();
		this.order_id = order_id;
		this.user_id = user_id;
		this. Total_price =  Total_price;
		this.status = status;
		this.createdat = createdat;
		this.updatedat = updatedat;
		this.orderitems = orderitems;
	}
	public orders(int user_id, BigDecimal total_amt, orderStatus status, LocalDateTime createdat,
			LocalDateTime updatedat, List<order_items> orderitems) {
		super();
		this.user_id = user_id;
		this. Total_price =  Total_price;
		this.status = status;
		this.createdat = createdat;
		this.updatedat = updatedat;
		this.orderitems = orderitems;
	}
	
	public orders(int user_id, BigDecimal  Total_price, orderStatus status, LocalDateTime createdat,
			LocalDateTime updatedat) {
		super();
		this.user_id = user_id;
		this. Total_price =  Total_price;
		this.status = status;
		this.createdat = createdat;
		this.updatedat = updatedat;
	}
	public String getOrder_id() {
		return order_id;
	}
	public void setOrder_id(String order_id) {
		this.order_id = order_id;
	}
	public int getUser_id() {
		return user_id;
	}
	public void setUser_id(int user_id) {
		this.user_id = user_id;
	}
	public BigDecimal getTotal_price() {
		return  Total_price;
	}
	public void setTotal_price(BigDecimal Total_price) {
		this. Total_price =  Total_price;
	}
	public orderStatus getStatus() {
		return status;
	}
	public void setStatus(orderStatus status) {
		this.status = status;
	}
	public LocalDateTime getCreatedat() {
		return createdat;
	}
	public void setCreatedat(LocalDateTime createdat) {
		this.createdat = createdat;
	}
	public LocalDateTime getUpdatedat() {
		return updatedat;
	}
	public void setUpdatedat(LocalDateTime updatedat) {
		this.updatedat = updatedat;
	}
	public List<order_items> getOrderitems() {
		return orderitems;
	}
	public void setOrderitems(List<order_items> orderitems) {
		this.orderitems = orderitems;
	}
	
}
 