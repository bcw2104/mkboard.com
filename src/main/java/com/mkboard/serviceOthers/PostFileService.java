package com.mkboard.serviceOthers;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.mkboard.entity.PostFileDTO;
import com.mkboard.mapper.PostFileMapperInterface;
import com.mkboard.util.FileTools;

@Service
public class PostFileService {

	@Autowired
	private PostFileMapperInterface mapper;
	@Autowired
	private FileTools fileTools;
	@Autowired
	private SequenceService sequenceService;

	public PostFileDTO findOne(String userId,int postId,String storedFileName) throws NumberFormatException{
		HashMap<String, Object> hashMap = new HashMap<String, Object>();
		hashMap.put("userId", userId);
		hashMap.put("postId", postId);
		hashMap.put("storedFileName", storedFileName);

		return mapper.selectOne(hashMap);
	}

	public ArrayList<PostFileDTO> findList(String userId,int postId,String fileType) {
		HashMap<String, Object> hashMap = new HashMap<String, Object>();
		hashMap.put("userId", userId);
		hashMap.put("postId", postId);
		hashMap.put("fileType", fileType);

		return mapper.selectList(hashMap);
	}

	public void register(String userId,int postId,List<MultipartFile> fileList,String fileType) throws Exception {
		ArrayList<PostFileDTO> parsedFileList = fileTools.postFileParser(userId,postId, fileList,fileType);

		for(PostFileDTO postFileDTO : parsedFileList) {
			postFileDTO.setFileNo(sequenceService.getNextSequence("file"));
			mapper.insert(postFileDTO);
		}
	}

	public void delete(String userId,int postId,String storedFileName) throws NumberFormatException{
		fileTools.fileRemover(userId, postId, storedFileName);

		HashMap<String, Object> hashMap = new HashMap<String, Object>();
		hashMap.put("userId", userId);
		hashMap.put("postId", postId);
		hashMap.put("storedFileName", storedFileName);

		mapper.delete(hashMap);
	}


}
