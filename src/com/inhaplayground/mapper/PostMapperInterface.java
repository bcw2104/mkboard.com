package com.inhaplayground.mapper;

import java.util.ArrayList;
import java.util.HashMap;

import org.apache.ibatis.annotations.Mapper;

import com.inhaplayground.entity.PostDTO;
import com.inhaplayground.entity.PostInfoDTO;

@Mapper
public interface PostMapperInterface {

	public int getCount(HashMap<String, Object> hashMap);

	public PostInfoDTO selectOne(int postId);

	public ArrayList<PostInfoDTO> selectClosestList(HashMap<String, Object> hashMap);

	public ArrayList<PostInfoDTO> selectList(HashMap<String, Object> hashMap);

	public void insert(PostDTO postDTO);

	public void update(PostDTO postDTO);

	public void increase(HashMap<String, Object> hashMap);

	public void delete(int postId);
}
