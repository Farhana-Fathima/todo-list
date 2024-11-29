<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>To-Do Application</title>
    <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.0/dist/css/bootstrap.min.css" rel="stylesheet">
    <style>
        body {
            background-color: #f8f9fa;
        }
        .todo-card {
            margin-top: 20px;
        }
        .btn-sm {
            font-size: 0.75rem;
        }
    </style>
</head>
<body>
<div class="container my-5">
    <h1 class="text-center mb-4">To-Do Application</h1>

    <!-- Add Task Form -->
    <div class="card p-4 shadow-sm">
        <form id="addTaskForm">
            <div class="mb-3">
                <label for="title" class="form-label">Task Title</label>
                <input type="text" class="form-control" id="title" placeholder="Enter task title" required>
            </div>
            <div class="mb-3">
                <label for="description" class="form-label">Task Description</label>
                <textarea class="form-control" id="description" rows="3" placeholder="Enter task description" required></textarea>
            </div>
            <button type="submit" class="btn btn-primary w-100">Add Task</button>
        </form>
    </div>

    <!-- Task List -->
    <div id="taskList" class="todo-card"></div>
</div>

<script>
    // Mock data for tasks (use AJAX to replace this with server data in production)
    let tasks = [];

    // Function to render tasks
    function renderTasks() {
        const taskList = document.getElementById('taskList');
        taskList.innerHTML = '';

        if (tasks.length === 0) {
            taskList.innerHTML = '<p class="text-center text-muted">No tasks available.</p>';
            return;
        }

        tasks.forEach((task, index) => {
            const taskCard = `
                <div class="card shadow-sm mb-3">
                    <div class="card-body">
                        <h5 class="card-title">${task.title}</h5>
                        <p class="card-text">${task.description}</p>
                        <div class="d-flex justify-content-between align-items-center">
                            <button class="btn btn-success btn-sm" onclick="markCompleted(${index})">
                                ${task.completed ? 'Completed' : 'Mark as Complete'}
                            </button>
                            <button class="btn btn-danger btn-sm" onclick="deleteTask(${index})">Delete</button>
                        </div>
                    </div>
                </div>
            `;
            taskList.innerHTML += taskCard;
        });
    }

    // Add task event
    document.getElementById('addTaskForm').addEventListener('submit', function (event) {
        event.preventDefault();
        const title = document.getElementById('title').value;
        const description = document.getElementById('description').value;

        // Add task to mock data
        tasks.push({ title, description, completed: false });
        renderTasks();

        // Clear form
        document.getElementById('addTaskForm').reset();
    });

    // Mark task as completed
    function markCompleted(index) {
        tasks[index].completed = !tasks[index].completed;
        renderTasks();
    }

    // Delete task
    function deleteTask(index) {
        tasks.splice(index, 1);
        renderTasks();
    }

    // Initial render
    renderTasks();
</script>
<script src="https://cdn.jsdelivr.net/npm/bootstrap@5.3.0/dist/js/bootstrap.bundle.min.js"></script>
</body>
</html>
    