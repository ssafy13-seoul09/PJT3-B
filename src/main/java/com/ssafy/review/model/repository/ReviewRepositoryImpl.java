// loginuser > session단위로 저장
// act OOO action XXX 
// repo > db친화, 조회(전체/개별), 수정, 삭제
package com.ssafy.review.model.repository;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import com.ssafy.review.model.dto.Review;
import com.ssafy.util.DBUtil;

public class ReviewRepositoryImpl implements ReviewRepository {
	
	// repo 싱글턴으로 관리  
	private final DBUtil util = DBUtil.getInstance();
	// 싱글턴으로 리뷰 관리 > 전체 리뷰 관리할 레포 
	private static ReviewRepository repo = new ReviewRepositoryImpl();
	
	private ReviewRepositoryImpl() {
		
	}
	
	// 싱글턴 -> 인스턴스 하나 생성
	public static ReviewRepository getInstance() {
		return repo;
	}
	
	@Override
	// review 전체 리턴 
	public List<Review> selectAll() {
		
		List<Review> reviewList = new ArrayList<>();
		
		// 모든 리뷰 반환하기
 		String sql = "SELECT * FROM review";
 		Connection conn = null; //DB 연결 객체
 		PreparedStatement pstmt = null; //sql문 실행 객체
 		ResultSet rs = null; //sql문 실행 결과 집합
 		
 		// 리뷰 전체 반환
 		try {
 			
 			conn = util.getConnection(); // 연결 객체 얻기
 			pstmt = conn.prepareStatement(sql);
 			rs = pstmt.executeQuery(); // sql 실행
 			
			while(rs.next()) {
				
				Review review = new Review(rs.getInt("review_id"), rs.getString("title"), rs.getString("author_id"), rs.getString("content"), rs.getTimestamp("created_at"), rs.getInt("view_count"), rs.getString("youtube_id"));
				// reviewId autoincrement인데 set 할 필요?
				reviewList.add(review);
			}
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			util.close(rs, pstmt, conn); // 자원 반환
		}
		
		return reviewList;
	}

	// youtubeId와 일치하는 리뷰 리스트 반환하기 (videorepo와 소통)
	@Override
	public List<Review> getReviewsbyId(String youtubeId) {
		List<Review> reviewList = new ArrayList<>();
		
		// 모든 리뷰 반환하기
 		String sql = "SELECT * FROM review WHERE youtube_id = ?";
 		Connection conn = null; //DB 연결 객체
 		PreparedStatement pstmt = null; //sql문 실행 객체
 		ResultSet rs = null; //sql문 실행 결과 집합
 		
 		// 리뷰 전체 반환
 		try {
 			
 			conn = util.getConnection(); // 연결 객체 얻기
 			pstmt = conn.prepareStatement(sql);
 			// pstmt string 
 			pstmt.setString(1, youtubeId);
 			rs = pstmt.executeQuery(); // sql 실행
 			
			while(rs.next()) {
				Review review = new Review(rs.getInt("review_id"), 
						rs.getString("title"), 
						rs.getString("author_id"), 
						rs.getString("content"), 
						rs.getTimestamp("created_at"), 
						rs.getInt("view_count"), 
						rs.getString("youtube_id"));

				
				reviewList.add(review);
			}
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			util.close(rs, pstmt, conn); // 자원 반환
		}
		
		return reviewList;
	}
	
	// 리뷰 1개 찾기 
	@Override
	public Review select(int reviewId) {
 		String sql = "SELECT * FROM review WHERE review_id = ?";
 		Connection conn = null; //DB 연결 객체
 		PreparedStatement pstmt = null; //sql문 실행 객체
 		ResultSet rs = null; //sql문 실행 결과 집합
 		Review review = null;
		try {
 			conn = util.getConnection(); // 연결 객체 얻기
 			pstmt = conn.prepareStatement(sql);
 			pstmt.setInt(1, reviewId);
 			rs = pstmt.executeQuery(); // sql 실행
 			
			while(rs.next()) {
				review = new Review(rs.getInt("review_id"), 
						rs.getString("title"), 
						rs.getString("author_id"), 
						rs.getString("content"), 
						rs.getTimestamp("created_at"), 
						rs.getInt("view_count"), 
						rs.getString("youtube_id"));
			}
 			
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			util.close(rs, pstmt, conn); // 자원 반환
		}
		
		return review;
	}

