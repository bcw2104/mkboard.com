package com.inhaplayground.mapper;

import java.util.ArrayList;

import org.apache.ibatis.annotations.Mapper;

import com.inhaplayground.entity.BoardDTO;

@Mapper
public interface BoardMapperInterface {

	public BoardDTO selectOne(String boardId);

	public ArrayList<BoardDTO> selectList(String categoryId);
}
