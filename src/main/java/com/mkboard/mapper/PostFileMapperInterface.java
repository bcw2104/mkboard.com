package com.mkboard.mapper;

import java.util.ArrayList;
import java.util.HashMap;

import org.apache.ibatis.annotations.Mapper;

import com.mkboard.entity.PostFileDTO;

@Mapper
public interface PostFileMapperInterface {

	public PostFileDTO selectOne(HashMap<String, Object> hashMap);

	public ArrayList<PostFileDTO> selectList(HashMap<String, Object> hashMap);

	public void insert(PostFileDTO fileDTO);

	public void delete(HashMap<String, Object> hashMap);
}
