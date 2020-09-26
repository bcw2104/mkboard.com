package com.mkboard.mapper;

import java.util.ArrayList;

import org.apache.ibatis.annotations.Mapper;

import com.mkboard.entity.BoardDTO;

@Mapper
public interface BoardMapperInterface {

	public BoardDTO selectOne(String boardId);

	public ArrayList<BoardDTO> selectList(String categoryId);
}
