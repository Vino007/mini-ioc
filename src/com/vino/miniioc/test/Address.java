package com.vino.miniioc.test;

import com.vino.miniioc.annotaion.Bean;

@Bean
public class Address {
	private String province;

	public String getProvince() {
		return province;
	}

	public void setProvince(String province) {
		this.province = province;
	}

	@Override
	public String toString() {
		return "Address [province=" + province + "]";
	}
	
}
