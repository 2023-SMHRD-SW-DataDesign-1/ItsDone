package view;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class DAO {
   
   public static void main(String[] args) {
      getConn();
   }

   

   public static void getConn() {

      try {
         Class.forName("oracle.jdbc.driver.OracleDriver");
         String dburl = "jdbc:oracle:thin:@project-db-stu.smhrd.com:1524:xe";
         String dbuser = "campus_g_0530_5";
         String dbpw = "smhrd5";
         
         Connection conn = DriverManager.getConnection(dburl, dbuser, dbpw);
         
         if (conn == null)
            System.out.println("접속실패");
         else
            System.out.println("접속성공");
         
      }catch (ClassNotFoundException e) {
         e.printStackTrace();

      } catch (SQLException e) {
   
         e.printStackTrace();
      }


   
   }
}