package model;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

public class DAO {

	private Connection conn = null;
	private PreparedStatement psmt = null;
	private ResultSet rs = null;

	public void getConn() {

		try {
			Class.forName("oracle.jdbc.driver.OracleDriver");
			String dburl = "jdbc:oracle:thin:@project-db-stu.smhrd.com:1524:xe";
			String dbuser = "campus_g_0530_5";
			String dbpw = "smhrd5";

			conn = DriverManager.getConnection(dburl, dbuser, dbpw);

//			if (conn == null)
//				System.out.println("접속실패");
//			else
//				System.out.println("접속성공");

		} catch (ClassNotFoundException e) {
			e.printStackTrace();

		} catch (SQLException e) {

			e.printStackTrace();
		}

	}

	public void getClose() {

		try {
			if (rs != null)
				rs.close();
			if (psmt != null)
				psmt.close();
			if (conn != null)
				conn.close();
		} catch (SQLException e) {
			e.printStackTrace();
		}

	}

	public ArrayList<CityDTO> selectCity() {

		ArrayList<CityDTO> cityList = new ArrayList<>();

		getConn();

		try {
			String sql = "SELECT * FROM CITY ORDER BY 정렬";

			psmt = conn.prepareStatement(sql);

			rs = psmt.executeQuery();

			while (rs.next()) {
				cityList.add(new CityDTO(rs.getString(1), rs.getInt(3)));
			}

		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			getClose();
		}

		return cityList;
	}

	public ArrayList<PlayerDTO> selectRanking() {

		ArrayList<PlayerDTO> rankList = new ArrayList<>();

		getConn();

		try {
			String sql = "SELECT * FROM RANKING ORDER BY SCORE DESC";

			psmt = conn.prepareStatement(sql);

			rs = psmt.executeQuery();

			while (rs.next()) {
				rankList.add(new PlayerDTO(rs.getString(2), rs.getInt(3)));
			}

		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			getClose();
		}

		return rankList;
	}

	public int insertRanking(String name, int money) {

		getConn();

		String sql = "INSERT INTO RANKING(PLAYER_ID, SCORE) VALUES(?,?)";

		int cnt = 0;

		try {
			psmt = conn.prepareStatement(sql);

			psmt.setString(1, name);
			psmt.setInt(2, money);

			cnt = psmt.executeUpdate();

		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			getClose();
		}

		return cnt;

	}

}