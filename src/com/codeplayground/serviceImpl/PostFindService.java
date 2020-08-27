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
	public int getCount(HashMap<String, Object> hashMap) {
		return mapper.getCount(hashMap);
	}

	@Override
	public PostDTO findOne(Object key) throws NumberFormatException{
		return mapper.selectOne(Integer.parseInt(key.toString()));
	}

	@Override
	public ArrayList<PostDTO> findList(HashMap<String, Object> hashMap) {
		return mapper.selectList(hashMap);
	}

}
