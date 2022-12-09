package com.inhatc.spring.capstone.file.service;

import static org.junit.jupiter.api.Assertions.*;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.transaction.annotation.Transactional;

import com.inhatc.spring.capstone.content.dto.DisplayedContentDTO;
import com.inhatc.spring.capstone.content.dto.NewContentDTO;
import com.inhatc.spring.capstone.content.service.ContentDocumentService;
import com.inhatc.spring.capstone.content.service.ContentService;
import com.inhatc.spring.capstone.file.dto.DisplayedImageDTO;
import com.inhatc.spring.capstone.user.dto.UsersJoinDTO;
import com.inhatc.spring.capstone.user.entity.Users;
import com.inhatc.spring.capstone.user.repository.UsersRepository;

@SpringBootTest
class ContentImageServiceTest {

	/*
	 * 이미지가 있는 콘텐츠 테스트
	 */
	
	@Autowired
	ContentService contentService;
	@Autowired
	TemporaryImageService tempImgService;
	@Autowired
	UsersRepository userRepository;
	@Autowired
	ContentDocumentService contentDocumentService;
	
	final String fileName = "rogo"; //파일명
	final String contentType = "png"; //파일타입
	final String filePath = "src/main/resources/static/img/"+fileName+"."+contentType; //파일경로

	// 이미지가 있는 게시글 생성 데이터
	NewContentDTO createContentWithImage(Users user, String title, List<String> savedTempImg) throws IOException {
//		String imgName = "222d6b0e-818d-47ea-a9f5-cfac9dc0a242.png";
		String str = ""; // 게시글 내용
		str += "<div>" + "<img src='/images/temporary/" + savedTempImg.get(0) + "' style='width=1920px; height=1080px;' alt='캡스톤 로고' bytesize='3661570'> 이상한 글귀<br>"
				+ "<h2>제목내용</h2>"
				+ "<div>필요 없는 값" + "<h3>제목내용</h3>" + "</div>";
		
		for (int i = 1; i < savedTempImg.size(); i++) {
			str += "aaaa" + "<img src='/images/temporary/" + savedTempImg.get(i) + "' style='width=1920px; height=1080px;' alt='캡스톤 로고' bytesize='3661570'> 이상한 글귀<br>";
		}
		
		str += "</div>";

		return NewContentDTO.builder()
				.userId(user.getId())
				.userEmail(user.getEmail())
				.title(title)
				.content(str)
				.usedLanguage("JAVA")
				.isRecruit(false)
				.build();
	}

	// 이미지 임시 저장
	DisplayedImageDTO saveImg() throws IOException {
    	FileInputStream fileInputStream = new FileInputStream(filePath);
    	MockMultipartFile tempImage = new MockMultipartFile(fileName, fileName+"."+contentType, contentType, fileInputStream);
    	return tempImgService.saveTemporaryImage(tempImage);
	}
	
	// 사용자 생성
	Users createUser() {
		UsersJoinDTO userDto = new UsersJoinDTO("test@test.com", "홍길동", "testPassword");		
		return userRepository.save(Users.createUser(userDto));
	}
	
	@Test
	@Transactional
	@DisplayName("이미지가 있는 컨텐츠 저장")
	void testSaveContentImg() throws IOException {
		Users user = createUser();
		DisplayedImageDTO savedTempImg1 = saveImg();
		DisplayedImageDTO savedTempImg2 = saveImg();
		DisplayedImageDTO savedTempImg3 = saveImg();
		
		List<String> tempImgLoc = new ArrayList<>();
		tempImgLoc.add(savedTempImg1.getSavedPath().substring(1).split("/")[2].trim());
		tempImgLoc.add(savedTempImg2.getSavedPath().substring(1).split("/")[2].trim());
		tempImgLoc.add(savedTempImg3.getSavedPath().substring(1).split("/")[2].trim());
		NewContentDTO contentDto = createContentWithImage(user, "테스트 게시물", tempImgLoc);
		
		DisplayedContentDTO createdContentDto = contentService.createProjectContent(contentDto);
		List<DisplayedImageDTO> imageElements = contentDocumentService.extractImageElement(createdContentDto.getContent());
		
		assertEquals(savedTempImg1.getSavedPath().replace("temporary", "content"), imageElements.get(0).getSavedPath());
		assertEquals(savedTempImg2.getSavedPath().replace("temporary", "content"), imageElements.get(1).getSavedPath());
		assertEquals(savedTempImg3.getSavedPath().replace("temporary", "content"), imageElements.get(2).getSavedPath());
	}

}
