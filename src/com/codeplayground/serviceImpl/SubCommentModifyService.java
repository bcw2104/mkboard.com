package com.codeplayground.serviceImpl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.codeplayground.entity.SubCommentDTO;
import com.codeplayground.mapper.SubCommentMapperInterface;
import com.codeplayground.service.ModifyService;

@Service("subCommentModifyService")
public class SubCommentModifyService implements ModifyService<SubCommentDTO> {
	@Autowired
	private SubCommentMapperInterface mapper;

	@Override
	public void register(SubCommentDTO subCommentDTO) {
		mapper.insert(subCommentDTO);
	}

	@Override
	public void update(SubCommentDTO subCommentDTO) {
		mapper.update(subCommentDTO);
	}

	@Override
	public void delete(Object key) throws NumberFormatException{
		int commentId =  Integer.parseInt(key.toString());

		mapper.delete(commentId);
	}

}
