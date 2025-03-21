package com.ssafy.review.model.service;

import java.util.List;

import com.ssafy.review.model.dto.Review;


// 서비스 > 사용자쪽 (개별 리뷰 조회, 작성, 수정)
public interface ReviewService {
	// 전체 리뷰 조회 (필요?? 일단 작성)
	public abstract List<Review> selectAll();

	// 리뷰 조회 > youtubeId와 일치하는 리뷰 찾기 (video와 소통)
	public abstract List<Review> getReviewsbyId(String youtubeId);
	
	// id 기반으로 리뷰 1개 찾기
	public abstract Review select(int reviewId);

	// 리뷰 작성
	public abstract boolean insertReview(Review review);
	
	// 리뷰 수정
	public abstract boolean updateReview(Review review);
	
	// 리뷰 삭제
	public abstract boolean removeReview(int reviewId);
	
	public abstract boolean updateViewCnt(int reviewId);
	
	
}
