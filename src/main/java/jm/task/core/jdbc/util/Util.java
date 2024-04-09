package jm.task.core.jdbc.util;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class Util {

      private static final String URL = "jdbc:mysql://localhost:3306/bd_jdbs";
      private static final String USER = "root";
      private static final String PASSWORD = "root";

      public static Connection connection() {

          Connection connection = null;
          try {
              connection = DriverManager.getConnection(URL, USER, PASSWORD);
          } catch (SQLException e) {
              throw new RuntimeException(e);
          }

          return connection;
      }
}