package com.codeplayground.serviceOthers;

import java.util.ArrayList;
import java.util.Iterator;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.codeplayground.entity.CommentDTO;
import com.codeplayground.entity.SubCommentDTO;
import com.codeplayground.mapper.CommentMapperInterface;

@Service
public class CommentOtherService {
	@Autowired
	private CommentMapperInterface mapper;

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
			object.put("userId", (boardId.equals("anonymous") ? "비공개" : commentDTO.getUserId()));
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
					subObject.put("userId", (boardId.equals("anonymous") ? "비공개" : subCommentDTO.getUserId()));
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
