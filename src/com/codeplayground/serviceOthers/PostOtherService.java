package com.codeplayground.serviceOthers;

import java.util.ArrayList;
import java.util.HashMap;

import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.codeplayground.entity.PostDTO;
import com.codeplayground.mapper.PostMapperInterface;

@Service
public class PostOtherService {
	@Autowired
	private PostMapperInterface mapper;

	public String sortTypeConverter(String sortType) {
		if(sortType != null) {
			if (sortType.equals("reg")) {
				sortType = "post_id ASC";
			}
			else if (sortType.equals("visit")) {
				sortType = "hits DESC";
			}
			else if (sortType.equals("com")) {
				sortType = "comments DESC";
			}
			else {
				sortType = "post_id DESC";
			}
		}
		else {
			sortType = "post_id DESC";
		}

		return sortType;
	}

	public void recordVisit(HttpSession session, int postId) {
		int visit = 0;

		if (session.getAttribute("recent_visit") != null) {
			visit = Integer.parseInt(session.getAttribute("recent_visit").toString());
		}

		if (visit != postId) {
			mapper.increaseHits(postId);
			session.setAttribute("recent_visit", postId);
		}
	}

	public ArrayList<PostDTO> getClosestList(int postId, String boardId) {
		HashMap<String, Object> hashMap = new HashMap<String, Object>();
		hashMap.put("postId", postId);
		hashMap.put("boardId", boardId);

		return mapper.selectClosestList(hashMap);
	}

	public int getCount(String boardId, String categoryId, String postTitle, String author){
		HashMap<String, Object> hashMap = new HashMap<String, Object>();
		hashMap.put("categoryId", categoryId);
		hashMap.put("postTitle", "%"+postTitle+"%");
		hashMap.put("author", "%"+author+"%");
		hashMap.put("boardId", boardId);

		return mapper.getCount(hashMap);
	}

	public void increaseComments(int postId) {
		mapper.increaseComments(postId);
	}

}
