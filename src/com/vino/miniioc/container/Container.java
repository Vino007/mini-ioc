package com.vino.miniioc.container;

public interface Container {
	//void registerBean(Class<?> clazz);
	void registerBean(String name,Object bean);
	void registerBean(Object bean);
	//void removeBean(Class<?> clazz);
	void removeBean(String beanName);
	/**
	 * ͨ��bean��������ȡbean
	 * @param name
	 */
	Object getBean(String beanName);
	/**
	 * ͨ��������ȡbean
	 * @param clazz
	 */
	Object getBean(Class<?> clazz);
	/**
	 * ��ʼ������
	 */
	void init();
	
	
}
