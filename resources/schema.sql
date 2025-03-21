DROP DATABASE IF EXISTS ssafit;

CREATE DATABASE ssafit CHARACTER SET=utf8mb4;

USE ssafit;

DROP TABLE IF EXISTS video;

DROP TABLE IF EXISTS user;

DROP TABLE IF EXISTS review;

CREATE TABLE video (
    youtube_id VARCHAR(11) PRIMARY KEY,
    title VARCHAR(255) NOT NULL,
    channel_name VARCHAR(255) NOT NULL,
    fitpart_name VARCHAR(255) NOT NULL,
    view_count INT NOT NULL DEFAULT 0
) ENGINE = InnoDB DEFAULT CHARSET = utf8mb4;

CREATE TABLE user (
    user_id VARCHAR(255) PRIMARY KEY,
    PASSWORD VARCHAR(255) NOT NULL
) ENGINE = InnoDB DEFAULT CHARSET = utf8mb4;

CREATE TABLE review (
    review_id INT AUTO_INCREMENT PRIMARY KEY,
    youtube_id VARCHAR(11) NOT NULL,
    author_id VARCHAR(255) NOT NULL,
    title VARCHAR(255) NOT NULL,
    content TEXT NOT NULL,
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    view_count INT NOT NULL DEFAULT 0,
    FOREIGN KEY (youtube_id) REFERENCES video(youtube_id),
    FOREIGN KEY (author_id) REFERENCES user(user_id)
) ENGINE = InnoDB DEFAULT CHARSET = utf8mb4;

DROP TABLE IF EXISTS likedvideo;
DROP TABLE IF EXISTS following;

CREATE TABLE likedvideo (
    youtube_id VARCHAR(11) NOT NULL,
    user_id VARCHAR(255) NOT NULL,
    FOREIGN KEY (youtube_id) REFERENCES video(youtube_id),
    FOREIGN KEY (user_id) REFERENCES user(user_id)
) ENGINE = InnoDB DEFAULT CHARSET = utf8mb4;

CREATE TABLE following (
    follower_id VARCHAR(255) NOT NULL,
    followee_id VARCHAR(255) NOT NULL,
    FOREIGN KEY (follower_id) REFERENCES user(user_id),
    FOREIGN KEY (followee_id) REFERENCES user(user_id)
) ENGINE = InnoDB DEFAULT CHARSET = utf8mb4;