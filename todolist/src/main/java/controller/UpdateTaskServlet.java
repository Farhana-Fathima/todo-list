package controller;

import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

import db.DatabaseConnection;
import db.DatabaseConnection.DatabaseConnectionException;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

@WebServlet("/UpdateTask")
public class UpdateTaskServlet extends HttpServlet {
    protected void doPost(HttpServletRequest request, HttpServletResponse response) 
            throws ServletException, IOException {
        
        
        int taskId = Integer.parseInt(request.getParameter("taskId"));
        String title = request.getParameter("title");
        String description = request.getParameter("description");
        boolean completed = request.getParameter("completed") != null;
        
        try {
            
            Connection conn = DatabaseConnection.getConnection();
            String sql = "UPDATE tasks SET title = ?, description = ?, completed = ? WHERE id = ?";
            
            PreparedStatement pstmt = conn.prepareStatement(sql);
            pstmt.setString(1, title);
            pstmt.setString(2, description);
            pstmt.setBoolean(3, completed);
            pstmt.setInt(4, taskId);
            
            pstmt.executeUpdate();
            
           
            response.sendRedirect("todo-list.jsp");
        } catch (SQLException e) {
            e.printStackTrace();
            response.sendError(HttpServletResponse.SC_INTERNAL_SERVER_ERROR, 
                "Error updating task: " + e.getMessage());
        } catch (DatabaseConnectionException e) {
	
			e.printStackTrace();
		}
    }
}
