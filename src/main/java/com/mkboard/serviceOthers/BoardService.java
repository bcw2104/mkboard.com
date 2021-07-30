package com.mkboard.serviceOthers;

import java.util.ArrayList;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.mkboard.entity.BoardDTO;
import com.mkboard.mapper.BoardMapperInterface;

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
