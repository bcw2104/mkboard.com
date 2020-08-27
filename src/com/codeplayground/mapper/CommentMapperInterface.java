package com.codeplayground.mapper;

import java.util.ArrayList;
import java.util.HashMap;

import org.apache.ibatis.annotations.Mapper;

import com.codeplayground.entity.CommentDTO;

@Mapper
public interface CommentMapperInterface {

	public int getCount(HashMap<String, Object> hashMap);

	public CommentDTO selectOne(int commentId);

	public ArrayList<CommentDTO> selectList(HashMap<String, Object> hashMap);

	public void insert(CommentDTO commentDTO);

	public void increaseChildCount(int commentId);
}
