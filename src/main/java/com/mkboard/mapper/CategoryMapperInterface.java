package com.mkboard.mapper;

import java.util.ArrayList;

import org.apache.ibatis.annotations.Mapper;

import com.mkboard.entity.CategoryDTO;

@Mapper
public interface CategoryMapperInterface {

	public CategoryDTO selectOne(String categoryId);

	public ArrayList<CategoryDTO> selectList();
}
