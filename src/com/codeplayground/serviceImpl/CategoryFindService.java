package com.codeplayground.serviceImpl;

import java.util.ArrayList;
import java.util.HashMap;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.codeplayground.entity.CategoryDTO;
import com.codeplayground.mapper.CategoryMapperInterface;
import com.codeplayground.service.FindService;

@Service("categoryFindService")
public class CategoryFindService implements FindService<CategoryDTO> {
	@Autowired
	private CategoryMapperInterface mapper;

	@Override
	public CategoryDTO findOnebyKey(Object key) {
		return mapper.selectOnebyId(key.toString());
	}

	@Override
	public ArrayList<CategoryDTO> findList(Object key) {
		return null;
	}

	@Override
	public ArrayList<CategoryDTO> findList(HashMap<String, Object> hashMap) {
		return null;
	}

	@Override
	public ArrayList<CategoryDTO> findAll() {
		return mapper.selectAll();
	}
}
