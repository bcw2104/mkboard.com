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
	public UserDTO findOnebyKey(Object key) {
		return mapper.selectOnebyId(key.toString());
	}

	@Override
	public ArrayList<UserDTO> findList(Object key) {
		return null;
	}

	@Override
	public ArrayList<UserDTO> findList(HashMap<String, Object> hashMap) {
		return mapper.selectListbyPage(hashMap);
	}

	@Override
	public ArrayList<UserDTO> findAll() {
		return null;
	}

}
