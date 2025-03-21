package com.ssafy.review.model.dto;

import java.sql.Timestamp;
//import java.sql.Date;
//import java.time.LocalDateTime;

// reviewcontroller: 개별 리뷰 조회, 생성, 수정

public class Review {
	
	private static int no = 1; // 누적시켜서 클래스변수로 저장, 고유 게시글번호++ 
	private int reviewId; // 고유 게시글 번호
	private String title; // 제목
	private String authorId; // 쓰니 > userId와 동일 
	private String contents; // 내용
	// 객체명 다르면 찾아보기
	private Timestamp createdAt; // localdatetime -> timestamp로 변경
	private int viewCnt;
	private String youtubeId; // FK로 video로 이어 1:N 관계 만들기 
	
	public Review() {
		// TODO Auto-generated constructor stub
	}
	
	// 입력받아야 하는 항목 3개
	// id, createdat같이 자동으로 돌아가는건 사실 상관업써
	// 어차피 객체 만들 때 같이 생성되기 땜무네
	public Review(int id, String title, String authorId, String contents, Timestamp createdAt,  int viewCnt, String youtubeId) {
		this.reviewId = id;
		this.title = title;
		// authorId 지금 받는게 맞는건지?
		this.authorId = authorId;
		this.contents = contents;
//		this.createdAt = LocalDateTime.now();
		this.createdAt = createdAt;
		// viewCnt 0으로 시작하는게 맞음?
		this.viewCnt = viewCnt;
		this.youtubeId = youtubeId; // 생성자 다 만들어두기 
	}

	public static int getNo() {
		return no;
	}

	public static void setNo(int no) {
		Review.no = no;
	}

	public int getReviewId() {
		return reviewId;
	}

	public void setReviewId(int reviewId) {
		this.reviewId = reviewId;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public String getAuthorId() {
		return authorId;
	}

	public void setAuthorId(String authorId) {
		this.authorId = authorId;
	}

	public String getContents() {
		return contents;
	}

	public void setContents(String contents) {
		this.contents = contents;
	}

	public Timestamp getCreatedAt() {
		return createdAt;
	}

	public void setCreatedAt(Timestamp timestamp) {
		this.createdAt = timestamp;
	}

	public int getViewCnt() {
		return viewCnt;
	}

	public void setViewCnt(int viewCnt) {
		this.viewCnt = viewCnt;
	}

	public String getYoutubeId() {
		return youtubeId;
	}

	public void setYoutubeId(String youtubeId) {
		this.youtubeId = youtubeId;
	}

	@Override
	public String toString() {
		return "Review [reviewId=" + reviewId + ", title=" + title + ", authorId=" + authorId + ", contents=" + contents
				+ ", createdAt=" + createdAt + ", viewCnt=" + viewCnt + ", youtubeId=" + youtubeId + "]";
	}
	
}
