package com.codeplayground.serviceImpl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.codeplayground.entity.PostDTO;
import com.codeplayground.mapper.PostMapperInterface;
import com.codeplayground.service.ModifyService;

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
