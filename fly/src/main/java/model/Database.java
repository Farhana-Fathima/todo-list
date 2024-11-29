package model;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

import io.github.cdimascio.dotenv.Dotenv;

public class Database {

	public static void main(String[] args) throws ClassNotFoundException, SQLException {
		
	}
		
	   private static Connection connection;

		    static {
		        try {
		            Dotenv dotenv = Dotenv.load();
		            String url = dotenv.get("DB_URL");
		            String user = dotenv.get("DB_USER");
		            String password = dotenv.get("DB_PASSWORD");
		            connection = DriverManager.getConnection(url, user, password);
		            
		            System.out.println("DB_URL: " + url);
		            System.out.println("DB_USER: " + user);
		            System.out.println("DB_PASSWORD: " + password);
		        }
		        catch (Exception e) {
		            e.printStackTrace();
		        }
		    }

		    public static Connection getConnection() {
		        return connection;
		    }
		    
} 
		
		// creating database
//		String sql = "create database todo";
//		
//		stmt.executeUpdate(sql);
//		
//		System.out.println("Database created successfully");
		
		// table creation
//		String sql = "create table todolist"
//		        + "(id Integer not null PRIMARY KEY AUTO_INCREMENT,"
//		        +"title varchar(15) not null,"
//		        + "completed BOOLEAN DEFAULT FALSE)";
//		
//		stmt.executeUpdate(sql);
//		System.out.println("Table created successfully");
		
		//updation of a record
//		String sql = "update camlist set cam_salary = '48000' where cam_id = '1' ";
//		
//		stm.executeUpdate(sql);
//		
//		System.out.println("one row affected");
		
//		deletion
//		String sql = "delete from camlist where cam_id = '3' ";
//		
//      stm.executeUpdate(sql);
//        
//      System.out.println("deleted");
		
		
//		con.close();
		

	

//}
