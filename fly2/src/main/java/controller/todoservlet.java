package controller;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import model.Todo;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.hibernate.boot.Metadata;
import org.hibernate.boot.MetadataSources;
import org.hibernate.boot.registry.StandardServiceRegistry;
import org.hibernate.boot.registry.StandardServiceRegistryBuilder;

import java.io.IOException;
import java.util.List;

@WebServlet("/todoservlet1")
public class todoservlet extends HttpServlet {
    private static final long serialVersionUID = 1L;

    public todoservlet() {
        super();
    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        // Retrieve list of todos from the database
        StandardServiceRegistry ssr = new StandardServiceRegistryBuilder().configure("hibernate.cfg.xml").build();
        Metadata md = new MetadataSources(ssr).getMetadataBuilder().build();
        SessionFactory sf = md.getSessionFactoryBuilder().build();
        Session session = sf.openSession();
        
        List<Todo> todos = session.createQuery("FROM Todo", Todo.class).getResultList();
        session.close();

        // Send the todos list to the JSP page
        request.setAttribute("todos", todos);
        request.getRequestDispatcher("todolist.jsp").forward(request, response);
    }

    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        // Determine the action from the form submission
        String action = request.getParameter("action");
        if (action != null) {
            StandardServiceRegistry ssr = new StandardServiceRegistryBuilder().configure("hibernate.cfg.xml").build();
            Metadata md = new MetadataSources(ssr).getMetadataBuilder().build();
            SessionFactory sf = md.getSessionFactoryBuilder().build();
            Session session = sf.openSession();
            Transaction transaction = session.beginTransaction();

            switch (action) {
                case "add":
                    // Create a new Todo
                    String title = request.getParameter("title");
                    Boolean completed = Boolean.valueOf(request.getParameter("completed"));
                    Todo newTodo = new Todo();
                    newTodo.setTitle(title);
                    newTodo.setCompleted(completed);
                    session.save(newTodo);
                    transaction.commit();
                    break;

                case "update":
                    // Update an existing Todo
                    Long id = Long.valueOf(request.getParameter("id"));
                    Todo todoToUpdate = session.get(Todo.class, id);
                    if (todoToUpdate != null) {
                        todoToUpdate.setTitle(request.getParameter("updatedTitle"));
                        todoToUpdate.setCompleted(Boolean.valueOf(request.getParameter("updatedCompleted")));
                        session.update(todoToUpdate);
                        transaction.commit();
                    }
                    break;

                case "delete":
                    // Delete a Todo
                    Long deleteId = Long.valueOf(request.getParameter("id"));
                    Todo todoToDelete = session.get(Todo.class, deleteId);
                    if (todoToDelete != null) {
                        session.delete(todoToDelete);
                        transaction.commit();
                    }
                    break;
            }
            session.close();
        }

        // Redirect to the todos list page after performing the action
        response.sendRedirect("todoservlet");
    }
}
