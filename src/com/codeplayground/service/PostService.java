package com.codeplayground.service;

import java.util.ArrayList;

import com.codeplayground.dao.PostDAO;
import com.codeplayground.entity.PostDTO;

public class PostService {

	public String fieldConverter(String field) {
		String result = "post_id";

		if (field.equals("id")) {
			result = "post_id";
		}
		return result;
	}

	public PostDTO clickPost(String postId) {
		PostDAO postDAO = new PostDAO();

		if (postDAO.addHits(postId)) {
			return postDAO.getPostData(postId);
		} else {
			return new PostDTO();
		}
	}

	public int getTotalPostCount(String boardId, String categoryId, String postTitle, String author) {
		PostDAO dao = new PostDAO();

		return dao.getTotalPostCount(boardId, categoryId, postTitle, author);
	}

	public ArrayList<PostDTO> getPostList(String boardId, String categoryId, String field, String postTitle,
			String author, int page) {
		PostDAO postDAO = new PostDAO();

		return postDAO.getPostList(boardId, categoryId, fieldConverter(field), postTitle, author, page);
	}
}
