package com.codeplayground.serviceImpl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.codeplayground.entity.SubCommentDTO;
import com.codeplayground.mapper.SubCommentMapperInterface;
import com.codeplayground.service.ModifyService;

@Service("subCommentModifyService")
public class SubCommentModifyService implements ModifyService<SubCommentDTO> {
	@Autowired
	SubCommentMapperInterface mapper;

	@Override
	public void register(SubCommentDTO dto) {
		mapper.insert(dto);
	}

	@Override
	public void update(SubCommentDTO dto) {
		mapper.update(dto);
	}

	@Override
	public void delete(Object key) throws NumberFormatException{
		int commentId =  Integer.parseInt(key.toString());

		mapper.delete(commentId);
	}

}
