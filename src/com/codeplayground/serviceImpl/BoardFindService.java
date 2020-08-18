package com.codeplayground.serviceImpl;

import java.util.ArrayList;
import java.util.HashMap;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.codeplayground.entity.BoardDTO;
import com.codeplayground.mapper.BoardMapperInterface;
import com.codeplayground.service.FindService;

@Service("boardFindService")
public class BoardFindService implements FindService<BoardDTO> {
	@Autowired
	BoardMapperInterface mapper;

	@Override
	public BoardDTO findOnebyKey(Object key){
		return mapper.selectOnebyId(key.toString());
	}

	@Override
	public ArrayList<BoardDTO> findList(Object key) {
		return mapper.selectListbyCategory(key.toString());
	}

	@Override
	public ArrayList<BoardDTO> findList(HashMap<String, Object> hashMap) {
		return null;
	}

	@Override
	public ArrayList<BoardDTO> findAll() {
		return null;
	}


}
