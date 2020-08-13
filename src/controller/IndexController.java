package controller;

import java.io.IOException;
import java.util.ArrayList;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import model.bean.Users;
import model.dao.UserDao;

public class IndexController extends HttpServlet {
	private static final long serialVersionUID = 1L;

	public IndexController() {
		super();
	}

	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		ArrayList<Users> listItems = new ArrayList<>();
		UserDao userDao = new UserDao();
		listItems = userDao.getItems();
		request.setAttribute("listUsers", listItems);
		request.getRequestDispatcher("/templates/index.jsp").forward(request, response);;
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
	}

}
