package com.codeplayground.mapper;

import java.util.ArrayList;

import org.apache.ibatis.annotations.Mapper;

import com.codeplayground.entity.BoardDTO;

@Mapper
public interface BoardMapperInterface {

	public BoardDTO selectOnebyId(String boardId);

	public ArrayList<BoardDTO> selectListbyCategory(String categoryId);
}
