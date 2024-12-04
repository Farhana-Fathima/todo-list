<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ page import="java.sql.*" %>
<%@ page import="java.time.LocalDate" %>
<%@ page import="db.DatabaseConnection" %>
<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <title>My To-Do List</title>
    <link rel="stylesheet" href="https://stackpath.bootstrapcdn.com/bootstrap/4.5.2/css/bootstrap.min.css">
    <link href="https://fonts.googleapis.com/css2?family=Inter:wght@400;600;700&display=swap" rel="stylesheet">
    <style>
    :root {
        --bg-primary: #121212;
        --bg-secondary: #1e1e1e;
        --bg-card: #2c2c2c;
        --text-primary: #e0e0e0;
        --text-secondary: #b0b0b0;
        --accent-color: #3498db;
        --accent-hover: #2980b9;
        --border-color: #444;
        --shadow-color: rgba(0, 0, 0, 0.3);
    }

    * {
        box-sizing: border-box;
        transition: all 0.3s ease;
    }

    body {
        background-color: var(--bg-primary);
        color: var(--text-primary);
        font-family: 'Inter', 'Segoe UI', Roboto, sans-serif;
        line-height: 1.6;
        padding-top: 50px;
        overflow-x: hidden;
    }

    .container {
        max-width: 1200px;
        padding: 0 15px;
        margin: 0 auto;
    }

    /* Enhanced Title */
    h1 {
        font-weight: 700;
        color: var(--accent-color);
        text-shadow: 0 2px 4px rgba(0, 0, 0, 0.3);
        letter-spacing: -1px;
        position: relative;
        text-align: center;
        margin-bottom: 30px;
        padding-bottom: 10px;
    }

    h1::after {
        content: '';
        position: absolute;
        bottom: 0;
        left: 50%;
        transform: translateX(-50%);
        width: 100px;
        height: 3px;
        background: linear-gradient(to right, transparent, var(--accent-color), transparent);
    }

    /* Card Styling */
    .card {
        background-color: var(--bg-card);
        border: none;
        border-radius: 12px;
        box-shadow: 0 10px 25px var(--shadow-color);
        margin-bottom: 20px;
        overflow: hidden;
        perspective: 1000px;
    }

    .card-header {
        background-color: var(--bg-secondary);
        color: var(--text-primary);
        padding: 15px 20px;
        font-weight: 600;
        border-bottom: 2px solid var(--border-color);
    }

    .task-card {
        transform-style: preserve-3d;
        transition: all 0.4s cubic-bezier(0.175, 0.885, 0.32, 1.275);
    }

    .task-card:hover {
        transform: scale(1.02) rotateX(5deg);
        box-shadow: 0 15px 35px rgba(0,0,0,0.4);
    }

    /* Form Styling */
    .form-control {
        background-color: var(--bg-secondary);
        color: var(--text-primary);
        border: 1px solid var(--border-color);
        border-radius: 8px;
        padding: 10px 15px;
        transition: all 0.3s ease;
    }

    .form-control:focus {
        background-color: var(--bg-primary);
        border-color: var(--accent-color);
        box-shadow: 0 0 0 0.2rem rgba(52, 152, 219, 0.25);
    }

    /* Buttons */
    .btn {
        border-radius: 8px;
        text-transform: uppercase;
        font-weight: 600;
        letter-spacing: 0.5px;
        transition: all 0.3s ease;
    }

    .btn-primary {
        background-color: var(--accent-color);
        border-color: var(--accent-color);
    }

    .btn-primary:hover {
        background-color: var(--accent-hover);
        transform: translateY(-2px);
        box-shadow: 0 4px 6px var(--shadow-color);
    }

    .btn-danger {
        background-color: #e74c3c;
        border-color: #c0392b;
    }

    .btn-danger:hover {
        background-color: #c0392b;
        transform: translateY(-2px);
        box-shadow: 0 4px 6px var(--shadow-color);
    }

    /* Completed Task Styling */
    .completed {
        opacity: 0.7;
        text-decoration: line-through;
        color: var(--text-secondary);
    }

    .completed .form-control {
        background-color: rgba(0,0,0,0.1);
    }

    /* Checkbox Styling */
    .form-check-input {
        background-color: var(--bg-secondary);
        border: 2px solid var(--border-color);
    }

    .form-check-input:checked {
        background-color: var(--accent-color);
        border-color: var(--accent-color);
    }

    /* Animations */
    @keyframes fadeInUp {
        from {
            opacity: 0;
            transform: translate3d(0, 20px, 0);
        }
        to {
            opacity: 1;
            transform: translate3d(0, 0, 0);
        }
    }

    .task-card {
        animation: fadeInUp 0.6s ease;
        animation-fill-mode: backwards;
    }

    .task-card:nth-child(2n) {
        animation-delay: 0.2s;
    }

    .task-card:nth-child(3n) {
        animation-delay: 0.4s;
    }

    /* Responsive Adjustments */
    @media (max-width: 768px) {
        .form-row {
            flex-direction: column;
        }

        .form-row > div {
            margin-bottom: 10px;
            width: 100%;
        }
    }
