package com.ssafy.video.model.repository;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.ssafy.review.model.dto.Review;
import com.ssafy.review.model.repository.ReviewRepository;
import com.ssafy.review.model.repository.ReviewRepositoryImpl;
import com.ssafy.video.model.dto.Video;

public class MemoryVideoRepository implements VideoRepository{
	// 영화를 담을 객체를 Singleton Pattern으로 관리해준다.
		private static VideoRepository repo = new MemoryVideoRepository();
		private static ReviewRepository repoReview = ReviewRepositoryImpl.getInstance();
		
		// Video를 담을 Map을 형성해준다.
		private Map<Integer, Video> map = new HashMap<>();
		
		// 생성한 객체에 Map 형식으로 데이터를 관리한다.
		private MemoryVideoRepository() {
			map.put(1, new Video("https://www.youtube.com/watch?v=7TLk7pscICk", "복부짱", 10, "복부", "누워서 5분 복부"));
			map.put(2, new Video("https://www.youtube.com/watch?v=cMkZ6A7wngk", "전신짱", 15, "전신", "전신 올인원"));
			map.put(3, new Video("https://www.youtube.com/watch?v=4kZHHPH6heY", "전신근력짱", 18, "전신", "전신운동 근력 유산소"));
			map.put(4, new Video("https://www.youtube.com/watch?v=DehgWgRde-I", "복부킹왕짱", 25, "복부", "악마의 복근 운동"));
			map.put(5, new Video("https://www.youtube.com/watch?v=DWYDL-WxF1U", "하체짱", 35, "하체", "하체날, 딱 10분밖에 없다면-스쿼트 10가지 동작"));
			map.put(6, new Video("https://www.youtube.com/watch?v=Hx8Lc_0hUaI", "상체짱", 28, "상체", "운동할 시간이 없다는 사람에게 보여주세요... 제발"));
			map.put(7, new Video("https://www.youtube.com/watch?v=C4_2puAkxfs", "하체킹왕짱", 40, "하체", "하루 한 번! 꼭 해야하는 10분 기본 하체근력 운동 홈트 (층간소음🙅🏻‍♀️)"));
			map.put(8, new Video("https://www.youtube.com/watch?v=UdvFhqxaBNo&list=PL2OrN5q5pzIvKy9bqhqYkZgklLZLVG8bF&index=1", "복부킹왕짱", 3, "복부", "하복부 지방 확실하게 태우는 단 10분 운동 - No 반복, No 휴식"));
			
		}
		
		public static VideoRepository getInstance() {
			return repo;
		}
		
		@Override
		public List<Video> selectAll() {
			
			// 모든 key에 대한 데이터를 ArrayList에 넣고, 반환한다.
			List<Video> tmp = new ArrayList<>();
			for(int key: map.keySet()) {
				tmp.add(map.get(key));
			}
			
			return tmp;
		}

		// 개별 video 불러오기 
		@Override
		public Video select(String youtubeId) {
			// youtubeId 기반으로 일치하는 video 리턴
			Video vid = null;
			for(int key: map.keySet()) {
				if (map.get(key).getYoutubeId() == youtubeId) {
					vid = map.get(key);			
				}
			}
			return vid;
		}

		@Override
		public boolean updateViewCnt(String youtubeId) {
			// TODO Auto-generated method stub
			return false;
		}

		// youtubeId와 같은 id 가지는 리뷰 불러오기 
		@Override
		public List<Review> getReviewbyId(String youtubeId) {
			// reviewrepo와 소통해 youtubeId와 일치하는 리뷰 찾아서 가져오기 
			List<Review> list = repoReview.getReviewsbyId(youtubeId);
			
			return list;
		}
		
}
