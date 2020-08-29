package com.inhaplayground.mapper;

import java.util.ArrayList;
import java.util.HashMap;

import org.apache.ibatis.annotations.Mapper;

import com.inhaplayground.entity.SubCommentDTO;

@Mapper
public interface SubCommentMapperInterface {

	public int getCount(HashMap<String, Object> hashMap);

	public ArrayList<SubCommentDTO> selectList(HashMap<String, Object> hashMap);

	public void insert(SubCommentDTO subCommentDTO);

	public void update(SubCommentDTO subCommentDTO);

	public void delete(int commentId);
}
