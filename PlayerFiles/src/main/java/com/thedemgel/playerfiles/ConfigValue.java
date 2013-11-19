
package com.thedemgel.playerfiles;


public class ConfigValue<T> {
	private T value;
	private boolean dirty = true;

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
		dirty = true;
		this.value = value;
	}

	public boolean isDirty() {
		return dirty;
	}

	public void setDirty(boolean dirty) {
		this.dirty = dirty;
	}
}
