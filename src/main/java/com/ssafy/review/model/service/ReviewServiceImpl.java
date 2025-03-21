package com.ssafy.review.model.service;

import java.util.List;

import com.ssafy.review.model.dto.Review;
import com.ssafy.review.model.repository.ReviewRepository;
import com.ssafy.review.model.repository.ReviewRepositoryImpl;

public class ReviewServiceImpl implements ReviewService {

	// singleton 관리
	private static ReviewService service = new ReviewServiceImpl();
	private ReviewRepository repo = ReviewRepositoryImpl.getInstance();
	
	public ReviewServiceImpl() {
 
	}
	
	public static ReviewService getInstance() {
		return service;
	}
	
	// service 메소드는 repo에만 계속 던져주기
	@Override
	public List<Review> selectAll() {
		// TODO Auto-generated method stub
		return repo.selectAll();
	}

	@Override
	public List<Review> getReviewsbyId(String youtubeId) {
		return repo.getReviewsbyId(youtubeId);
	}
	
	@Override
	public Review select(int reviewId) {
		// TODO Auto-generated method stub
		return repo.select(reviewId);
	}

	// service 단에서 boolean 처리하는건 repo의 boolean이랑 뭐가 달라야할까???
	// service단의 예외 -> 사용자가 전달한 값의 유효성 검사
	@Override
	public boolean insertReview(Review review) {
		// 사용자 전달 값의 유효성 검사: 제공해준 값이 없으면 false
		if (review == null) return false;
		
		repo.insertReview(review);
		return true;
	}

	@Override
	public boolean updateReview(Review review) {
		// 사용자 전달 값의 유효성 검사: 제공해준 값이 없으면 false
		if (review == null) return false;
		
		repo.updateReview(review);
		return true;
	}

	@Override
	public boolean removeReview(int reviewId) {
		// 사용자가 제공한 id값 유효성 검증
		if (reviewId <= 0) return false;
		repo.deleteReview(reviewId);
		return true;
	}

	@Override
	public boolean updateViewCnt(int reviewId) {
		if (reviewId <= 0) return false;
		return repo.updateViewCnt(reviewId);
	}

}
