<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ page import ="com.ssafy.video.model.dto.Video" %>   
<%@ taglib prefix="c" uri="jakarta.tags.core"%>  

<html>
<head>
<meta charset="UTF-8">
<title>유저 테스트</title>
</head>
<body>
  <a href="user?act=loginform">로그인</a>
  <a href="user?act=logout">로그아웃</a>
  <a href="user?act=register">회원가입</a>
  <a href="user?act=mypage">마이페이지</a>
</body>
</html>
