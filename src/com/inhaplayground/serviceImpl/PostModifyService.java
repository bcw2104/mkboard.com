package com.inhaplayground.serviceImpl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.inhaplayground.entity.PostDTO;
import com.inhaplayground.mapper.PostMapperInterface;
import com.inhaplayground.service.ModifyService;

@Service("postModifyService")
public class PostModifyService implements ModifyService<PostDTO>{

	@Autowired
	private PostMapperInterface mapper;

	@Override
	public void register(PostDTO postDTO) {
		mapper.insert(postDTO);
	}

	@Override
	public void update(PostDTO postDTO) {
		mapper.update(postDTO);
	}

	@Override
	public void delete(Object key) throws NumberFormatException{
		int postId =  Integer.parseInt(key.toString());

		mapper.delete(postId);
	}

}
