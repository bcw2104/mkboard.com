package com.codeplayground.serviceOthers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.codeplayground.mapper.UserMapperInterface;

@Service
public class UserOtherService {
	@Autowired
	UserMapperInterface mapper;

	public int getTotalCount() {
		return mapper.getTotalCount();
	}

}
