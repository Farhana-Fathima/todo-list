package model;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

import io.github.cdimascio.dotenv.Dotenv;

public class TodoDAO {
	
//public static void main(String[] args) {
//	Dotenv dotenv = Dotenv.load();
//
//    String url = dotenv.get("DB_URL");
//    String user = dotenv.get("DB_USER");
//    String password = dotenv.get("DB_PASSWORD");
//
//    System.out.println("DB_URL: " + url);
//    System.out.println("DB_USER: " + user);
//    System.out.println("DB_PASSWORD: " + password);
//}

    public void create(Todo todo) throws SQLException {
    	String query = "INSERT INTO todos (title, completed) VALUES (?, ?)";
    	try (PreparedStatement stmt = Database.getConnection().prepareStatement(query)) {
    	    stmt.setString(1, todo.getTitle());
    	    stmt.setBoolean(2, todo.isCompleted());
    	    stmt.executeUpdate();
    	}

    }

    public List<Todo> readAll() throws SQLException {
        String query = "SELECT * FROM todos";
        List<Todo> todos = new ArrayList<>();
        try (Statement stmt = Database.getConnection().createStatement();
             ResultSet rs = stmt.executeQuery(query)) {
            while (rs.next()) {
                todos.add(new Todo(
                    rs.getInt("id"),
                    rs.getString("title"),
                    rs.getBoolean("completed")
                ));
            }
        }
        return todos;
    }

    public void update(Todo todo) throws SQLException {
    	String query = "UPDATE todos SET title = ?, completed = ? WHERE id = ?";
    	try (PreparedStatement stmt = Database.getConnection().prepareStatement(query)) {
    	    stmt.setString(1, todo.getTitle());
    	    stmt.setBoolean(2, todo.isCompleted()); 
    	    stmt.setInt(3, todo.getId());
    	    stmt.executeUpdate();
    	}

    }

    public void delete(int id) throws SQLException {
        String query = "DELETE FROM todos WHERE id = ?";
        try (PreparedStatement stmt = Database.getConnection().prepareStatement(query)) {
            stmt.setInt(1, id);
            stmt.executeUpdate();
        }
    }
}

