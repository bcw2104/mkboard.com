package com.mkboard.util;

import java.io.File;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.multipart.MultipartFile;

import com.mkboard.entity.PostFileDTO;
import com.mkboard.filter.ImageFileFilter;

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
				dirPath = rootPath+File.separator+userPath+File.separator+userId+File.separator+profilePath;
				if(storedFileName != null) {
					file = new File(dirPath+File.separator+storedFileName);
					if(file.exists()) {
						return file;
					}
					else {
						return new File(rootPath+File.separator+defaultProfilePath);
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
							return new File(rootPath+File.separator+defaultProfilePath);
						}
					}
					else {
						return new File(rootPath+File.separator+defaultProfilePath);
					}
				}
			}
			else {
				return new File(rootPath+File.separator+defaultProfilePath);
			}
		}
		else {
			dirPath = rootPath+File.separator+userPath+File.separator+userId+File.separator+postPath+File.separator+postId;
			file = new File(dirPath +File.separator+storedFileName);
			if(file.exists()) {
				return file;
			}
		}

		return null;
	}

	public void createFile(String userId,int postId,String fileName,MultipartFile multipartFile) throws Exception {
		String dirPath;

		if(postId == 0) {
			dirPath = rootPath+File.separator+userPath+File.separator+userId+File.separator+profilePath;
		}
		else {
			dirPath = rootPath+File.separator+userPath+File.separator+userId+File.separator+postPath+File.separator+postId;
		}

		File file = new File(dirPath+File.separator+fileName);
		if(!file.exists()) {
			file.mkdirs();
		}

		multipartFile.transferTo(file);

	}

	public ArrayList<PostFileDTO> postFileParser(String userId,int postId ,List<MultipartFile> fileList, String fileType) throws Exception {
		ArrayList<PostFileDTO> parsedFileList = new ArrayList<PostFileDTO>();
		long current = System.currentTimeMillis();

		for(MultipartFile multipartFile : fileList) {
			String orgFileName = multipartFile.getOriginalFilename();
			String storedFileName = UUID.randomUUID().toString().replace("-", "")+"_"+orgFileName;

			createFile(userId,postId,storedFileName,multipartFile);

			PostFileDTO postFileDTO = new PostFileDTO();

			postFileDTO.setPostId(postId);
			postFileDTO.setUserId(userId);
			postFileDTO.setOrgFileName(orgFileName);
			postFileDTO.setStoredFileName(storedFileName);
			postFileDTO.setFileSize(multipartFile.getSize());
			postFileDTO.setCreateDate(new Timestamp(current));
			postFileDTO.setFileType(fileType);

			parsedFileList.add(postFileDTO);
		}

		return parsedFileList;
	}

	private void clearDirectory(File[] files) {

		 for (int i = 0; i < files.length; i++) {
			 if(files[i].isDirectory()) {
				 clearDirectory(files[i].listFiles());
			}
			 files[i].delete();
		 }
	}

	public void fileRemover(String userId,int postId,String storedFileName) {
		String dirPath;


		if(postId == 0) {
			dirPath = rootPath+File.separator+userPath+File.separator+userId;
		}
		else {
			dirPath = rootPath+File.separator+userPath+File.separator+userId+File.separator+postPath+File.separator+postId;
		}

		File file = null;

		if(storedFileName != null) {
			file = new File(dirPath+File.separator+storedFileName);
		}else {
			file = new File(dirPath);
		}

		 if(file.exists()) {
			if(file.isDirectory()) {
				clearDirectory(file.listFiles());
			 }
			file.delete();
		 }
	}
}
