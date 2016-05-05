package com.vino.miniioc.container;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

import com.vino.miniioc.annotaion.AutoWired;
import com.vino.miniioc.annotaion.Bean;
import com.vino.miniioc.utils.PackageUtil;
/**
 * 容器，实现注解配置bean,自动装配 
 * @author vino007
 *
 */
public class MiniContainer implements Container {
	/**
	 * name bean
	 */
	private ConcurrentHashMap<String, Object> beans = new ConcurrentHashMap<>();
	private String[] basePackages;
	private boolean isInit=false;
	@Override
	public void registerBean(String name, Object bean) {
		if (null != name && null != bean) {

			beans.put(name, bean);
		}

	}

	@Override
	public void registerBean(Object bean) {
		if (null != bean) {
			String name = bean.getClass().getName();
			beans.put(name, bean);
		}

	}

	@Override
	public void removeBean(String beanName) {
		// TODO Auto-generated method stub
		if (null != beanName) {
			beans.remove(beanName);
		}
	}

	@Override
	public Object getBean(String beanName) {
		// TODO Auto-generated method stub

		return beans.get(beanName);
	}

	@Override
	public Object getBean(Class<?> clazz) {
		// TODO Auto-generated method stub
		String name = clazz.getName();
		return beans.get(name);
	}

	@Override
	public void init() {
		// TODO Auto-generated method stub
		System.out.println("初始化容器");
		if(basePackages!=null)
			autoScanBean();//自动扫描注册bean
		System.out.println("依赖注入");
		for (Map.Entry<String, Object> entry : beans.entrySet()) {
			try {
				
				inject(entry.getValue());
				
			} catch (IllegalArgumentException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (IllegalAccessException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
	}


	public void inject(Object bean) throws IllegalArgumentException, IllegalAccessException {
		Field[] fields = bean.getClass().getDeclaredFields();
		//获得某个类的所有申明的字段，即包括public、private和proteced，但是不包括父类的申明字段。 
		//getFields()获得某个类的所有的公共（public）的字段，包括父类。
		for (Field field : fields) {
			
			AutoWired autoWired = field.getAnnotation(AutoWired.class);
			if (null != autoWired) {
				String name = autoWired.name();// 获取bean name
				if("".equals(name)){
					name=field.getName();//默认若没有指定name，则使用field的名字查找bean
				}
				//System.out.println("field bean name:" + name);
				Object autoWiredBeanField = beans.get(name);// 获取对应的field bean
				//System.out.println("field bean :" + autoWiredBeanField);
				if (null == autoWiredBeanField) {
					throw new RuntimeException("没有找到对应的bean");
				}
				boolean accessible = field.isAccessible();
				field.setAccessible(true);//要访问private属性需要设置为true
				field.set(bean, autoWiredBeanField);
				field.setAccessible(accessible);
			}
		}
	}
	public void autoScanBean(){
		System.out.println("自动扫描并注册bean");
		List<String> classNames=new ArrayList<>();
		for(String basePackage:basePackages){
			List<String> names=PackageUtil.getClassNameByPackageName(basePackage, true);			
			classNames.addAll(names);
		}
		for(String className:classNames){
			try {
				Class<?> clazz=Class.forName(className);
				Bean beanAnnotation=clazz.getAnnotation(Bean.class);
				if(null!=beanAnnotation){
					String name=beanAnnotation.name();
					if("".equals(name)){//当没有设置name时使用首字母小写类名的作为name
						name=clazz.getSimpleName().substring(0, 1).toLowerCase()+clazz.getSimpleName().substring(1);
						
					}
					Object bean=clazz.newInstance();
					registerBean(name, bean);
				}
			} catch (ClassNotFoundException | InstantiationException | IllegalAccessException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			
		}
	}

	public String[] getBasePackages() {
		return basePackages;
	}

	public void setBasePackages(String[] basePackages) {
		this.basePackages = basePackages;
	}

	public boolean isInit() {
		return isInit;
	}

	public void setInit(boolean isInit) {
		this.isInit = isInit;
	}

}
