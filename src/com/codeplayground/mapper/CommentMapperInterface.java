package com.codeplayground.mapper;

import java.util.ArrayList;
import java.util.HashMap;

import org.apache.ibatis.annotations.Mapper;

import com.codeplayground.entity.CommentDTO;

@Mapper
public interface CommentMapperInterface {

	public ArrayList<CommentDTO> selectListSortedbyDate(HashMap<String, Object> map);

	public ArrayList<CommentDTO> selectListbyUser(String userId);

	public void insert(CommentDTO commentDTO);

	public void increaseChildCount(int commentId);
}
