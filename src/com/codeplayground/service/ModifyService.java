package com.codeplayground.service;

public interface ModifyService <T>{
	public void register(T dto);

	public void update(T dto);

	public void delete(Object key);
}
