package com.inhaplayground.serviceImpl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.inhaplayground.entity.SubCommentDTO;
import com.inhaplayground.mapper.SubCommentMapperInterface;
import com.inhaplayground.service.ModifyService;

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
