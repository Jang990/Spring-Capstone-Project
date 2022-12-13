package com.inhatc.spring.capstone.test.controller;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import com.inhatc.spring.capstone.content.dto.DisplayedContentDTO;

import groovy.util.logging.Log4j2;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Controller
public class projectcontroller {
	
	
	@Getter
	@Setter
	class projectDto{
		String title ="제목";//프로젝트 이름
		String accessDate = "2022-12-12";//작성시간
		String usedLanguage="JAVA";//언어
		String writer= "김상덕";//작성자
		String email="";//이메일
		int viewCount=0;//조회수
		
		public projectDto(String title,String email,String writer,String accessDate,String usedLanguage,int viewCount) {
		this.title = title;
		this.writer =writer;
		this.email=email;
		this.accessDate = accessDate;
		this.usedLanguage = usedLanguage;
		this.viewCount = viewCount;
		}
	}
	
	@GetMapping("/projectboard")
	public String projectboard(Model model) {
		

		List<projectDto> projectList = new ArrayList<>();//프로젝트 리스트
		projectDto test1 = new projectDto("캡스톤 디자인관리", "email@mail.com", "김상덕", "2012-12-12", "자바", 76);
		projectDto test2 = new projectDto("이메일 관리", "1email@mail.com", "길드", "2012-12-17", "python", 79);
		projectDto test3 = new projectDto("드론 프로젝트", "3email@mail.com", "라이트", "2012-12-14", "C", 77);
		
		projectList.add(test1);
		projectList.add(test2);
		projectList.add(test3);
		
		model.addAttribute("project_",projectList);
		return "/projectboard";
	}

}
