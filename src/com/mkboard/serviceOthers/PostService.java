package com.mkboard.serviceOthers;

import java.util.ArrayList;
import java.util.HashMap;

import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.mkboard.entity.PostInfoDTO;
import com.mkboard.mapper.PostMapperInterface;

@Service
public class PostService{
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
		return sortType;
	}

	public void recordVisit(HttpSession session, int postId) {
		int visit = 0;

		if (session.getAttribute("recent_visit") != null) {
			visit = Integer.parseInt(session.getAttribute("recent_visit").toString());
		}

		if (visit != postId) {
			increase(postId, 1, 0);
			session.setAttribute("recent_visit", postId);
		}
	}

	public int getCount(String categoryId,String boardId,int important) {
		HashMap<String, Object> hashMap = new HashMap<String, Object>();
		hashMap.put("categoryId", categoryId);
		hashMap.put("boardId", boardId);
		hashMap.put("important", important);

		return mapper.getCount(hashMap);
	}

	public int getCount(String categoryId,String boardId,int important,String postTitle,String userId,boolean correct) {
		HashMap<String, Object> hashMap = new HashMap<String, Object>();
		hashMap.put("categoryId", categoryId);
		hashMap.put("boardId", boardId);
		hashMap.put("important", important);
		hashMap.put("postTitle", postTitle);
		hashMap.put("userId", userId);
		hashMap.put("correct", correct);

		return mapper.getCount(hashMap);
	}

	public PostInfoDTO findOne(int postId) {
		return mapper.selectOne(postId);
	}

	public ArrayList<PostInfoDTO> findList(String categoryId,String boardId,int important) {
		HashMap<String, Object> hashMap = new HashMap<String, Object>();
		hashMap.put("categoryId", categoryId);
		hashMap.put("boardId", boardId);
		hashMap.put("important", important);

		return mapper.selectList(hashMap);
	}

	public ArrayList<PostInfoDTO> findList(String categoryId,String boardId,int important,
							String postTitle,String userId,boolean correct,String sortType,int frontPageNum,int rearPageNum) {

		HashMap<String, Object> hashMap = new HashMap<String, Object>();
		hashMap.put("categoryId", categoryId);
		hashMap.put("boardId", boardId);
		hashMap.put("important", important);
		hashMap.put("postTitle", postTitle);
		hashMap.put("userId", userId);
		hashMap.put("correct", correct);
		hashMap.put("sortType", sortTypeConverter(sortType));
		hashMap.put("frontPageNum", frontPageNum);
		hashMap.put("rearPageNum", rearPageNum);

		return mapper.selectList(hashMap);
	}

	public ArrayList<PostInfoDTO> getClosestList(String categoryId, String boardId,int postId) {
		HashMap<String, Object> hashMap = new HashMap<String, Object>();
		hashMap.put("categoryId", categoryId);
		hashMap.put("boardId", boardId);
		hashMap.put("postId", postId);

		return mapper.selectClosestList(hashMap);
	}

	public void increase(int postId,int hits,int comments) {
		HashMap<String, Object> hashMap = new HashMap<String, Object>();
		hashMap.put("postId", postId);
		if(hits > 0) {
			hashMap.put("hits", hits);
		}
		if(comments > 0) {
			hashMap.put("comments", comments);
		}
		mapper.increase(hashMap);
	}
}
