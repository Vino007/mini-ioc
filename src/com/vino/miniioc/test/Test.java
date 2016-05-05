package com.vino.miniioc.test;

import com.vino.miniioc.container.MiniContainer;

public class Test {

	public static void main(String[] args) {
		MiniContainer container=new MiniContainer();
		container.setBasePackages(new String[]{"com.vino.miniioc.test"});
		
		container.registerBean("username","foo");
		container.registerBean("password", "123456");
		container.init();
		Object bean=container.getBean("user");
		Object address=container.getBean("address");
		System.out.println(address);
		System.out.println(bean);
		
	}

}
