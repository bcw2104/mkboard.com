package com.mkboard.serviceOthers;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.mkboard.entity.CommentDTO;
import com.mkboard.entity.SubCommentDTO;
import com.mkboard.mapper.CommentMapperInterface;
import com.mkboard.mapper.SubCommentMapperInterface;

@Service
public class CommentService{

	@Autowired
	private CommentMapperInterface mapper;
	@Autowired
	private SubCommentMapperInterface subMapper;

	public int getCount(int postId, String userId) {
		HashMap<String, Object> hashMap = new HashMap<String, Object>();
		hashMap.put("postId", postId);
		hashMap.put("userId", userId);

		return mapper.getCount(hashMap);
	}

	public int getSubCount(int parentId, String userId) {
		HashMap<String, Object> hashMap = new HashMap<String, Object>();
		hashMap.put("parentId", parentId);
		hashMap.put("userId", userId);

		return subMapper.getCount(hashMap);
	}

	public ArrayList<CommentDTO> findList(int postId, String userId, String sortType) {
		HashMap<String, Object> hashMap = new HashMap<String, Object>();
		hashMap.put("postId", postId);
		hashMap.put("userId", userId);
		hashMap.put("sortType", sortType);

		return mapper.selectList(hashMap);
	}

	public ArrayList<SubCommentDTO> findSubList(int parentId, String userId) {
		HashMap<String, Object> hashMap = new HashMap<String, Object>();
		hashMap.put("parentId", parentId);
		hashMap.put("userId", userId);

		return subMapper.selectList(hashMap);
	}

	public void increaseChildCount(int commentId) {
		mapper.increaseChildCount(commentId);
	}

	@SuppressWarnings("unchecked")
	public JSONArray convertToJson(ArrayList<CommentDTO> commentList, String boardId) {
		Iterator<CommentDTO> itr = commentList.iterator();
		JSONArray array = new JSONArray();

		while (itr.hasNext()) {
			CommentDTO commentDTO = itr.next();
			JSONObject object = new JSONObject();
			object.put("commentId", commentDTO.getCommentId());
			object.put("userNickName", (boardId.equals("anonymous") ? "비공개" : commentDTO.getUserNickName()));
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
					subObject.put("userNickName", (boardId.equals("anonymous") ? "비공개" : subCommentDTO.getUserNickName()));
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
}
