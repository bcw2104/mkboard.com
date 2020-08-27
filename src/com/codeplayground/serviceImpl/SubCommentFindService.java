package com.codeplayground.serviceImpl;

import java.util.ArrayList;
import java.util.HashMap;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.codeplayground.entity.SubCommentDTO;
import com.codeplayground.mapper.SubCommentMapperInterface;
import com.codeplayground.service.FindService;

@Service("subCommentFindService")
public class SubCommentFindService implements FindService<SubCommentDTO> {
	@Autowired
	SubCommentMapperInterface mapper;

	@Override
	public int getCount(HashMap<String, Object> hashMap) {

		return mapper.getCount(hashMap);
	}

	@Override
	public SubCommentDTO findOne(Object key) {
		return null;
	}

	@Override
	public ArrayList<SubCommentDTO> findList(HashMap<String, Object> hashMap) {
		return mapper.selectList(hashMap);
	}
}
