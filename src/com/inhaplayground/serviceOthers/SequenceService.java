package com.inhaplayground.serviceOthers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.inhaplayground.mapper.SequenceMapperInterface;

@Service
public class SequenceService {
	@Autowired
	private SequenceMapperInterface mapper;

	public int getNextSequence(String type) {
		return mapper.next(type);
	}
}
