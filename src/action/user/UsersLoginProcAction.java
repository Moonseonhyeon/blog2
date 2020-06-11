package action.user;

import java.io.IOException;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import action.Action;
import lombok.Builder;
import model.RoleType;
import model.Users;
import repository.UsersRepository;
import util.Script;

public class UsersLoginProcAction implements Action {

	@Override
	public void execute(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// 유효성 검사
		if (request.getParameter("username").equals("") ||
			request.getParameter("username") == null||
			request.getParameter("password").equals("") ||
			request.getParameter("password") == null
		) {
			return;
		}

		// 파라메터를 받기(x-www-form-urlencoded 라는 MIME타입/ key=value)
		String username = request.getParameter("username");
		String password = request.getParameter("password");

		// db연결 - UsersRepository의 save() 호출
		UsersRepository usersRepository = UsersRepository.getInstance();
		Users user = usersRepository.findByUsernameAndPassword(username, password);

		// index.jsp 페이지로 이동ㅎ
		if (user != null) {
			HttpSession session = request.getSession();
			session.setAttribute("principal", user);
			
			if(request.getParameter("remember") != null) {
				Cookie cookie = new Cookie("remember", user.getUsername());
				response.addCookie(cookie);
			}
			
			Script.href("로그인 성공", "/blog2/board?cmd=home", response);
		} else {
			Script.back("로그인 실패.", response);
		}
	}

}
