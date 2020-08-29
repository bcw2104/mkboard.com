package com.inhaplayground.serviceImpl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.inhaplayground.entity.CommentDTO;
import com.inhaplayground.mapper.CommentMapperInterface;
import com.inhaplayground.service.ModifyService;

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
