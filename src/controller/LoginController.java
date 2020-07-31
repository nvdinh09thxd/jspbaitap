package controller;

import java.io.IOException;
import java.util.ArrayList;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import model.bean.Users;
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
			ArrayList<Users> listUsers = new ArrayList<>();
			UserDao userDao = new UserDao();
			listUsers = userDao.getItems();
			request.setAttribute("listUsers", listUsers);
			request.getRequestDispatcher("/templates/index.jsp").forward(request, response);
			return;
		}
		RequestDispatcher rd = request.getRequestDispatcher("/templates/login.jsp");
		rd.forward(request, response);
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
			RequestDispatcher rd = request.getRequestDispatcher("/templates/login.jsp");
			rd.forward(request, response);
		} else {
			ArrayList<Users> listItems = new ArrayList<>();
			UserDao userDao = new UserDao();
			listItems = userDao.getItems();
			for (Users user : listItems) {
				if (user.getEmail().equals(email) && user.getPassword().equals(password)) {
					session.setAttribute("user", user);
					request.setAttribute("listUsers", listItems);
					request.getRequestDispatcher("/templates/index.jsp").forward(request, response);
					return;
				}
			}
			request.setAttribute("err", err2);
			RequestDispatcher rd = request.getRequestDispatcher("/templates/login.jsp");
			rd.forward(request, response);
		}
	}
}