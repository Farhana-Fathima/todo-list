<%@ page language="java" contentType="text/html; charset=ISO-8859-1" pageEncoding="ISO-8859-1" %>
<%@ page import="java.util.ArrayList" %>
<%
    ArrayList<String> tasks = (ArrayList<String>) session.getAttribute("tasks");
    ArrayList<String> completedTasks = (ArrayList<String>) session.getAttribute("completedTasks");

    if (tasks == null) {
        tasks = new ArrayList<>();
        session.setAttribute("tasks", tasks);
    }

    if (completedTasks == null) {
        completedTasks = new ArrayList<>();
        session.setAttribute("completedTasks", completedTasks);
    }

    String newTask = request.getParameter("newTask");
    if (newTask != null && !newTask.trim().isEmpty()) {
        tasks.add(newTask.trim());
    }

    String completeTask = request.getParameter("completeTask");
    if (completeTask != null && tasks.contains(completeTask)) {
        tasks.remove(completeTask);
        completedTasks.add(completeTask);
    }

    String deleteTask = request.getParameter("deleteTask");
    if (deleteTask != null) {
        tasks.remove(deleteTask);
        completedTasks.remove(deleteTask);
    }

    String oldTask = request.getParameter("oldTask");
    String updatedTask = request.getParameter("updatedTask");
    if (oldTask != null && updatedTask != null && !updatedTask.trim().isEmpty() && tasks.contains(oldTask)) {
        tasks.set(tasks.indexOf(oldTask), updatedTask.trim());
    }
%>

<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="ISO-8859-1">
    <title>To-Do List</title>
    <link rel="stylesheet" href="https://stackpath.bootstrapcdn.com/bootstrap/4.5.2/css/bootstrap.min.css">
    <style>
    body {
        background-color: #121212;
        color: #e0e0e0;
        font-family: Arial, sans-serif;
    }

    .navbar {
        background-color: #1f1f1f;
    }

    .navbar-brand, .nav-link {
        color: #e0e0e0 !important;
    }

    .task-card {
        background-color: #1f1f1f;
        border: 1px solid #333;
        margin-bottom: 15px;
        transition: box-shadow 0.2s ease-in-out;
    }

    .task-card:hover {
        box-shadow: 0 2px 6px rgba(0, 0, 0, 0.15);
    }

    .task-section h5 {
        color: #bb86fc;
        margin-bottom: 15px;
    }

    .btn-primary {
        background-color: #6200ea;
        border-color: #6200ea;
        transition: background-color 0.2s ease;
    }

    .btn-primary:hover {
        background-color: #3700b3;
    }

    .btn-success {
        background-color: #03dac6;
        border-color: #03dac6;
        transition: background-color 0.2s ease;
    }

    .btn-success:hover {
        background-color: #018786;
    }

    .btn-danger {
        background-color: #cf6679;
        border-color: #cf6679;
        transition: background-color 0.2s ease;
    }

    .btn-danger:hover {
        background-color: #b00020;
    }

    .btn-warning {
        background-color: #ffab40;
        border-color: #ffab40;
        color: #000;
        transition: background-color 0.2s ease;
    }

    .btn-warning:hover {
        background-color: #ff9100;
    }

    footer {
        background-color: #1f1f1f;
        color: #e0e0e0;
        padding: 10px 0;
        text-align: center;
        position: fixed;
        bottom: 0;
        width: 100%;
    }

    .modal-content {
        background-color: #1f1f1f;
        color: #e0e0e0;
        border: 1px solid #333;
    }

    .modal-header, .modal-footer {
        border-color: #333;
    }

    input.form-control {
        background-color: #121212;
        color: #e0e0e0;
        border: 1px solid #333;
    }

    input.form-control:focus {
        background-color: #1f1f1f;
        border-color: #6200ea;
        box-shadow: 0 0 4px #6200ea;
    }
</style>
    