	// 리뷰 새로 생성 
	@Override
	public boolean insertReview(Review review) {
		
 		String sql = "INSERT INTO review(youtube_id, author_id, title, content) VALUES(?, ?, ?, ?)";

 		Connection conn = null;
 		PreparedStatement pstmt = null;
 		boolean result = false;
 		
 		try {
 			conn = util.getConnection();
 			pstmt = conn.prepareStatement(sql);
 			pstmt.setString(1, review.getYoutubeId());
 			pstmt.setString(2, review.getAuthorId());
 			pstmt.setString(3, review.getTitle());
 			pstmt.setString(4, review.getContents());
 			// sql 실행 결과 영향받은 행 수가 0 이상인 경우 정상 실행
			// executeupdate의 리턴값은 정상적으로 db에 업데이트된 행 개수 
 			result = pstmt.executeUpdate() > 0 ? true : false;
			
 		} catch (SQLException e) {
 			e.printStackTrace();
 		} finally {
 	 		util.close(pstmt, conn);
 		}
 		
		return result;
	}

	// 개별 리뷰 수정 
	@Override
	public boolean updateReview(Review review) {
		// 수정할 리뷰 내용 불러온 후 수정하기 ? or 덮어씌우기? 
		// id 기반으로 리뷰 찾아야 하니까 id도 받아야쥐? 
 		String sql = "UPDATE review SET author_id=?, title=?, content=? WHERE review_id=?";

 		Connection conn = null;
 		PreparedStatement pstmt = null;
 		boolean result = false;
 		
 		try {
 			conn = util.getConnection();
 			pstmt = conn.prepareStatement(sql);
 			pstmt.setString(1, review.getAuthorId());
 			pstmt.setString(2, review.getTitle());
 			pstmt.setString(3, review.getContents());
 			pstmt.setInt(4, review.getReviewId());
 			// sql 실행 결과 영향받은 행 수가 0 이상인 경우 정상 실행
			// executeupdate의 리턴값은 정상적으로 db에 업데이트된 행 개수 
 			result = pstmt.executeUpdate() > 0 ? true : false;
			
 		} catch (SQLException e) {
 			e.printStackTrace();
 		} finally {
 	 		util.close(pstmt, conn);
 		}
 		
		return result;
		
	}

	// 개별 리뷰 삭제
	@Override
	public boolean deleteReview(int reviewId) {
 		String sql = "DELETE FROM review WHERE review_id=?";

 		Connection conn = null;
 		PreparedStatement pstmt = null;
 		boolean result = false;
 		
 		try {
 			conn = util.getConnection();
 			pstmt = conn.prepareStatement(sql);
 			pstmt.setInt(1, reviewId);
 			
 			result = pstmt.executeUpdate() > 0 ? true : false;
			
 		} catch (SQLException e) {
 			e.printStackTrace();
 		} finally {
 	 		util.close(pstmt, conn);
 		}
 		
		return result;
		
	}

	// 리뷰 조회당 조회수 늘리기 
	@Override
	public boolean updateViewCnt(int reviewId) {
 		String sql = "UPDATE review SET view_count = view_count+1 WHERE review_id=?";

 		Connection conn = null;
 		PreparedStatement pstmt = null;
 		boolean result = false;
 		
 		try {
 			conn = util.getConnection();
 			pstmt = conn.prepareStatement(sql);
 			pstmt.setInt(1, reviewId);
 			
 			result = pstmt.executeUpdate() == 1;
			
 		} catch (SQLException e) {
 			e.printStackTrace();
 		} finally {
 	 		util.close(pstmt, conn);
 		}
 		
		return result;
	}

}
