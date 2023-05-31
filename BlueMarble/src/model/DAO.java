package model;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Scanner;

public class DAO {

	static Connection conn = null;
	static PreparedStatement psmt = null;
	static ResultSet rs = null;

	public static void main(String[] args) {
		// 테스트
		Scanner sc = new Scanner(System.in);

		System.out.println("----랭킹-----");
		System.out.print("아이디 : ");
		String Id = sc.next();
		System.out.print("Score  : ");
		int Score = sc.nextInt();
		System.out.print("Playtime : ");
		int Playtime = sc.nextInt();
		System.out.print("Ranking : ");
		int Ranking = sc.nextInt();

		RankingDTO ran = new RankingDTO(Id, Score, Playtime, Ranking);

		insertRanking(ran);
		
		
	}

	public static void getConn() {

		try {
			Class.forName("oracle.jdbc.driver.OracleDriver");
			String dburl = "jdbc:oracle:thin:@project-db-stu.smhrd.com:1524:xe";
			String dbuser = "campus_g_0530_5";
			String dbpw = "smhrd5";

			conn = DriverManager.getConnection(dburl, dbuser, dbpw);

			if (conn == null)
				System.out.println("접속실패");
			else
				System.out.println("접속성공");

		} catch (ClassNotFoundException e) {
			e.printStackTrace();

		} catch (SQLException e) {

			e.printStackTrace();
		}

	}

	public static void getClose() {

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

	public static void selectCity() {
		getConn();
		try {
			String sql = "select CITY from CITY order by 정렬";
			
			psmt = conn.prepareStatement(sql);

			rs = psmt.executeQuery();
			
		} catch (Exception e) {
			e.printStackTrace();
		}finally {
			getClose();
		}
	}

	public static void selectRanking() {
		getConn();
		try {
			String sql = "select * from RANKING ";

			psmt = conn.prepareStatement(sql);

			rs = psmt.executeQuery();

		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			getClose();
		}
	}

	public static void insertRanking(RankingDTO dto) {

		getConn();

		String sql = "insert into RANKING values(?,?,?,?)";

		int cnt = 0;

		try {
			psmt = conn.prepareStatement(sql);

			psmt.setInt(1, dto.getRanking());
			psmt.setString(2, dto.getId());
			psmt.setInt(3, dto.getScore());
			psmt.setInt(4, dto.getPlaytime());

			cnt = psmt.executeUpdate();

			if (cnt > 0) {

				System.out.println("insert성공!");
			} else {
				System.out.println("insert실패!");

			}
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			getClose();
		}

	}

}