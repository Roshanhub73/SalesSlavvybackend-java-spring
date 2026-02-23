package com.kodnest.project.app.USER;
import java.time.LocalDateTime;
import java.util.Objects;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
@Entity
@Table
public class user {
	@Id
	@GeneratedValue(strategy= GenerationType.IDENTITY)
int userid;
	@Column(nullable=false, unique = true)
String username;
	@Column(nullable=false, unique = true)
String email;
	@Column(nullable=false)
String password;
@Enumerated(EnumType.STRING)
@Column(nullable=false)
Role role;
@Column(nullable=false, updatable= false)
LocalDateTime createdat = LocalDateTime.now();
@Column(nullable=false)
LocalDateTime updatedat = LocalDateTime.now();
public user() {
	super();
	// TODO Auto-generated constructor stub
}
public user(int userid, String username, String email, String password, Role role, LocalDateTime createdat,
		LocalDateTime updatedat) {
	super();
	this.userid = userid;
	this.username = username;
	this.email = email;
	this.password = password;
	this.role = role;
	this.createdat = createdat;
	this.updatedat = updatedat;
}
public user(String username, String email, String password, Role role, LocalDateTime createdat,
		LocalDateTime updatedat) {
	super();
	this.username = username;
	this.email = email;
	this.password = password;
	this.role = role;
	this.createdat = createdat;
	this.updatedat = updatedat;
}
public int getUserid() {
	return userid;
}
public void setUserid(int userid) {
	this.userid = userid;
}
public String getUsername() {
	return username;
}
public void setUsername(String username) {
	this.username = username;
}
public String getEmail() {
	return email;
}
public void setEmail(String email) {
	this.email = email;
}
public String getPassword() {
	return password;
}
public void setPassword(String password) {
	this.password = password;
}
public Role getRole() {
	return role;
}
public void setRole(Role role) {
	this.role = role;
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
@Override
public int hashCode() {
	return Objects.hash(createdat, email, password, role, updatedat, userid, username);
}
@Override
public boolean equals(Object obj) {
	if (this == obj)
		return true;
	if (obj == null)
		return false;
	if (getClass() != obj.getClass())
		return false;
	user other = (user) obj;
	return Objects.equals(createdat, other.createdat) && Objects.equals(email, other.email)
			&& Objects.equals(password, other.password) && role == other.role
			&& Objects.equals(updatedat, other.updatedat) && userid == other.userid
			&& Objects.equals(username, other.username);
}
@Override
public String toString() {
	return "user [userid=" + userid + ", username=" + username + ", email=" + email + ", password=" + password
			+ ", role=" + role + ", createdat=" + createdat + ", updatedat=" + updatedat + "]";
}


}
