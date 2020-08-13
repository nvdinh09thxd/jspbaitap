package controller;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import model.dao.UserDao;

public class LoginController extends HttpServlet {
	private static final long serialVersionUID = 1L;

	public LoginController() {
		super();
	}

	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		HttpSession session = request.getSession();
		if (session.getAttribute("user") != null) {
			response.sendRedirect(request.getContextPath()+ "/index");
			return;
		}
		request.getRequestDispatcher("/templates/login.jsp").forward(request, response);
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		HttpSession session = request.getSession();
		String err1 = "Bạn vui lòng nhập Email / Password";
		String err2 = "Sai tên đăng nhập hoặc mật khẩu";
		String email = request.getParameter("email");
		String password = request.getParameter("password");

		request.setAttribute("email", email);

		if ("".equals(email) || "".equals(password)) {
			request.setAttribute("err", err1);
			request.getRequestDispatcher("/templates/login.jsp").forward(request, response);
		} else {
			UserDao userDao = new UserDao();
			if (userDao.getItem(email, password) != null) {
				session.setAttribute("user", userDao.getItem(email, password));
				response.sendRedirect(request.getContextPath() + "/index");
				return;
			}
			request.setAttribute("err", err2);
			request.getRequestDispatcher("/templates/login.jsp").forward(request, response);
		}
	}
}