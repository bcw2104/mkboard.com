package com.codeplayground.mapper;

import java.util.ArrayList;
import java.util.HashMap;

import org.apache.ibatis.annotations.Mapper;

import com.codeplayground.entity.UserDTO;

@Mapper
public interface UserMapperInterface {

	public int getCount();

	public UserDTO selectOne(String userId);

	public ArrayList<UserDTO> selectList(HashMap<String,Object> hashMap);

	public void insert(UserDTO userDTO);

	public void update(UserDTO userDTO);

	public void delete(String userId);
}
