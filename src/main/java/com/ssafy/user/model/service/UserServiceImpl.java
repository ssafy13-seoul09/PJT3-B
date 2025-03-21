package com.ssafy.user.model.service;

import java.util.List;

import com.ssafy.user.model.dto.User;
import com.ssafy.user.model.repository.UserRepositoryImpl;
import com.ssafy.user.model.repository.UserRepository;

public class UserServiceImpl implements UserService {

    private static final UserService INSTANCE = new UserServiceImpl();
    private final UserRepository repo = UserRepositoryImpl.getInstance();

    private UserServiceImpl() {
    }

    public static UserService getInstance() {
        return INSTANCE;
    }

    @Override
    public boolean userIdExists(String userId) {
        return repo.checkUser(userId);
    }

    @Override
    public boolean authenticate(String userId, String password) {
        User user = repo.select(userId);
        if (user == null) {
            return false;
        }

        return user.getPassword().equals(password);
    }

    @Override
    public boolean register(String userId, String password) {
        if (userIdExists(userId)) {
            return false;
        }

        return repo.insertUser(new User(userId, password));
    }

    @Override
    public User select(String userId) {
        return repo.select(userId);
    }

    @Override
    public List<String> getFollowings(String userId) {
        return repo.getFollowings(userId);
    }

    @Override
    public boolean follow(String userId, String targetId) {
        return repo.addFollowing(userId, targetId);
    }

    @Override
    public boolean unfollow(String userId, String targetId) {
        return repo.removeFollowing(userId, targetId);
    }

    @Override
    public List<String> getLikedVideos(String userId) {
        return repo.getLikedVideos(userId);
    }

    @Override
    public boolean likeVideo(String userId, String videoId) {
        return repo.addLikedVideo(userId, videoId);
    }

    @Override
    public boolean unlikeVideo(String userId, String videoId) {
        return repo.removeLikedVideo(userId, videoId);
    }
}
