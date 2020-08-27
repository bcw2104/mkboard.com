package com.codeplayground.mapper;

import java.util.ArrayList;
import java.util.HashMap;

import org.apache.ibatis.annotations.Mapper;

import com.codeplayground.entity.FileDTO;

@Mapper
public interface PostFileMapperInterface {

	public FileDTO selectOne(HashMap<String, Object> hashMap);

	public ArrayList<FileDTO> selectList(HashMap<String, Object> hashMap);

	public void insert(FileDTO fileDTO);

	public void delete(HashMap<String, Object> hashMap);
}
