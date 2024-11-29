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
		
		