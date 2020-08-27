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
	public void register(CommentDTO commentDTO) {
		mapper.insert(commentDTO);
	}

	@Override
	public void update(CommentDTO commentDTO) {

	}

	@Override
	public void delete(Object key) {

	}

}
