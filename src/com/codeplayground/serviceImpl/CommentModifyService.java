package com.codeplayground.serviceImpl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.codeplayground.entity.CommentDTO;
import com.codeplayground.mapper.CommentMapperInterface;
import com.codeplayground.service.ModifyService;

@Service("commentModifyService")
public class CommentModifyService implements ModifyService<CommentDTO> {
	@Autowired
	private CommentMapperInterface mapper;

	@Override
	public void register(CommentDTO dto) {
		mapper.insert(dto);
	}

	@Override
	public void update(CommentDTO dto) {

	}

	@Override
	public void delete(Object key) {

	}

}
