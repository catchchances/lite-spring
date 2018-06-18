package com.kkdz.code.beans.factory.support;

import com.kkdz.code.beans.BeanDefinition;

public class GenericBeanDefinition implements BeanDefinition {

	private String beanId;

	private String beanClassName;

	private boolean singleton = true;

	private boolean prototype = false;

	private String scope = SCOPE_DEFAULT;

	public GenericBeanDefinition(String beanId, String beanClassName) {
		this.beanId = beanId;
		this.beanClassName = beanClassName;
	}

	@Override
	public String getBeanClassName() {
		return this.beanClassName;
	}

	public String getBeanId() {
		return this.beanId;
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

}
