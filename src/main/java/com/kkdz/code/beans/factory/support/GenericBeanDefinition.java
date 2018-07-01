package com.kkdz.code.beans.factory.support;

import java.util.ArrayList;
import java.util.List;

import com.kkdz.code.beans.BeanDefinition;
import com.kkdz.code.beans.ConstructorArgument;
import com.kkdz.code.beans.PropertyValue;

public class GenericBeanDefinition implements BeanDefinition {


	private String beanClassName;
	
	private String id;

	private boolean singleton = true;

	private boolean prototype = false;

	private String scope = SCOPE_DEFAULT;

	List<PropertyValue> propertyValues = new ArrayList<>();
	
	private ConstructorArgument constructorArgument = new ConstructorArgument();

	public GenericBeanDefinition(String id, String beanClassName) {
		this.id = id;
		this.beanClassName = beanClassName;
	}

	@Override
	public String getBeanClassName() {
		return this.beanClassName;
	}


	public boolean isSingleton() {
		return singleton;
	}

	public void setSingleton(boolean singleton) {
		this.singleton = singleton;
	}

	public boolean isPrototype() {
		return prototype;
	}

	public void setPrototype(boolean prototype) {
		this.prototype = prototype;
	}

	@Override
	public String getScope() {
		return this.scope;
	}

	public void setScope(String scope) {
		this.scope = scope;
		this.singleton = SCOPE_SINGLETON.equals(scope) || SCOPE_DEFAULT.equals(scope);
		this.prototype = SCOPE_PROTOTYPE.equals(scope);
	}

	@Override
	public List<PropertyValue> getPropertyValues() {
		return this.propertyValues;
	}


	@Override
	public String getID() {
		return this.id;
	}

	@Override
	public boolean hasConstructorArgumentValues() {
		return !this.getConstructorArgument().isEmpty();
	}

	@Override
	public ConstructorArgument getConstructorArgument() {
		return this.constructorArgument;
	}


}
