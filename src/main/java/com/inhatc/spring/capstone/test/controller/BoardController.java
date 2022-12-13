package com.inhatc.spring.capstone.test.controller;


import java.io.IOException;

import java.io.InputStream;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.kohsuke.github.GHEventPayload.Public;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

import com.google.gson.JsonObject;
import com.inhatc.spring.capstone.entity.board.board;
import com.inhatc.spring.capstone.test.service.boardservice;
import com.querydsl.core.util.FileUtils;

import groovy.util.logging.Log4j2;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import com.inhatc.spring.capstone.content.service.ContentService;

@Controller

public class BoardController {
	
	@Autowired
	private boardservice boardservice;
	
    @GetMapping("editor/editor3")
    public String editorboard() {
		
		return "editor/editor3";
	}
    
    
	@PostMapping("/editor/editor4")
	public String boardWritePro(board board){
		
		//boardservice.write(board);
		System.out.println("-----------------------------------------------------------------------");
		//System.out.println(formtest.getPeople());
		System.out.println("백엔드,프론트엔드:"+board.getSelectbox());
		System.out.println("언어:"+board.getLanguage());
		System.out.println("백엔드 프론트엔드(언어별 인원수):"+board.getPeople());
		System.out.println("프로젝트 제목:"+board.getTitle());
        System.out.println("텍스트에디터 값:"+board.getContent());
		System.out.println("-----------------------------------------------------------------------");
		return "/main";
	
	}
	
	
	}




