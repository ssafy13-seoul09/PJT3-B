package com.ssafy.user.controller;

import java.io.IOException;

import com.ssafy.user.model.service.UserService;
import com.ssafy.user.model.service.UserServiceImpl;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

@WebServlet("/user")
public class UserController extends HttpServlet {
    private static final long serialVersionUID = 1L;

    private static final int LOGIN_SESSION_TIMEOUT = 60 * 60;

    private UserService userService = UserServiceImpl.getInstance();

    protected void service(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String act = req.getParameter("act");

        switch (act) {
            case "loginform":
                doLoginform(req, resp);
                break;
            case "login":
                doLogin(req, resp);
                break;
            case "logout":
                doLogout(req, resp);
                break;
            case "registform":
                doRegistform(req, resp);
                break;
            case "regist":
                doRegist(req, resp);
                break;
            case "followlist":
                doFollowlist(req, resp);
                break;
            case "follow":
                doFollow(req, resp);
                break;
            case "unfollow":
                doUnfollow(req, resp);
                break;
            case "likedvideolist":
                doLikedVideoList(req, resp);
                break;
            case "likevideo":
                doLikeVideo(req, resp);
                break;
            case "unlikevideo":
                doUnlikeVideo(req, resp);
                break;
            case "mypage":
                req.getRequestDispatcher("/WEB-INF/user/mypage.jsp").forward(req, resp);
                break;
            default:
                break;
        }
    }

	private boolean checkLogin(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        if (req.getSession().getAttribute("loginUser") == null) {
            req.setAttribute("msg", "로그인이 필요합니다.");
            req.getRequestDispatcher("/WEB-INF/user/login.jsp").forward(req, resp);
            return false;
        }
        return true;
    }

    private void doLoginform(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        req.getRequestDispatcher("/WEB-INF/user/login.jsp").forward(req, resp);
    }

    private void doLogin(HttpServletRequest req, HttpServletResponse resp) throws IOException, ServletException {
        String id = req.getParameter("id");
        String password = req.getParameter("password");

        if (userService.authenticate(id, password)) {
            HttpSession session = req.getSession();
            session.setMaxInactiveInterval(LOGIN_SESSION_TIMEOUT);
            session.setAttribute("loginUser", id);
            resp.sendRedirect("index.jsp");
        } else {
            req.setAttribute("msg", "아이디 또는 비밀번호가 일치하지 않습니다.");
            req.getRequestDispatcher("/WEB-INF/user/login.jsp").forward(req, resp);
        }
    }

    private void doLogout(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        HttpSession session = req.getSession();
        session.invalidate();
        resp.sendRedirect("index.jsp");
    }

    private void doRegistform(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        req.getRequestDispatcher("/WEB-INF/user/register.jsp").forward(req, resp);
    }

    private void doRegist(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String id = req.getParameter("id");
        String password = req.getParameter("password");

        if (userService.userIdExists(id)) {
            req.setAttribute("msg", "이미 존재하는 아이디입니다.");
            req.getRequestDispatcher("/WEB-INF/user/register.jsp").forward(req, resp);
            return;
        }

        userService.register(id, password);
        req.setAttribute("msg", "회원가입이 완료되었습니다. 로그인해주세요.");
        req.getRequestDispatcher("/WEB-INF/user/login.jsp").forward(req, resp);
    }

    private void doFollowlist(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        if (!checkLogin(req, resp)) {
            return;
        }

        String userId = req.getSession().getAttribute("loginUser").toString();
        req.setAttribute("followlist", userService.getFollowings(userId));
        req.getRequestDispatcher("/WEB-INF/user/following.jsp").forward(req, resp);
    }

    // TODO: Follow/Unfollow Failure Handling
    private void doUnfollow(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        if (!checkLogin(req, resp)) {
            return;
        }

        String userId = req.getSession().getAttribute("loginUser").toString();
        String targetId = req.getParameter("id");
        userService.unfollow(userId, targetId);

        resp.sendRedirect("user?act=followlist");
    }

    private void doFollow(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        if (!checkLogin(req, resp)) {
            return;
        }

        String userId = req.getSession().getAttribute("loginUser").toString();
        String targetId = req.getParameter("id");
        userService.follow(userId, targetId);

        resp.sendRedirect("user?act=followlist");
    }

    // TODO: Consistent Naming
	private void doLikedVideoList(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        if (!checkLogin(req, resp)) {
            return;
        }

        String userId = req.getSession().getAttribute("loginUser").toString();
        req.setAttribute("likedvideolist", userService.getLikedVideos(userId));
        req.getRequestDispatcher("/WEB-INF/user/favorite.jsp").forward(req, resp);
	}

    // TODO: Like/Unlike Failure Handling
    private void doUnlikeVideo(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        if (!checkLogin(req, resp)) {
            return;
        }

        String userId = req.getSession().getAttribute("loginUser").toString();
        String videoId = req.getParameter("id");
        userService.unlikeVideo(userId, videoId);

        resp.sendRedirect("user?act=likedvideolist");
	}

	private void doLikeVideo(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        if (!checkLogin(req, resp)) {
            return;
        }

        String userId = req.getSession().getAttribute("loginUser").toString();
        String videoId = req.getParameter("id");
        userService.likeVideo(userId, videoId);
        
        

        resp.sendRedirect("user?act=likedvideolist");
	}
}
