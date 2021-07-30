package com.mkboard.mapper;

import java.util.ArrayList;
import java.util.HashMap;

import org.apache.ibatis.annotations.Mapper;

import com.mkboard.entity.CommentDTO;

@Mapper
public interface CommentMapperInterface {

	public int getCount(HashMap<String, Object> hashMap);

	public CommentDTO selectOne(int commentId);

	public ArrayList<CommentDTO> selectList(HashMap<String, Object> hashMap);

	public void insert(CommentDTO commentDTO);

	public void increaseChildCount(int commentId);
}
