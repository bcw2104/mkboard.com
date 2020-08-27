package com.codeplayground.serviceOthers;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.codeplayground.entity.FileDTO;
import com.codeplayground.mapper.PostFileMapperInterface;
import com.codeplayground.util.FileTools;

@Service
public class PostFileService {

	@Autowired
	private PostFileMapperInterface mapper;
	@Autowired
	private FileTools fileTools;
	@Autowired
	private SequenceService sequenceService;

	public FileDTO findOne(String userId,int postId,String storedFileName) throws NumberFormatException{
		HashMap<String, Object> hashMap = new HashMap<String, Object>();
		hashMap.put("userId", userId);
		hashMap.put("postId", postId);
		hashMap.put("storedFileName", storedFileName);

		return mapper.selectOne(hashMap);
	}

	public ArrayList<FileDTO> findList(String userId,int postId) {
		HashMap<String, Object> hashMap = new HashMap<String, Object>();
		hashMap.put("userId", userId);
		hashMap.put("postId", postId);

		return mapper.selectList(hashMap);
	}

	public void register(String userId,int postId,List<MultipartFile> fileList) throws Exception {
		ArrayList<FileDTO> parsedFileList = fileTools.fileParser(userId,postId, fileList);

		for(FileDTO fileDTO : parsedFileList) {
			fileDTO.setFileNo(sequenceService.getNextSequence("file"));
			mapper.insert(fileDTO);
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
