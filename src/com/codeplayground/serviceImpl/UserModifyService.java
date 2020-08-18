package com.codeplayground.serviceImpl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.codeplayground.entity.UserDTO;
import com.codeplayground.mapper.UserMapperInterface;
import com.codeplayground.service.ModifyService;

@Service("userModifyService")
public class UserModifyService implements ModifyService<UserDTO> {

	@Autowired
	UserMapperInterface mapper;

	@Override
	public void register(UserDTO dto) {
		mapper.insert(dto);
	}

	@Override
	public void update(UserDTO dto) {
		mapper.update(dto);
	}

	@Override
	public void delete(Object key) {
		mapper.delete(key.toString());
	}

}