</head>
<body>
<nav class="navbar navbar-expand-lg navbar-dark">
    <div class="container">
        <a class="navbar-brand" href="#">Todo Application</a>
        <button class="navbar-toggler" type="button" data-toggle="collapse" data-target="#navbarNav" aria-controls="navbarNav" aria-expanded="false" aria-label="Toggle navigation">
            <span class="navbar-toggler-icon"></span>
        </button>
        <div class="collapse navbar-collapse" id="navbarNav">
            <ul class="navbar-nav ml-auto">
                <li class="nav-item">
                    <a class="nav-link" href="adminLoginn.jsp">Log out</a>
                </li>
            </ul>
        </div>
    </div>
</nav>

<div class="container mt-5">
    <h2 class="text-center" style="color: #bb86fc;">To-Do List</h2>
    <form action="todolist.jsp" method="post" class="mb-4">
        <div class="input-group">
            <input type="text" name="newTask" placeholder="Enter a new task" class="form-control" required>
            <div class="input-group-append">
                <button class="btn btn-primary" type="submit">Add Task</button>
            </div>
        </div>
    </form>

    <div class="row">
        <!-- Tasks Section -->
        <div class="col-md-6">
            <div class="task-section">
                <h5>Your Tasks</h5>
                <% for (String task : tasks) { %>
                
                    <div class="card task-card">
                        <div class="card-body d-flex justify-content-between align-items-center">
                            <span><%= task %></span>
                            <div>
                                <form action="todolist.jsp" method="post" style="display:inline;">
                                    <input type="hidden" name="completeTask" value="<%= task %>">
                                    <button class="btn btn-sm btn-success" type="submit">Complete</button>
                                </form>
                                <form action="todolist.jsp" method="post" style="display:inline;">
                                    <input type="hidden" name="deleteTask" value="<%= task %>">
                                    <button class="btn btn-sm btn-danger" type="submit">Delete</button>
                                </form>
                                <!-- Edit Task -->
                                <button class="btn btn-sm btn-warning" type="button" data-toggle="modal" data-target="#editModal<%= task.hashCode() %>">Edit</button>

                                <!-- Edit Modal -->
                                <div class="modal fade" id="editModal<%= task.hashCode() %>" tabindex="-1" role="dialog" aria-labelledby="editModalLabel" aria-hidden="true">
                                    <div class="modal-dialog" role="document">
                                        <div class="modal-content">
                                            <div class="modal-header">
                                                <h5 class="modal-title" id="editModalLabel">Edit Task</h5>
                                                <button type="button" class="close" data-dismiss="modal" aria-label="Close">
                                                    <span aria-hidden="true">&times;</span>
                                                </button>
                                            </div>
                                            <form action="todolist.jsp" method="post">
                                                <div class="modal-body">
                                                    <input type="hidden" name="oldTask" value="<%= task %>">
                                                    <input type="text" name="updatedTask" class="form-control" value="<%= task %>" required>
                                                </di  v>
                                                <div class="modal-footer">
                                                    <button type="button" class="btn btn-secondary" data-dismiss="modal">Cancel</button>
                                                    <button type="submit" class="btn btn-warning">Save Changes</button>
                                                </div>
                                            </form>
                                        </div>
                                    </div>
                                </div>
                            </div>
                        </div>
                    </div>
                <% } %>
            </div>
        </div>

        <!-- Completed Tasks Section -->
        <div class="col-md-6">
            <div class="task-section completed-section">
                <h5>Completed Tasks</h5>
                <% for (String completedTask : completedTasks) { %>
                    <div class="card task-card">
                        <div class="card-body d-flex justify-content-between align-items-center">
                            <span><%= completedTask %></span>
                            <form action="todolist.jsp" method="post" style="display:inline;">
                                <input type="hidden" name="deleteTask" value="<%= completedTask %>">
                                <button class="btn btn-sm btn-danger" type="submit">Delete</button>
                            </form>
                        </div>
                    </div>
                <% } %>
            </div>
        </div>
    </div>
</div>

<footer class="footer">
    <div class="container">
        <span>© Copyright 2024. All Rights Reserved.</span>
    </div>
</footer>

<script src="https://code.jquery.com/jquery-3.5.1.slim.min.js"></script>
<script src="https://cdn.jsdelivr.net/npm/@popperjs/core@2.5.3/dist/umd/popper.min.js"></script>
<script src="https://stackpath.bootstrapcdn.com/bootstrap/4.5.2/js/bootstrap.min.js"></script>
</body>
</html>
