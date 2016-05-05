package com.vino.miniioc.test;

import com.vino.miniioc.annotaion.AutoWired;
import com.vino.miniioc.annotaion.Bean;
@Bean(name="user")
public class User {
	@AutoWired
	private Address address;
	@AutoWired
	private String username;
	@AutoWired(name="password")
	private String password;
	public String getUsername() {
		return username;
	}
	public void setUsername(String username) {
		this.username = username;
	}
	public String getPassword() {
		return password;
	}
	public void setPassword(String password) {
		this.password = password;
	}
	
	public Address getAddress() {
		return address;
	}
	public void setAddress(Address address) {
		this.address = address;
	}
	@Override
	public String toString() {
		return "User [address=" + address + ", username=" + username + ", password=" + password + "]";
	}
	
}
