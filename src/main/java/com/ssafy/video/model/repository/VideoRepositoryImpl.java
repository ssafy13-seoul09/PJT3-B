package com.ssafy.video.model.repository;
import com.ssafy.review.model.dto.Review;
import com.ssafy.review.model.repository.ReviewRepository;
import com.ssafy.review.model.repository.ReviewRepositoryImpl;
import com.ssafy.util.DBUtil;
import com.ssafy.video.model.dto.Video;

import java.sql.*;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.ssafy.video.model.dto.Video;

public class VideoRepositoryImpl implements VideoRepository{
	// 영화를 담을 객체를 Singleton Pattern으로 관리해준다.
	private static VideoRepository dao = new VideoRepositoryImpl();
	private static ReviewRepository repoReview = ReviewRepositoryImpl.getInstance();
	
	
	private VideoRepositoryImpl() {
	}
	
	public static VideoRepository getInstance() {
		return dao;
	}
	
	
	// DB를 생성 및 연결하고 해제하는 객체를 불러온다.
	DBUtil util = DBUtil.getInstance();
	
	
	@Override
	public List<Video> selectAll() {
		List<Video> list = new ArrayList<>();
		String sql = "SELECT * FROM video";
		Connection conn = null;
		Statement st = null;
		ResultSet rs = null;
		
		try {
			// DB와 연결을 생성하고, statement를 생성하고 rs에 결과를 받는다.
			conn = util.getConnection();
			st = conn.createStatement();
			rs = st.executeQuery(sql);
			
			// rs에 받은 결과를 video에 담고, list에 추가한다.
			while(rs.next()) {
				Video video = new Video(rs.getString("youtube_id"), rs.getString("channel_name"), rs.getInt("view_count"), rs.getString("fitpart_name"), rs.getString("title"));		
				
				list.add(video);
			}
			
			
		} catch (SQLException e) {
			
			e.printStackTrace();
		}finally {
			util.close(rs, st, conn);
		}
		
		return list;

	}

	// 개별 video 불러오기 
	@Override
	public Video select(String youtubeId) {
		Connection conn = null;
		PreparedStatement pst = null;
		ResultSet rs = null;
		
		String sql = "SELECT * FROM video WHERE youtube_id=?";
		
		Video video = null;
		try {
			// 첫번째 물음표에 들어갈 변수를 설정한다.
			conn = util.getConnection();
			pst = conn.prepareStatement(sql);
			pst.setString(1, youtubeId);
			
			rs = pst.executeQuery();
			
			// 개별 데이터가 존재한다면 video에 담는다.
			if(rs.next()) {
				video = new Video(rs.getString("youtube_id"), rs.getString("channel_name"), rs.getInt("view_count"), rs.getString("fitpart_name"), rs.getString("title"));		
				
			}
			
		}catch (SQLException e) {
			e.printStackTrace();
		}finally {
			util.close(rs, pst, conn);
		}
		
		return video;
		
	}

	@Override
	public boolean updateViewCnt(String youtubeId) {
		Connection conn = null;
		PreparedStatement pst = null;
		int result = 0;
		// ResultSet rs = null;
		
		
		String sql = "UPDATE video SET view_count = view_count + 1 WHERE youtube_id=?";
		
		try {
			conn = util.getConnection();
			pst = conn.prepareStatement(sql);
			pst.setString( 1, youtubeId);
			
			result = pst.executeUpdate(); 
			
		}
		catch (SQLException e) {
			e.printStackTrace();
		}
		finally {
			util.close(pst, conn);
		}			
		
		return result == 1;
		
	}

	// youtubeId와 같은 id 가지는 리뷰 불러오기 
	@Override
	public List<Review> getReviewbyId(String youtubeId){
		throw new UnsupportedOperationException();
	}
	
}
