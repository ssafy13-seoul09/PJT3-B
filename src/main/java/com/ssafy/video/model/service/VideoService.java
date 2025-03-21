package com.ssafy.video.model.service;
import java.util.List;

import com.ssafy.review.model.dto.Review;
import com.ssafy.video.model.dto.Video;

public interface VideoService {
	// video 
	// 전체 비디오를 선택한다.
	public abstract List<Video> selectAll();
	
	// 조회수 가장 많은 비디오
	public abstract List<Video> selectPopularVideos();
	
	// 부위별로 비디오를 선택한다.
	public abstract List<Video> selectBodypartVideos(String fitBodyPart);
	
	// 일치하는 리뷰 리스트 불러오기 
	public abstract void getReviewbyId(String youtubeId);
	
	// 개별 video 가져오기
	public abstract Video select(String youtubeId);
	
	// video 조회수 올리기
	public boolean updateViewCnt(String youtubeId);


}
