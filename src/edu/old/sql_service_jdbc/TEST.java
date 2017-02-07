package edu.old.sql_service_jdbc;

import java.sql.*;

/**
 * Created by Dmitry on 31.01.2017.
 */
public class TEST {
    private static final String url = "jdbc:mysql://localhost:3306/hospital?autoReconnect=true&verifyServerCertificate=false&useSSL=true";
    private static final String user = "root";
    private static final String password = "";

        public static void main(String[] args) {
            try {

                Connection conn = DriverManager.getConnection(url, user, password);

                if (conn == null) {
                    System.out.println("no connection");
                    System.exit(0);
                }

                Statement stmt = conn.createStatement();
                ResultSet rs = stmt.executeQuery("SELECT * FROM users");

                while (rs.next()) {
                    System.out.println(rs.getRow() + ". " + rs.getString("firstname")
                            + "\t" + rs.getString("lastname"));
                }

                /**
                 * stmt.close();
                 * При закрытии Statement автоматически закрываются
                 * все связанные с ним открытые объекты ResultSet
                 */
                stmt.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }

}
