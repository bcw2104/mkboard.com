package com.mkboard.serviceOthers;

import java.util.ArrayList;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.mkboard.entity.CategoryDTO;
import com.mkboard.mapper.CategoryMapperInterface;

@Service("categoryFindService")
public class CategoryService{
	@Autowired
	private CategoryMapperInterface mapper;


	public CategoryDTO findOne(String categoryId) {
		return mapper.selectOne(categoryId);
	}

	public ArrayList<CategoryDTO> findList() {
		return mapper.selectList();
	}
}
