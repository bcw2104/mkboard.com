package com.codeplayground.mapper;

import java.util.ArrayList;
import java.util.HashMap;

import org.apache.ibatis.annotations.Mapper;

import com.codeplayground.entity.PostDTO;

@Mapper
public interface PostMapperInterface {

	public int getCount(HashMap<String, Object> map);

	public PostDTO selectOnebyId(int postId);

	public ArrayList<PostDTO> selectClosestList(HashMap<String, Object> map);

	public ArrayList<PostDTO> selectList(HashMap<String, Object> map);

	public void insert(PostDTO postDTO);

	public void increaseHits(int postId);

	public void increaseComments(int postId);

	public void update(PostDTO postDTO);

	public void delete(int postId);
}
