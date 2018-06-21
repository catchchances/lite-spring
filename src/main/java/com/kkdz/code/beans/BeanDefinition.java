package com.kkdz.code.beans;

/**
 * anDefinition是get bean的定义
 * 
 */
public interface BeanDefinition {

	public static final String SCOPE_SINGLETON = "singleton";

	public static final String SCOPE_PROTOTYPE = "prototype";

	public static final String SCOPE_DEFAULT = "";

	public String getBeanClassName();

	public boolean isSingleton();

	public boolean isPrototype();

	public String getScope();

	public void setScope(String scope);

}
