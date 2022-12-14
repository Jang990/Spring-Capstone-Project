package com.inhatc.spring.capstone.content.controller;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.inhatc.spring.capstone.content.dto.NewContentDTO;
import com.inhatc.spring.capstone.content.dto.DisplayedContentDTO;
import com.inhatc.spring.capstone.content.service.ContentDocumentService;
import com.inhatc.spring.capstone.content.service.ContentService;
import com.inhatc.spring.capstone.file.dto.DisplayedImageDTO;
import com.inhatc.spring.capstone.file.service.TemporaryImageService;

import lombok.RequiredArgsConstructor;

@RestController
@RequiredArgsConstructor
@RequestMapping("/test/content")
public class ContentController {
	
	private final ContentService contentService;
	
	/** 프로젝트 리스트 페이지에서 새로만들기 같은 create 버튼 클릭 */
	@PostMapping
	public String createContent(NewContentDTO contentDto) {
		if(contentDto.isRecruit()) {
			// 프로젝트 구인 게시글 생성 - 미구현
			contentService.createRecruitContent(contentDto);
		}
		else {
			// 프로젝트 게시글 생성
			try {
				contentService.createProjectContent(contentDto);
			} catch (IOException e) {
				return "임시저장폴더 -> 실제 저장폴더로 이미지 파일 이동 실패";
			}
		}
		return "완료";
	}
	
	/** 프로젝트 컨텐츠를 클릭해서 페이지 이동 */
	@GetMapping("/{contentId}")
	public String viewContent(@PathVariable("contentId")Long contentId) {
		// Cookie를 이용한 조회수 증가 확인 - 추후 구현
		contentService.viewProjectContent(contentId);
		return "";
	}
	
	/** 프로젝트 수정 버튼 클릭 */
	@PutMapping("/{contentId}")
	public String modifyProjectContent(NewContentDTO contentDto, @PathVariable("contentId")Long contentId) {
		// 컨트롤러에서 수정할 권한이 있는지 확인? - 미완료
		try {
			contentService.modifyProjectContent(contentDto);
		} catch (IOException e) {
			return "임시저장폴더 -> 실제 저장폴더로 이미지 파일 이동 실패";
		}
		
		// Model로 업데이트한 콘텐츠 보내기
		contentService.viewProjectContent(contentId);
		return "부분 완료";
	}
	
	
}