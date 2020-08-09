package com.codeplayground.service;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Iterator;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;

import com.codeplayground.dao.CommentDAO;
import com.codeplayground.dao.PostDAO;
import com.codeplayground.entity.CommentDTO;
import com.codeplayground.entity.SubCommentDTO;

public class CommentService {
	private CommentDAO commentDAO;

	public CommentService() {
		commentDAO = new CommentDAO();
	}


	public void addSubCommentList(ArrayList<CommentDTO> list) throws SQLException{
		Iterator<CommentDTO> itr = list.iterator();
		CommentDTO temp = null;

		while (itr.hasNext()) {
			temp = itr.next();
			if (temp.getChildCount() > 0) {
				temp.setSubComment(commentDAO.getSubCommentList(temp.getCommentId()));
			}
		}
	}

	@SuppressWarnings("unchecked")
	public JSONArray convertToJson(ArrayList<CommentDTO> commentList) {
		Iterator<CommentDTO> itr = commentList.iterator();
		JSONArray array = new JSONArray();

		while (itr.hasNext()) {
			CommentDTO commentDTO = itr.next();
			JSONObject object = new JSONObject();
			object.put("commentId", commentDTO.getCommentId());
			object.put("userId", commentDTO.getUserId());
			object.put("commentContent", commentDTO.getCommentContent());
			object.put("createDate", commentDTO.getCreateDate().toString());

			if (commentDTO.getChildCount() > 0) {
				ArrayList<SubCommentDTO> subCommentList = commentDTO.getSubComment();
				Iterator<SubCommentDTO> subItr = subCommentList.iterator();
				JSONArray subArray = new JSONArray();
				while (subItr.hasNext()) {
					SubCommentDTO subCommentDTO = subItr.next();
					JSONObject subObject = new JSONObject();
					subObject.put("commentId", subCommentDTO.getCommentId());
					subObject.put("userId", subCommentDTO.getUserId());
					subObject.put("commentContent", subCommentDTO.getCommentContent());
					subObject.put("createDate", subCommentDTO.getCreateDate().toString());

					subArray.add(subObject);
				}
				object.put("subCommentList", subArray);
			} else {
				object.put("subCommentList", "");
			}
			array.add(object);
		}

		return array;
	}

	public ArrayList<CommentDTO> getCommentList(int postId, String sort) throws SQLException{
		if (sort == null) {
			sort = "DESC";
		}
		ArrayList<CommentDTO> list = commentDAO.getCommentList(postId, sort);

		return list;
	}

	public void addNewComment(int postId, String commentContent, String userId) throws SQLException{
		PostDAO postDAO = new PostDAO();

		postDAO.putComments(postId);
		commentDAO.postComment(postId, commentContent, userId);
	}

	public void addNewSubComment(int postId, int parentId, String commentContent, String userId) throws SQLException{
		PostDAO postDAO = new PostDAO();
		postDAO.putComments(postId);
		commentDAO.postSubComment(parentId, commentContent, userId);
		commentDAO.putCommentChild(parentId);
	}
}
