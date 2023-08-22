package com.data;

import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;


@WebServlet("/signup")
public class program1 extends HttpServlet {
	private static final long serialVersionUID = 1L;
	
	private String url = "jdbc:mysql://localhost:3306/tap_academy";
    private String username = "root";
    private String password = "ravi1717";

    private Connection connection;

    public void init() throws ServletException {
        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
            connection = DriverManager.getConnection(url, username, password);
        } catch (ClassNotFoundException | SQLException e) {
            e.printStackTrace();
        }
    }

    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        response.setContentType("text/html");
        PrintWriter writer = response.getWriter();

        int id = Integer.parseInt(request.getParameter("id"));
        String firstname = request.getParameter("username");
        String lastname = request.getParameter("lname");
        String email = request.getParameter("email");
        String password = request.getParameter("password");
        int contact = Integer.parseInt(request.getParameter("contact"));

        String query = "INSERT INTO student (id, firstname, lastname, email, password, contact) VALUES (?, ?, ?, ?, ?, ?)";
        try (PreparedStatement pstmt = connection.prepareStatement(query)) {
            pstmt.setInt(1, id);
            pstmt.setString(2, firstname);
            pstmt.setString(3, lastname);
            pstmt.setString(4, email);
            pstmt.setString(5, password);
            pstmt.setInt(6, contact);

            int rowsAffected = pstmt.executeUpdate();
            
            if (rowsAffected > 0) {
                writer.println("Data inserted successfully");
            } else {
                writer.println("Failed to insert data");
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void destroy() {
        try {
            if (connection != null) {
                connection.close();
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
		
	}


}
