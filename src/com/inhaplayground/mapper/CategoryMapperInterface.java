package com.inhaplayground.mapper;

import java.util.ArrayList;

import org.apache.ibatis.annotations.Mapper;

import com.inhaplayground.entity.CategoryDTO;

@Mapper
public interface CategoryMapperInterface {

	public CategoryDTO selectOne(String categoryId);

	public ArrayList<CategoryDTO> selectList();
}
