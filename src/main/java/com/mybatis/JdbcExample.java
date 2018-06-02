package com.mybatis;

import org.apache.log4j.Logger;

import com.mybatis.entity.User;
import com.mybatis.enums.TypeEnum;

import java.sql.*;

public class JdbcExample {
	/**
	 * 获取数据库连接
	 *
	 * @return
	 */
	private Connection getConnection() {

		Logger log = Logger.getLogger(JdbcExample.class);
		Connection connection = null;
		try {
			Class.forName("com.mysql.jdbc.Driver");
			String url = "jdbc:mysql://localhost:3306/mybatis-plus";
			String user = "root";
			String password = "";
			connection = DriverManager.getConnection(url, user, password);

		} catch (ClassNotFoundException | SQLException e) {
			return null;
		}
		return connection;
	}

	public User getUser(long id) {
		Connection connection = getConnection();
		PreparedStatement ps = null;
		ResultSet rs = null;
		try {
			ps = connection
					.prepareStatement("select id, name, age, type, ctime from sys_user where id=?");
			ps.setLong(1, id);
			rs = ps.executeQuery();
			while (rs.next()) {
				Integer userId = rs.getInt("id");
				String name = rs.getString("name");
				String age = rs.getString("age");
				String ctime = rs.getString("ctime");
				User user = new User();
				user.setAge(Integer.valueOf(age));
				user.setName(name);
				user.setCtime(new java.util.Date());
				user.setType(TypeEnum.DISABLED);
				user.setId(userId);

				return user;
			}
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			this.close(rs, ps, connection);
		}
		return null;
	}

	/**
	 * 关闭资源
	 *
	 * @param rs
	 * @param stmt
	 * @param conn
	 */
	private void close(ResultSet rs, Statement stmt, Connection conn) {
		try {
			if (rs != null && !rs.isClosed()) {
				rs.close();
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		try {
			if (stmt != null && !stmt.isClosed()) {
				stmt.close();
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		try {
			if (conn != null && !conn.isClosed()) {
				conn.close();
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}

	public static void main(String[] args) {
		JdbcExample jdbcExample = new JdbcExample();
		User user = jdbcExample.getUser(1L);
		System.out.println("user==>" + user);
	}
}
