package com.inhatc.spring.capstone.file.controller;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.inhatc.spring.capstone.file.service.TechImgService;

import lombok.RequiredArgsConstructor;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/tech-tag")
public class TechImgController {
	
	private final TechImgService techImgService;
	
	/** Tech 태그에 해당하는 리소스 위치 가져오기 */
	@GetMapping("/resource-loc/{tagId}")
	public ResponseEntity<String> getTechTagResourceLocation(Long tagId) {
		techImgService.getTechImgResourceSrc(tagId);
		
		return new ResponseEntity<>(HttpStatus.OK);
	}
}
