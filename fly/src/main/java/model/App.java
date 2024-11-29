package model;
	
	import java.util.List;
	import java.util.Scanner;

	public class App {
	    public static void main(String[] args) {
	        TodoDAO todoDAO = new TodoDAO();
	        Scanner scanner = new Scanner(System.in);

	        while (true) {
	            System.out.println("1. Create Todo");
	            System.out.println("2. View Todos");
	            System.out.println("3. Update Todo");
	            System.out.println("4. Delete Todo");
	            System.out.println("5. Exit");
	            System.out.print("Choose an option: ");
	            int choice = scanner.nextInt();

	            try {
	                switch (choice) {
	                    case 1 -> {
	                        scanner.nextLine();  
	                        System.out.print("Enter title: ");
	                        String title = scanner.nextLine();
	                        todoDAO.create(new Todo(0, title, false));
	                        System.out.println("Todo created!");
	                    }
	                    case 2 -> {
	                        List<Todo> todos = todoDAO.readAll();
	                        todos.forEach(todo -> System.out.println(todo.getId() + ": " + todo.getTitle()));
	                    }
	                    case 3 -> {
	                        System.out.print("Enter Todo ID to update: ");
	                        int id = scanner.nextInt();
	                        scanner.nextLine();  
	                        System.out.print("Enter new title: ");
	                        String title = scanner.nextLine();
	                        System.out.print("Is completed (true/false): ");
	                        boolean completed = scanner.nextBoolean();
	                        todoDAO.update(new Todo(id, title, completed));
	                        System.out.println("Todo updated!");
	                    }
	                    case 4 -> {
	                        System.out.print("Enter Todo ID to delete: ");
	                        int id = scanner.nextInt();
	                        todoDAO.delete(id);
	                        System.out.println("Todo deleted!");
	                    }
	                    case 5 -> {
	                        System.out.println("Exiting...");
	                        return;
	                    }
	                    default -> System.out.println("Invalid choice!");
	                }
	            } catch (Exception e) {
	                e.printStackTrace();
	            }
	        }
	    }
	}



