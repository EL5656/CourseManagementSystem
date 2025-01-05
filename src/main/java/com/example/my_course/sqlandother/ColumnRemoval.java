package com.example.my_course.sqlandother;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.Statement;
import java.sql.SQLException;

public class ColumnRemoval {

    public static void main(String[] args) {
        String jdbcUrl = "jdbc:mysql://localhost:3306/my_course";
        String username = "root";
        String password = "12345";

        // SQL query to remove 'user_type' column
        String sql = "ALTER TABLE user DROP COLUMN user_type";

        try (Connection connection = DriverManager.getConnection(jdbcUrl, username, password);
             Statement statement = connection.createStatement()) {

            // Execute the ALTER TABLE query
            int rowsAffected = statement.executeUpdate(sql);
            if (rowsAffected >= 0) {
                System.out.println("Successfully dropped the 'user_type' column.");
            }

        } catch (SQLException e) {
            System.out.println("Error during SQL execution: " + e.getMessage());
        }
    }
}

