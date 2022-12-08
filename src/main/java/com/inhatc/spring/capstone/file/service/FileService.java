package com.inhatc.spring.capstone.file.service;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.UUID;

import org.springframework.stereotype.Service;

import lombok.extern.slf4j.Slf4j;

@Service
@Slf4j
public class FileService {
	public String uploadFile(String uploadPath, String oriFileName, byte[] fileData) throws IOException {
		UUID uuid = UUID.randomUUID();
        String extension = oriFileName.substring(oriFileName.lastIndexOf(".")); // jpg, gif 등등
        String savedFileName = uuid.toString() + extension; 
        String fileUploadFullUrl = uploadPath + "/" + savedFileName;
        FileOutputStream fos = new FileOutputStream(fileUploadFullUrl);
        fos.write(fileData);
        fos.close();
        return savedFileName;
	}
	
	public void deleteFile(String filePath) {
		File deleteFile = new File(filePath);
		
		if(deleteFile.exists()) {
			deleteFile.delete();
			log.info("파일을 삭제했습니다.");
		}
		else {
			log.info("파일이 존재하지 않습니다.");
		}
	}
}
