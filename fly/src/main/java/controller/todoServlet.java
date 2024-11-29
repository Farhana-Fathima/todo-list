package controller;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;

import io.github.cdimascio.dotenv.Dotenv;

/**
 * Servlet implementation class todoServlet
 */
//@WebServlet("/todolist")
//public class todoServlet extends HttpServlet {
//	private static final long serialVersionUID = 1L;
//       
//    /**
//     * @see HttpServlet#HttpServlet()
//     */
//    public todoServlet() {
//        super();
//        // TODO Auto-generated constructor stub
//    }
//
//	/**
//	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
//	 */
//	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
//		// TODO Auto-generated method stub
//		response.getWriter().append("Served at: ").append(request.getContextPath());
//	}
//
//	/**
//	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
//	 */
//	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
//		
//		Connection conn = null;
//		PreparedStatement preparedstatement = null;
//		PrintWriter out = response.getWriter();
//		
//		//retrieve form data
//		String title = request.getParameter("fname");
//		Boolean completed = request.getParameter();
//	    String usrname = request.getParameter("username");
//	    String passwrd= request.getParameter("password");
//	    
//	    try {
//	    	Dotenv dotenv = Dotenv.load();
//	        String dbUrl = dotenv.get("DATABASE_URL");
//	        String username = dotenv.get("DB_USERNAME");
//	    	
//	    	// INSERT STAFF DATA INTO THE DATABASE
//	    	String sql = "INSERT INTO todos (title, completed) VALUES (?,?)";
//	    	preparedstatement = conn.prepareStatement(sql);
//	    	preparedstatement.setString(1,fname);
//	    	preparedstatement.setString(2,lname);
//	    	preparedstatement.setString(3,usrname);
//	    	preparedstatement.setString(4,passwrd);
//	    	
//	    	
//	    	// execute the prepared statement
//	    	int rowsInserted = preparedstatement.executeUpdate();
//	    	if (rowsInserted > 0) {
//				response.sendRedirect("signup.jsp");
//			}
//		}
//	    catch (SQLException se) {
//	    	// handle errors for jdbc
//		     se.printStackTrace();
//		}
//	    catch (Exception e) {
//	    	// handle errors for class.forname
//			e.printStackTrace();
//		}
//	    finally {
//	    	try {
//				if (preparedstatement != null)
//				    preparedstatement.close();
//			}
//	    	catch (SQLException se2) {
//				
//			}
//	    	try {
//	    		if(conn != null)
//	    		   conn.close();	
//				
//			} 
//	    	catch (SQLException se) {
//				se.printStackTrace();
//			}
//	    }
//	}
//
//}
