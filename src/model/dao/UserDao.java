package model.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;

import model.bean.Users;
import util.DBConnectionUtil;

public class UserDao {
	private static Connection conn;
	private static Statement st;
	private static PreparedStatement pst;
	private static ResultSet rs;

	public ArrayList<Users> getItems() {
		ArrayList<Users> listItems = new ArrayList<>();
		conn = DBConnectionUtil.getConnection();
		try {
			String sql = "SELECT * FROM users";
			st = conn.createStatement();
			rs = st.executeQuery(sql);
			while (rs.next()) {
				Users user = new Users(rs.getInt("id"), rs.getString("email"), rs.getString("password"),
						rs.getString("avatar"), rs.getString("fullname"));
				listItems.add(user);
			}

		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			DBConnectionUtil.close(rs, st, conn);
		}
		return listItems;
	}

	public Users getItem(int id) {
		Users item = null;
		conn = DBConnectionUtil.getConnection();
		try {
			String sql = "SELECT * FROM users WHERE id = ?";
			pst = conn.prepareStatement(sql);
			pst.setInt(1, id);
			rs = pst.executeQuery();
			if (rs.next()) {
				item = new Users(rs.getInt("id"), rs.getString("email"), rs.getString("password"),
						rs.getString("avatar"), rs.getString("fullname"));
			}

		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			DBConnectionUtil.close(rs, pst, conn);
		}
		return item;
	}

	public int editItem(Users user) {
		int result = 0;
		conn = DBConnectionUtil.getConnection();
		String sql = "UPDATE users SET email = ?, fullname = ?, avatar = ? WHERE id = ?";
		try {
			pst = conn.prepareStatement(sql);
			pst.setString(1, user.getEmail());
			pst.setString(2, user.getFullName());
			pst.setString(3, user.getAvatar());
			pst.setInt(4, user.getId());
			result = pst.executeUpdate();
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			DBConnectionUtil.close(pst, conn);
		}
		return result;
	}

	public Users getItem(String username, String password) {
		conn = DBConnectionUtil.getConnection();
		String sql = "SELECT * FROM users WHERE email = ? AND password = ?";
		Users item = null;
		try {
			pst = conn.prepareStatement(sql);
			pst.setString(1, username);
			pst.setString(2, password);
			rs = pst.executeQuery();
			if (rs.next()) {
				item = new Users(rs.getInt("id"), rs.getString("email"), rs.getString("password"),
						rs.getString("avatar"), rs.getString("fullname"));
			}
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			DBConnectionUtil.close(rs, pst, conn);
		}
		return item;
	}
}
