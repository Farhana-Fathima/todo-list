package controller;

import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.time.LocalDate;

import db.DatabaseConnection;
import db.DatabaseConnection.DatabaseConnectionException;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import model.Task;

@WebServlet("/AddTask")
public class AddTaskServlet extends HttpServlet {
    protected void doPost(HttpServletRequest request, HttpServletResponse response) 
            throws ServletException, IOException {
        
        
        String title = request.getParameter("title");
        String description = request.getParameter("description");
        String taskDateStr = request.getParameter("taskDate");
        
        try {
         
            LocalDate taskDate = LocalDate.parse(taskDateStr);
            
          
            Task task = new Task(title, description, taskDate);
            
         
            Connection conn = DatabaseConnection.getConnection();
            String sql = "INSERT INTO tasks (title, description, task_date, completed) VALUES (?, ?, ?, ?)";
            
            PreparedStatement pstmt = conn.prepareStatement(sql);
            pstmt.setString(1, task.getTitle());
            pstmt.setString(2, task.getDescription());
            pstmt.setDate(3, java.sql.Date.valueOf(task.getTaskDate()));
            pstmt.setBoolean(4, task.isCompleted());
            
            pstmt.executeUpdate();
            
           
            response.sendRedirect("todo-list.jsp");
        } catch (SQLException e) {
            e.printStackTrace();
            response.sendError(HttpServletResponse.SC_INTERNAL_SERVER_ERROR, 
                "Error adding task: " + e.getMessage());
        } catch (DatabaseConnectionException e) {
	
			e.printStackTrace();
		}
    }
}
