package com.example;

import java.sql.*;

public class DBConnectionTest {
    public static void main(String[] args) throws Exception {
        Connection c = null;

        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
        } catch (ClassNotFoundException e) {
            System.err.println("JDBC 드라이버를 로드하는데 문제 발생" + e.getMessage());
            e.printStackTrace();
        }

        try {
            c = DriverManager.getConnection("jdbc:mysql://localhost:3305", "root", "8699");
            System.out.println("연결 완료!!!");

            Statement stmt  = c.createStatement();
            String query = "SELECT now()"; // 시스템의 현재 날짜시간을 출력하는 쿼리(query)
            ResultSet rs = stmt.executeQuery(query); // query를 실행한 결과를 rs에 담는다.

            // 실행결과가 담긴 rs에서 한 줄씩 읽어서 출력
            while (rs.next()) {
                String curDate = rs.getString(1);  // 읽어온 행의 첫번째 컬럼의 값을 String으로 읽어서 curDate에 저장
                System.out.println(curDate);       // 2022-01-11 13:53:00.0
            }

        } catch (SQLException e) {
            System.out.println("연결 오류" + e.getMessage());
            e.printStackTrace();
        }

        try {
            if(c != null) {
                c.close();
            }
        } catch (SQLException e) {}
    } // main()
}