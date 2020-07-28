package kr.co.codeplayground.util;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class DBUtil {
	private static Connection conn = null;

	public static Connection getConnection() {
		if(conn == null) {
			String url = "jdbc:oracle:thin:@localhost:1521:orcl";
			String dbId = "scott";
			String dbPw = "tiger";
			try {
				Class.forName("oracle.jdbc.driver.OracleDriver");
				conn = DriverManager.getConnection(url, dbId, dbPw);
			}
			catch (SQLException e1) {
				e1.printStackTrace();
			}
			catch(ClassNotFoundException e2) {
				System.out.println("데이터베이스를 찾을 수 없습니다.");
				e2.printStackTrace();
			}

		}
		return conn;
	}

	public static void close() {
		if(conn != null) {
			try {
				if(!conn.isClosed()) {
					conn.close();
				}
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
	}
}
