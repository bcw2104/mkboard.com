package com.codeplayground.service;

import java.util.ArrayList;
import java.util.HashMap;

public interface FindService<T> {

	public int getCount(HashMap<String, Object> hashMap);

	public T findOne(Object key);

	public ArrayList<T> findList(HashMap<String, Object> hashMap);
}
