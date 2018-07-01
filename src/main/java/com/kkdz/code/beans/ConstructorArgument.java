package com.kkdz.code.beans;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class ConstructorArgument {

	private final List<ValueHolder> argumentValues = new ArrayList<>();

	public ConstructorArgument() {
	}

	public void addArgumentValue(ValueHolder valueHolder) {
		this.argumentValues.add(valueHolder);
	}

	public List<ValueHolder> getArgumentValues() {
		return Collections.unmodifiableList(this.argumentValues);
	}

	public int getArgumentCount() {
		return this.argumentValues.size();
	}

	public boolean isEmpty() {
		return this.argumentValues.isEmpty();
	}

	public void clear() {
		this.argumentValues.clear();
	}

	public static class ValueHolder {

		private String name;

		private Object value;

		private String type;

		public ValueHolder(Object value) {
			this.value = value;
		}

		public ValueHolder(Object value, String type) {
			this.value = value;
			this.type = type;
		}

		public ValueHolder(Object value, String type, String name) {
			this.value = value;
			this.type = type;
			this.name = name;
		}

		public String getName() {
			return name;
		}

		public void setName(String name) {
			this.name = name;
		}

		public Object getValue() {
			return value;
		}

		public void setValue(Object value) {
			this.value = value;
		}

		public String getType() {
			return type;
		}

		public void setType(String type) {
			this.type = type;
		}

	}

}
