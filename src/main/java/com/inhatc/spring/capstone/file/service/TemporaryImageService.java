package com.inhatc.spring.capstone.file.service;

import java.awt.image.BufferedImage;
import java.io.IOException;

import javax.imageio.ImageIO;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import org.thymeleaf.util.StringUtils;

import com.inhatc.spring.capstone.file.dto.DisplayedImageDTO;

import lombok.RequiredArgsConstructor;

/** 
 * 글을 작성하기 전 임시저장 서비스
 */
@Service
@RequiredArgsConstructor
public class TemporaryImageService {
	@Value("${temporaryLocation}")
	private String temporaryLocation;
	
	private final FileService fileService;
	
	public DisplayedImageDTO saveTemporaryImage(MultipartFile ImgFile) throws IOException {
		String oriImgName = ImgFile.getOriginalFilename();
		String imgName = "";
		String imgUrl = "";

		if (StringUtils.isEmpty(oriImgName)) {
			throw new IllegalArgumentException();
		}
		
		BufferedImage bufferedImage = ImageIO.read(ImgFile.getInputStream());
	    int width = bufferedImage.getWidth();
	    int height = bufferedImage.getHeight();
		
		imgName = fileService.uploadFile(temporaryLocation, oriImgName, ImgFile.getBytes());
		imgUrl = "/images/temporary/" + imgName;
		
		return DisplayedImageDTO.builder()
				.width(width)
				.height(height)
				.byteSize(ImgFile.getSize())
				.originalName(oriImgName)
				.savedPath(imgUrl)
				.build();
	}
	
	/**
	 * 임시저장 파일을 실제 저장폴더로 이동함. 
	 */
	public DisplayedImageDTO convertTempImgToSavedImg(DisplayedImageDTO tempImg, String movedFolderName) throws IOException {
		String tempPath = tempImg.getSavedPath();
		String targetPath = tempPath.replaceFirst("temporary", movedFolderName);
		fileService.moveFile(tempPath, temporaryLocation);
		
		return DisplayedImageDTO.builder()
			.width(tempImg.getWidth())
			.height(tempImg.getHeight())
			.originalName(tempImg.getOriginalName())
			.byteSize(tempImg.getByteSize())
			.savedPath(targetPath)
			.build();
	}
	
}
