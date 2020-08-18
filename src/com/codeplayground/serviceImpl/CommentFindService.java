package com.codeplayground.serviceImpl;

import java.util.ArrayList;
import java.util.HashMap;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.codeplayground.entity.CommentDTO;
import com.codeplayground.mapper.CommentMapperInterface;
import com.codeplayground.service.FindService;

@Service("commentFindService")
public class CommentFindService implements FindService<CommentDTO> {

	@Autowired
	private CommentMapperInterface mapper;

	@Override
	public CommentDTO findOnebyKey(Object key) {
		return null;
	}

	@Override
	public ArrayList<CommentDTO> findList(Object key) {
		return mapper.selectListbyUser(key.toString());
	}

	@Override
	public ArrayList<CommentDTO> findList(HashMap<String, Object> hashMap) {

		return mapper.selectListSortedbyDate(hashMap);
	}

	@Override
	public ArrayList<CommentDTO> findAll() {
		return null;
	}

}
