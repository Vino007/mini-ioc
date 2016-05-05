package com.vino.miniioc.container;

public interface Container {
	//void registerBean(Class<?> clazz);
	void registerBean(String name,Object bean);
	void registerBean(Object bean);
	//void removeBean(Class<?> clazz);
	void removeBean(String beanName);
	/**
	 * 通过bean名字来获取bean
	 * @param name
	 */
	Object getBean(String beanName);
	/**
	 * 通过类来获取bean
	 * @param clazz
	 */
	Object getBean(Class<?> clazz);
	/**
	 * 初始化容器
	 */
	void init();
	
	
}
