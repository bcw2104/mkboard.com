package com.codeplayground.serviceOthers;

import java.util.ArrayList;
import java.util.Iterator;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.codeplayground.entity.CommentDTO;
import com.codeplayground.mapper.SubCommentMapperInterface;

@Service
public class SubCommentOtherService {
	@Autowired
	private SubCommentMapperInterface mapper ;

	public void attachToParent(ArrayList<CommentDTO> list){
		Iterator<CommentDTO> itr = list.iterator();
		CommentDTO temp = null;

		while (itr.hasNext()) {
			temp = itr.next();
			if (temp.getChildCount() > 0) {
				temp.setSubComment(mapper.selectListbyParent(temp.getCommentId()));
			}
		}
	}
}
