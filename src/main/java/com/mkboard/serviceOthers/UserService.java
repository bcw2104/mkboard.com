package com.mkboard.serviceOthers;

import java.util.ArrayList;
import java.util.HashMap;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.mkboard.entity.UserDTO;
import com.mkboard.mapper.UserMapperInterface;

@Service
public class UserService{
	@Autowired
	UserMapperInterface mapper;

	public int getCount() {
		return mapper.getCount();
	}

	public UserDTO findOne(String userId,String userNickName, String userEmail) {
		HashMap<String, Object> hashMap = new HashMap<String, Object>();
		hashMap.put("userId", userId);
		hashMap.put("userNickName", userNickName);
		hashMap.put("userEmail", userEmail);

		return mapper.selectOne(hashMap);
	}

	public ArrayList<UserDTO> findList(int frontPageNum,int rearPageNum, String sortType) {
		HashMap<String, Object> hashMap = new HashMap<String, Object>();
		hashMap.put("frontPageNum", frontPageNum);
		hashMap.put("rearPageNum", rearPageNum);

		if(sortType == null) {
			sortType = "DESC";
		}
		hashMap.put("sortType", sortType);

		return mapper.selectList(hashMap);
	}

}
