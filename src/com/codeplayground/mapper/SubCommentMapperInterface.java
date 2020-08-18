package com.codeplayground.mapper;

import java.util.ArrayList;

import org.apache.ibatis.annotations.Mapper;

import com.codeplayground.entity.SubCommentDTO;

@Mapper
public interface SubCommentMapperInterface {

	public ArrayList<SubCommentDTO> selectListbyParent(int parentId);

	public ArrayList<SubCommentDTO> selectListbyUser(String userId);

	public void insert(SubCommentDTO subCommentDTO);

	public void update(SubCommentDTO subCommentDTO);

	public void delete(int commentId);
}
