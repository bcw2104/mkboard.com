package com.codeplayground.service;

import java.util.ArrayList;
import java.util.HashMap;

public interface FindService<T> {
	public T findOnebyKey(Object key);

	public ArrayList<T> findList(Object key);

	public ArrayList<T> findList(HashMap<String, Object> hashMap);

	public ArrayList<T> findAll();
}