</style>
</head>
<body>
    <div class="container">
        <h1 class="text-center mb-4">To-Do List</h1>

        <!-- Add New Task -->
        <div class="card mb-4">
            <div class="card-header text-white">Add New Task</div>
            <div class="card-body">
                <form action="AddTask" method="post">
                    <div class="form-row">
                        <div class="col-md-4">
                            <input type="text" class="form-control" name="title" placeholder="Task Title" required>
                        </div>
                        <div class="col-md-4">
                            <input type="text" class="form-control" name="description" placeholder="Description">
                        </div>
                        <div class="col-md-3">
                            <input type="date" class="form-control" name="taskDate" required>
                        </div>
                        <div class="col-md-1">
                            <button type="submit" class="btn btn-primary">Add</button>
                        </div>
                    </div>
                </form>
            </div>
        </div>

        <!-- Date Filter -->
        <div class="form-group">
            <form method="get" class="form-inline justify-content-center">
                <label class="mr-2 text-white">Filter Tasks by Date: </label>
                <input type="date" name="selectedDate" class="form-control mr-2" 
                       value="<%= request.getParameter("selectedDate") != null ? request.getParameter("selectedDate") : LocalDate.now() %>">
                <button type="submit" class="btn btn-secondary">Show Tasks</button>
            </form>
        </div>

        <div class="row">
            <%
                String selectedDateStr = request.getParameter("selectedDate");
                LocalDate selectedDate = selectedDateStr != null 
                    ? LocalDate.parse(selectedDateStr) 
                    : LocalDate.now();

                try {
                    Connection conn = DatabaseConnection.getConnection();
                    String sql = "SELECT * FROM tasks WHERE task_date = ?";
                    PreparedStatement pstmt = conn.prepareStatement(sql);
                    pstmt.setDate(1, java.sql.Date.valueOf(selectedDate));
                    ResultSet rs = pstmt.executeQuery();

                    while (rs.next()) {
            %>
                <div class="col-md-12">
                    <div class="card task-card <%= rs.getBoolean("completed") ? "completed" : "" %>">
                        <div class="card-body">
                            <form action="UpdateTask" method="post">
                                <input type="hidden" name="taskId" value="<%= rs.getInt("id") %>">
                                <div class="form-row">
                                    <div class="col-md-4">
                                        <input type="text" class="form-control" name="title" 
                                               value="<%= rs.getString("title") %>" required>
                                    </div>
                                    <div class="col-md-4">
                                        <input type="text" class="form-control" name="description" 
                                               value="<%= rs.getString("description") %>">
                                    </div>
                                    <div class="col-md-2">
                                        <div class="form-check">
                                            <input type="checkbox" class="form-check-input" name="completed" 
                                                   id="completed<%= rs.getInt("id") %>"
                                                   <%= rs.getBoolean("completed") ? "checked" : "" %>>
                                            <label class="form-check-label" for="completed<%= rs.getInt("id") %>">
                                                Completed
                                            </label>
                                        </div>
                                    </div>
                                    <div class="col-md-2">
                                        <div class="btn-group">
                                            <button type="submit" class="btn btn-primary btn-sm">Update</button>
                                            <button type="submit" formaction="DeleteTask" 
                                                    class="btn btn-danger btn-sm">Delete</button>
                                        </div>
                                    </div>
                                </div>
                            </form>
                        </div>
                    </div>
                </div>
            <% 
                }
  
                rs.close();
                pstmt.close();
                conn.close();
            } catch (SQLException e) {
                e.printStackTrace();
            %>
                <div class="col-md-12">
                    <div class="alert alert-danger">
                        Error loading tasks: <%= e.getMessage() %>
                    </div>
                </div>
            <% } %>
        </div>
    </div>

    <script src="https://code.jquery.com/jquery-3.5.1.slim.min.js"></script>
    <script src="https://cdn.jsdelivr.net/npm/@popperjs/core@2.5.3/dist/umd/popper.min.js"></script>
    <script src="https://stackpath.bootstrapcdn.com/bootstrap/4.5.2/js/bootstrap.min.js"></script>
</body>
</html>
