package controller;

import java.io.File;
import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.annotation.MultipartConfig;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import javax.servlet.http.Part;

import model.bean.Users;
import model.dao.UserDao;

@MultipartConfig
public class UpdateController extends HttpServlet {
	private static final long serialVersionUID = 1L;

	public UpdateController() {
		super();
	}

	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		request.getRequestDispatcher("/templates/capnhat.jsp").forward(request, response);
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		HttpSession session = request.getSession();
		request.setCharacterEncoding("UTF-8");
		response.setCharacterEncoding("UTF-8");
		response.setContentType("text/html");
		String username = request.getParameter("username");
		String fullname = request.getParameter("fullname");

		Part filePart = request.getPart("avatar");
		String fileName = filePart.getSubmittedFileName();
		String appPath = request.getServletContext().getRealPath("");
		String dirPath = appPath + "templates/avatar";
		File saveDir = new File(dirPath);
		if (!saveDir.exists()) {
			saveDir.mkdir();
		}

		UserDao userDao = new UserDao();
		Users userEdit = (Users) session.getAttribute("user");
		int id = userEdit.getId();
		String avatarName = userEdit.getAvatar();
		// Lấy ảnh cũ
		String oldAvatar = dirPath + File.separator + avatarName;
		File oldFile = new File(oldAvatar);

		Users user = new Users();
		if (!"".equals(fileName)) {

			// Đổi tên file ảnh
			String portal = fileName.split("\\.")[0];
			String extra = fileName.split("\\.")[1];
			long time = System.currentTimeMillis();
			fileName = portal + "_" + time + "." + extra;

			user = new Users(id, username, "", fileName, fullname);
		} else {
			user = new Users(id, username, "", avatarName, fullname);
		}
		if (userDao.editItem(user) > 0) {
			// Nếu có chọn ảnh thì xóa ảnh cũ và upload ảnh mới
			if (!"".equals(fileName)) {
				oldFile.delete();
				// Upload ảnh mới
				String filePath = dirPath + File.separator + fileName;
				filePart.write(filePath);
			}
			session.setAttribute("user", user);
			response.sendRedirect(request.getContextPath()+ "/index?msg=1");
			return;
		} else {
			request.setAttribute("err", "Xảy ra lỗi trong quá trình xử lý!");
			request.getRequestDispatcher("/templates/capnhat.jsp").forward(request, response);
			return;
		}
	}

}
