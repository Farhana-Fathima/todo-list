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

@WebServlet("/DeleteTask")
public class DeleteTaskServlet extends HttpServlet {
    protected void doPost(HttpServletRequest request, HttpServletResponse response) 
            throws ServletException, IOException {
        
       
        int taskId = Integer.parseInt(request.getParameter("taskId"));
        
        try {
          
            Connection conn = DatabaseConnection.getConnection();
            String sql = "DELETE FROM tasks WHERE id = ?";
            
            PreparedStatement pstmt = conn.prepareStatement(sql);
            pstmt.setInt(1, taskId);
            
            pstmt.executeUpdate();
            
            
            response.sendRedirect("todo-list.jsp");
        } catch (SQLException e) {
            e.printStackTrace();
            response.sendError(HttpServletResponse.SC_INTERNAL_SERVER_ERROR, 
                "Error deleting task: " + e.getMessage());
        } catch (DatabaseConnectionException e) {
	
			e.printStackTrace();
		}
    }
}
