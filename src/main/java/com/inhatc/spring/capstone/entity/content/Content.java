package com.inhatc.spring.capstone.entity.content;

import java.sql.Timestamp; 
import java.util.ArrayList;
import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;

import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import com.inhatc.spring.capstone.entity.file.SavedFile;
import com.inhatc.spring.capstone.entity.user.Users;

@Entity
@Table(name = "content")
/** 프로젝트 소개 게시글 테이블 엔티티 */
public class Content {
	/*
	게시글번호- PK
	제목
	내용 
	글등록시간
	언어 (- 깃허브 API로 링크받아서 등록)
	사용자 번호 - FK (- 메일, 이름)
	
	이미지
	추천 수
	 */
	
	@Id
	@Column(name = "project_content_id")
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	
	@ManyToOne
//	@Column(name = "writer_id")
	@JoinColumn(name = "user_id")
	private Users writer; // 게시물 작성자
	private String title; // 제목
	private String content; // 내용
	private String used_language; // 사용 언어 - 나중에 GitHub API를 사용하면서 타입을 바꿀 것
	private int view_count; // 조회수 - 쿠키로 조회수 중복을 제거할 것이다.
	
	@CreationTimestamp
	private Timestamp date_created;  // 글 등록 시간
	@UpdateTimestamp
	private Timestamp last_updated; // 글 수정 시간
	
	@OneToMany(mappedBy = "id")
	List<SavedFile> files = new ArrayList<>();
}