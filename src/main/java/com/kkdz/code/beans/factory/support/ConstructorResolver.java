package com.kkdz.code.beans.factory.support;

import java.lang.reflect.Constructor;
import java.util.List;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.kkdz.code.beans.BeanDefinition;
import com.kkdz.code.beans.ConstructorArgument;
import com.kkdz.code.beans.ConstructorArgument.ValueHolder;
import com.kkdz.code.beans.SimpleTypeConverter;
import com.kkdz.code.beans.factory.BeanCreationException;
import com.kkdz.code.beans.factory.config.ConfigurableBeanFactory;

public class ConstructorResolver {

	private Log logger = LogFactory.getLog(getClass());

	private ConfigurableBeanFactory beanFactory;

	public ConstructorResolver(ConfigurableBeanFactory beanFactory) {
		this.beanFactory = beanFactory;
	}

	public Object autowireConstructor(BeanDefinition bd) {
		Constructor<?> constructorToUse = null;
		Object[] argToUse = null;
		Class<?> beanClass = null;
		try {
			beanClass = beanFactory.getClassLoader().loadClass(bd.getBeanClassName());
		} catch (ClassNotFoundException e) {
			throw new BeanCreationException(bd.getID(), "Instantiation of bean failed, can't resolve class", e);
		}
		ConstructorArgument constructorArgument = bd.getConstructorArgument();
		List<ValueHolder> argumentValues = constructorArgument.getArgumentValues();

		Constructor<?>[] constructors = beanClass.getConstructors();
		for (Constructor<?> candidateConstructor : constructors) {
			int constructorsSize = candidateConstructor.getParameterCount();
			if (constructorsSize != argumentValues.size()) {
				continue;
			}
			Class<?>[] parameterTypes = candidateConstructor.getParameterTypes();
			argToUse = new Object[parameterTypes.length];
			BeanDefinitionValueResolver valueResolver = new BeanDefinitionValueResolver(beanFactory);
			SimpleTypeConverter typeConverter = new SimpleTypeConverter();
			for (int i = 0; i < parameterTypes.length; i++) {
				Class<?> typeClass = parameterTypes[i];
				for (ValueHolder valueHoler : argumentValues) {
					try {
						Object value = valueResolver.resolveValueIfNeccessary(valueHoler.getValue());
						Object object = typeConverter.convertIfNecessary(value, typeClass);
						argToUse[i] = object;
						break;
					} catch (Exception e) {
						logger.error(e);
						continue;
					}
				}
			}
			constructorToUse = candidateConstructor;
			break;
		}

		// 找不到一个合适的构造函数
		if (constructorToUse == null) {
			throw new BeanCreationException(bd.getID(), "can't find a apporiate constructor");
		}

		try {
			return constructorToUse.newInstance(argToUse);
		} catch (Exception e) {
			throw new BeanCreationException(bd.getID(), "can't find a create instance using " + constructorToUse);
		}
	}

}
