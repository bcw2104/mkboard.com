package com.inhaplayground.serviceOthers;

import java.util.ArrayList;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.inhaplayground.entity.BoardDTO;
import com.inhaplayground.mapper.BoardMapperInterface;

@Service("boardFindService")
public class BoardService {
	@Autowired
	BoardMapperInterface mapper;

	public BoardDTO findOne(String boardId) {
		return mapper.selectOne(boardId);
	}

	public ArrayList<BoardDTO> findList(String categoryId) {
		return mapper.selectList(categoryId);
	}
}
