package com.mkboard.mapper;

import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface SequenceMapperInterface {

	public int next(String type);
}
