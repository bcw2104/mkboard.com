package com.inhaplayground.serviceOthers;

import java.util.ArrayList;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.inhaplayground.entity.CategoryDTO;
import com.inhaplayground.mapper.CategoryMapperInterface;

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
