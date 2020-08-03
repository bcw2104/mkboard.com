package com.codeplayground.service;

import java.util.ArrayList;
import java.util.Iterator;

import javax.servlet.http.HttpSession;

import com.codeplayground.dao.PostDAO;
import com.codeplayground.entity.PostDTO;

public class PostService {
	private PostDAO postDAO;

	public PostService() {
		postDAO = new PostDAO();
	}

	public String fieldConverter(String field) {
		if(field != null) {
			if (field.equals("reg")) {
				field = "post_id ASC";
			}
			else if (field.equals("visit")) {
				field = "hits DESC";
			}
			else if (field.equals("com")) {
				field = "comments DESC";
			}
			else {
				field = "post_id DESC";
			}
		}
		else {
			field = "post_id DESC";
		}

		return field;
	}

	public PostDTO getPostInfo(HttpSession session, int postId) {
		int visit = 0;

		if (session.getAttribute("recent_visit") != null) {
			visit = Integer.parseInt(session.getAttribute("recent_visit").toString());
		}

		if (visit != postId) {
			postDAO.putHits(postId);
			session.setAttribute("recent_visit", postId);
		}

		return postDAO.getPost(postId);
	}

	public ArrayList<PostDTO> getClosestPostList(int postId, String boardId){
		return postDAO.getClosestPostList(postId, boardId);
	}

	public int getTotalPostCount(String categoryId, String postTitle, String author) {
		return postDAO.getPostCount(categoryId, postTitle, author);
	}

	public int getPostCount(String boardId, String categoryId, String postTitle, String author) {
		if(boardId.equals("")) {
			return postDAO.getPostCount(categoryId, postTitle, author);
		}
		else {
			return postDAO.getPostCount(boardId, categoryId, postTitle, author);
		}
	}

	public ArrayList<PostDTO> getPostList(String boardId, String categoryId, String field, String postTitle,
			String author, int pageNum) {
		if(boardId.equals("")) {
			return postDAO.getPostList(categoryId, fieldConverter(field), postTitle, author, pageNum);
		}
		else {
			return postDAO.getPostList(boardId, categoryId, fieldConverter(field), postTitle, author, pageNum);
		}
	}

	public boolean uploadPost(String postTitle,String postContent,String boardId,String author) {
		return postDAO.postPost(postTitle, postContent, boardId, author);
	}
}
