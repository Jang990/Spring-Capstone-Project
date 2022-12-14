package com.inhatc.spring.capstone.tag.service;

import java.util.List;

import javax.persistence.EntityNotFoundException;

import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import com.inhatc.spring.capstone.tag.dto.DisplayedTagDTO;
import com.inhatc.spring.capstone.tag.entity.Tag;
import com.inhatc.spring.capstone.tag.repository.TagRepository;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class TagService {
	
	private final TagRepository tagRepository;
	
	/** 유사한 태그 검색 */
	public List<DisplayedTagDTO> searchSimilarTags(String tag, Pageable pageable) {
		return tagRepository.getSimilarTags(tag, pageable);
	}
	
	/** 상위 태그 5개 검색 */
	public List<DisplayedTagDTO> searchTop5Tags() {
		Pageable pageable = PageRequest.of(0, 5);
		return tagRepository.getSimilarTags(null, pageable);
	}
	
	/** 커스텀 태그 저장 */
	public Tag createCustomTag(DisplayedTagDTO contentTagDto) {
		Tag savedTag = Tag.createCustomTag(contentTagDto);
		return tagRepository.save(savedTag);
	}
	
	/** 이미 존재하는 태그 가져오기 */
	public Tag getExistTag(DisplayedTagDTO contentTagDto) {
		if(contentTagDto.getTagId() == null) 
			throw new IllegalArgumentException();
		
		Tag existTag = tagRepository.findById(contentTagDto.getTagId()).orElseThrow(EntityNotFoundException::new);
		
		if(!existTag.getName().equals(contentTagDto.getTagName().toLowerCase()) 
				|| !existTag.getType().toString().equals(contentTagDto.getTagType().toUpperCase()))
			throw new IllegalArgumentException();
		
		existTag.addTagToContent();
		return existTag;
	}
	
	/** 있는지 없는지 알 수 없는 태그 검색해서 만들려면 만들기 */
	public Tag getUnknownTag(DisplayedTagDTO contentTagDto) {
		Tag tag = tagRepository.findByName(contentTagDto.getTagName())
				.orElseGet(() -> createCustomTag(contentTagDto));
		return tag;
	}
	
}
