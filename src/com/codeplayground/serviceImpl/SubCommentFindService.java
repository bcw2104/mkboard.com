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
	public SubCommentDTO findOnebyKey(Object key) {
		return null;
	}

	@Override
	public ArrayList<SubCommentDTO> findList(Object key) {
		return mapper.selectListbyUser(key.toString());
	}

	@Override
	public ArrayList<SubCommentDTO> findList(HashMap<String, Object> hashMap) {
		return null;
	}

	@Override
	public ArrayList<SubCommentDTO> findAll() {
		return null;
	}

}
