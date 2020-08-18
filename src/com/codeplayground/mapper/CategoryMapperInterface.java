package com.codeplayground.mapper;

import java.util.ArrayList;

import org.apache.ibatis.annotations.Mapper;

import com.codeplayground.entity.CategoryDTO;

@Mapper
public interface CategoryMapperInterface {

	public ArrayList<CategoryDTO> selectAll();

	public CategoryDTO selectOnebyId(String categoryId);
}
