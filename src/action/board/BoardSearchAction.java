package action.board;

import java.io.IOException;
import java.util.List;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import action.Action;
import model.Board;
import repository.BoardRepository;
import util.HtmlParser;
import util.Script;

public class BoardSearchAction implements Action {

	@Override
	public void execute(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

		if (request.getParameter("keyword") == null || request.getParameter("keyword").equals("")) {
			Script.back("검색 키워드가 없습니다.", response);
			return;
		}

		int page = Integer.parseInt(request.getParameter("page"));
		String keyword = request.getParameter("keyword");

		// 1. DB연결해서 Board 목록 다 불러와서
		BoardRepository boardRepository = BoardRepository.getInstance();

		// 2. 3건만 페이징하여 가져오기
		List<Board> boards = boardRepository.findAll(page, keyword);

		// 본문 짧게 가공하기
		for (Board board : boards) {
			String preview = HtmlParser.getContentPreview(board.getContent());
			board.setContent(preview);
		}

		request.setAttribute("boards", boards);

		// 마지막페이지 확인 로직
		int count = boardRepository.count(keyword);
		int lastPage = (count - 1) / 3;

		// 현재 페이지가 전체의 몇 프로인지
		double currentPercent = (double) (page) / (lastPage) * 100;

		request.setAttribute("lastPage", lastPage);
		request.setAttribute("currentPercent", currentPercent);
		RequestDispatcher dis = request.getRequestDispatcher("home.jsp");
		dis.forward(request, response);
	}
}
