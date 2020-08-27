package com.codeplayground.serviceImpl;

import java.util.ArrayList;
import java.util.HashMap;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.codeplayground.entity.UserDTO;
import com.codeplayground.mapper.UserMapperInterface;
import com.codeplayground.service.FindService;

@Service("userFindService")
public class UserFindService implements FindService<UserDTO> {
	@Autowired
	UserMapperInterface mapper;

	@Override
	public int getCount(HashMap<String, Object> hashMap) {
		return mapper.getCount();
	}

	@Override
	public UserDTO findOne(Object key) {
		return mapper.selectOne(key.toString());
	}

	@Override
	public ArrayList<UserDTO> findList(HashMap<String, Object> hashMap) {
		return mapper.selectList(hashMap);
	}
}
