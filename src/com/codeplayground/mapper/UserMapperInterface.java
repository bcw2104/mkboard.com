package com.codeplayground.mapper;

import java.util.ArrayList;
import java.util.HashMap;

import org.apache.ibatis.annotations.Mapper;

import com.codeplayground.entity.UserDTO;

@Mapper
public interface UserMapperInterface {

	public UserDTO selectOnebyId(String userId);

	public int getTotalCount();

	public ArrayList<UserDTO> selectListbyPage(HashMap<String,Object> map);

	public void insert(UserDTO userDTO);

	public void update(UserDTO userDTO);

	public void certification(String userId);

	public void delete(String userId);

}
