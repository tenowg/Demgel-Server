
package com.thedemgel.playerfiles;


public class ConfigValue<T> {
	private T value;

	public ConfigValue(T value) {
		this.value = value;
	}

	public T getType() {
		return (T) value;
	}

	public T getValue() {
		return value;
	}

	public void setValue(T value) {
		this.value = value;
	}
}
