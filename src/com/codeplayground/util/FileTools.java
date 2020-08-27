package com.codeplayground.util;

import java.io.File;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.multipart.MultipartFile;

import com.codeplayground.entity.FileDTO;
import com.codeplayground.filter.ImageFileFilter;

public class FileTools {

	@Value("${file.root}")
	private String rootPath;
	@Value("${file.user}")
	private String userPath;
	@Value("${file.user.post}")
	private String postPath;
	@Value("${file.user.profile}")
	private String profilePath;
	@Value("${file.default.profile}")
	private String defaultProfilePath;

	public File findFile(String userId,int postId,String storedFileName) {

		File file = null;
		String dirPath;

		if(postId == 0) {
			if(userId != null) {
				dirPath = rootPath+"\\"+userPath+"\\"+userId+"\\"+profilePath;
				if(storedFileName != null) {
					file = new File(dirPath+"\\"+storedFileName);
					if(file.exists()) {
						return file;
					}
					else {
						return new File(rootPath+"\\"+defaultProfilePath);
					}
				}
				else {
					file = new File(dirPath);
					if(file.exists() && file.isDirectory()) {
						boolean exist = false;
						for(File ele : file.listFiles(new ImageFileFilter())) {
							if(ele.isFile()) {
								file = ele;
								exist = true;
								break;
							}
						}
						if(exist) {
							return file;
						}
						else {
							return new File(rootPath+"\\"+defaultProfilePath);
						}
					}
					else {
						return new File(rootPath+"\\"+defaultProfilePath);
					}
				}
			}
			else {
				return new File(rootPath+"\\"+defaultProfilePath);
			}
		}
		else {
			dirPath = rootPath+"\\"+userPath+"\\"+userId+"\\"+postPath+"\\"+postId;
			file = new File(dirPath +"\\"+storedFileName);
			if(file.exists()) {
				return file;
			}
		}

		return null;
	}

	public void createFile(String userId,int postId,String fileName,MultipartFile multipartFile) throws Exception {
		String dirPath;

		if(postId == 0) {
			dirPath = rootPath+"\\"+userPath+"\\"+userId+"\\"+profilePath;
		}
		else {
			dirPath = rootPath+"\\"+userPath+"\\"+userId+"\\"+postPath+"\\"+postId;
		}

		File file = new File(dirPath+"\\"+fileName);
		if(!file.exists()) {
			file.mkdirs();
		}

		multipartFile.transferTo(file);

	}

	public ArrayList<FileDTO> fileParser(String userId,int postId ,List<MultipartFile> fileList) throws Exception {
		ArrayList<FileDTO> parsedFileList = new ArrayList<FileDTO>();
		long current = System.currentTimeMillis();

		for(MultipartFile multipartFile : fileList) {
			String orgFileName = multipartFile.getOriginalFilename();
			String storedFileName = UUID.randomUUID().toString().replace("-", "")+"_"+orgFileName;

			createFile(userId,postId,storedFileName,multipartFile);

			FileDTO fileDTO = new FileDTO();

			fileDTO.setPostId(postId);
			fileDTO.setUserId(userId);
			fileDTO.setOrgFileName(orgFileName);
			fileDTO.setStoredFileName(storedFileName);
			fileDTO.setFileSize(multipartFile.getSize());
			fileDTO.setCreateDate(new Timestamp(current));

			parsedFileList.add(fileDTO);
		}

		return parsedFileList;
	}

	private void deleteDirectory(File[] files) {

		 for (int i = 0; i < files.length; i++) {
			 if(files[i].isDirectory()) {
				 deleteDirectory(files[i].listFiles());
			}
			else {
				files[i].delete();
			}
		 }
	}

	public void fileRemover(String userId,int postId,String storedFileName) {
		String dirPath;
		if(postId == 0) {
			dirPath = rootPath+"\\"+userPath+"\\"+userId+"\\"+profilePath;
		}
		else {
			dirPath = rootPath+"\\"+userPath+"\\"+userId+"\\"+postPath+"\\"+postId;
		}
		File file = null;

		if(storedFileName != null) {
			file = new File(dirPath+"\\"+storedFileName);
		}else {
			file = new File(dirPath);
		}

		 if(file.exists()) {
			if(file.isDirectory()) {
				deleteDirectory(file.listFiles());
			 }
			else {
				file.delete();
			}
		 }
	}
}
