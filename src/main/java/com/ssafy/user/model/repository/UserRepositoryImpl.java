package com.ssafy.user.model.repository;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import com.ssafy.user.model.dto.User;
import com.ssafy.util.DBUtil;

public class UserRepositoryImpl implements UserRepository {

    private final DBUtil dbUtil = DBUtil.getInstance();

    private static final UserRepositoryImpl INSTANCE = new UserRepositoryImpl();

    private UserRepositoryImpl() {
    }

    public static UserRepositoryImpl getInstance() {
        return INSTANCE;
    }

    @Override
    public User select(String userId) {
        String sql = "SELECT * FROM user WHERE user_id = ?;";
        User result = null;

        Connection conn = null;
        PreparedStatement pstmt = null;
        ResultSet rs = null;

        try {
            conn = dbUtil.getConnection();
            pstmt = conn.prepareStatement(sql);

            pstmt.setString(1, userId);
            rs = pstmt.executeQuery();

            if (rs.next()) {
                result = new User(rs.getString("user_id"), rs.getString("password"));
            }

        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            dbUtil.close(rs, pstmt, conn);
        }

        return result;
    }

    @Override
    public boolean insertUser(User user) {
        String sql = "INSERT INTO user (user_id, password) VALUES (?, ?);";

        Connection conn = null;
        PreparedStatement pstmt = null;
        boolean result = false;

        try {
            conn = dbUtil.getConnection();
            pstmt = conn.prepareStatement(sql);

            pstmt.setString(1, user.getUserId());
            pstmt.setString(2, user.getPassword());

            result = pstmt.executeUpdate() > 0;

        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            dbUtil.close(pstmt, conn);
        }

        return result;
    }

    @Override
    public boolean updateUser(User user) {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'updateUser'");
    }

    @Override
    public boolean deleteUser(String userId) {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'deleteUser'");
    }

    @Override
    public boolean checkUser(String userId) {
        return select(userId) != null;
    }

    @Override
    public List<String> getFollowings(String userId) {
        String sql = "SELECT * FROM following WHERE follower_id = ?;";
        List<String> result = new ArrayList<>();

        Connection conn = null;
        PreparedStatement pstmt = null;
        ResultSet rs = null;

        try {
            conn = dbUtil.getConnection();
            pstmt = conn.prepareStatement(sql);

            pstmt.setString(1, userId);
            rs = pstmt.executeQuery();

            while (rs.next()) {
                result.add(rs.getString("followee_id"));
            }

        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            dbUtil.close(rs, pstmt, conn);
        }

        return result;
    }

    @Override
    public boolean addFollowing(String userId, String targetId) {
        String sql = "INSERT INTO following (follower_id, followee_id) VALUES (?, ?);";

        Connection conn = null;
        PreparedStatement pstmt = null;
        boolean result = false;

        try {
            conn = dbUtil.getConnection();
            pstmt = conn.prepareStatement(sql);

            pstmt.setString(1, userId);
            pstmt.setString(2, targetId);

            result = pstmt.executeUpdate() > 0;

        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            dbUtil.close(pstmt, conn);
        }

        return result;
    }

    @Override
    public boolean removeFollowing(String userId, String targetId) {
        String sql = "DELETE FROM following WHERE follower_id = ? AND followee_id = ?;";

        Connection conn = null;
        PreparedStatement pstmt = null;
        boolean result = false;

        try {
            conn = dbUtil.getConnection();
            pstmt = conn.prepareStatement(sql);

            pstmt.setString(1, userId);
            pstmt.setString(2, targetId);

            result = pstmt.executeUpdate() > 0;

        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            dbUtil.close(pstmt, conn);
        }

        return result;
    }

    @Override
    public List<String> getLikedVideos(String userId) {
        String sql = "SELECT * FROM likedvideo WHERE user_id = ?;";
        List<String> result = new ArrayList<>();

        Connection conn = null;
        PreparedStatement pstmt = null;
        ResultSet rs = null;

        try {
            conn = dbUtil.getConnection();
            pstmt = conn.prepareStatement(sql);

            pstmt.setString(1, userId);
            rs = pstmt.executeQuery();

            while (rs.next()) {
                result.add(rs.getString("youtube_id"));
            }

        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            dbUtil.close(rs, pstmt, conn);
        }

        return result;
    }

    @Override
    public boolean addLikedVideo(String userId, String videoId) {
        String sql = "INSERT INTO likedvideo (user_id, youtube_id) VALUES (?, ?);";

        Connection conn = null;
        PreparedStatement pstmt = null;
        boolean result = false;

        try {
            conn = dbUtil.getConnection();
            pstmt = conn.prepareStatement(sql);

            pstmt.setString(1, userId);
            pstmt.setString(2, videoId);

            result = pstmt.executeUpdate() > 0;

        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            dbUtil.close(pstmt, conn);
        }

        return result;
    }

    @Override
    public boolean removeLikedVideo(String userId, String videoId) {
        String sql = "DELETE FROM likedvideo WHERE user_id = ? AND youtube_id = ?;";

        Connection conn = null;
        PreparedStatement pstmt = null;
        boolean result = false;

        try {
            conn = dbUtil.getConnection();
            pstmt = conn.prepareStatement(sql);

            pstmt.setString(1, userId);
            pstmt.setString(2, videoId);

            result = pstmt.executeUpdate() > 0;

        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            dbUtil.close(pstmt, conn);
        }

        return result;
    }

}
