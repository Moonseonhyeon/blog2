package action.user;

import java.io.IOException;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import action.Action;
import lombok.Builder;
import model.RoleType;
import model.Users;
import repository.UsersRepository;
import util.SHA256;
import util.Script;

public class UsersJoinProcAction implements Action {

	@Override
	public void execute(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// 유효성 검사
		if (request.getParameter("username").equals("") ||
				request.getParameter("username") == null||
				request.getParameter("password").equals("") ||
				request.getParameter("password") == null ||
				request.getParameter("email").equals("") ||
				request.getParameter("email") == null ||
				request.getParameter("address").equals("") ||
				request.getParameter("address") == null

		) {
			return;
		}

		// 파라메터를 받기(x-www-form-urlencoded 라는 MIME타입/ key=value)
		String username = request.getParameter("username");
		String rawPassword = request.getParameter("password");
		String password = SHA256.encodeSha256(rawPassword);
		String email = request.getParameter("email");
		String address = request.getParameter("address");
		String userRole = RoleType.USER.toString();

		// user 오브젝트 변환
		Users user = Users.builder()
				.username(username)
				.password(password)
				.email(email)
				.address(address)
				.userRole(userRole)
				.build();

		// db연결 - UsersRepository의 save() 호출
		UsersRepository userRepository = UsersRepository.getInstance();
		int result = userRepository.save(user);

		// index.jsp 페이지로 이동ㅎ
		if (result == 1) {
			Script.href("회원가입에 성공하였습니다.", "/blog2/user?cmd=login", response);
		} else {
			Script.back("회원가입에 실패하였습니다.", response);
		}
	}

}
