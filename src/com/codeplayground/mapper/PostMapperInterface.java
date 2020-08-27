package com.codeplayground.mapper;

import java.util.ArrayList;
import java.util.HashMap;

import org.apache.ibatis.annotations.Mapper;

import com.codeplayground.entity.PostBoardDTO;
import com.codeplayground.entity.PostDTO;

@Mapper
public interface PostMapperInterface {

	public int getCount(HashMap<String, Object> hashMap);

	public PostDTO selectOne(int postId);

	public ArrayList<PostDTO> selectClosestList(HashMap<String, Object> hashMap);

	public ArrayList<PostDTO> selectList(HashMap<String, Object> hashMap);

	public ArrayList<PostBoardDTO> selectListjoinBoard(HashMap<String, Object> hashMap);

	public void insert(PostDTO postDTO);

	public void update(PostDTO postDTO);

	public void delete(int postId);
}
