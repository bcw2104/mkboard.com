package com.codeplayground.serviceImpl;

import java.util.ArrayList;
import java.util.HashMap;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.codeplayground.entity.PostDTO;
import com.codeplayground.mapper.PostMapperInterface;
import com.codeplayground.service.FindService;

@Service("postFindService")
public class PostFindService implements FindService<PostDTO> {
	@Autowired
	private PostMapperInterface mapper;

	@Override
	public PostDTO findOnebyKey(Object key) throws NumberFormatException{
		int postId = Integer.parseInt(key.toString());

		return mapper.selectOnebyId(postId);
	}

	@Override
	public ArrayList<PostDTO> findList(Object key) {

		return null;
	}

	@Override
	public ArrayList<PostDTO> findList(HashMap<String, Object> hashMap) {
		return mapper.selectList(hashMap);
	}

	@Override
	public ArrayList<PostDTO> findAll() {

		return null;
	}

}
