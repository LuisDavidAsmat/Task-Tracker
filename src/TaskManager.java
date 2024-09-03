import javax.management.MBeanRegistration;
import java.io.*;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;


public class TaskManager
{
    private final List<Task> tasks = new ArrayList<>();
    private final TaskFileHandler taskFileHandler;


    public TaskManager(TaskFileHandler taskFileHandler)
    {
        this.taskFileHandler = taskFileHandler;

        List<Task> loadedTasks = taskFileHandler.loadTasks();
        if (loadedTasks.isEmpty()) {
            // Handle the case where no tasks were loaded
            System.out.println("No tasks were loaded from the file.");
        }
        else {
            tasks.addAll(loadedTasks);
        }

        updateTaskCounter();
    }

    private void updateTaskCounter()
    {
        int maxId = tasks.stream().mapToInt(Task::getId).max().orElse(0);

        Task.updateCounter(maxId);
    }

    private void saveTask()
    {
        taskFileHandler.saveTasksToFile(tasks);
    };

    public void addTask (String taskDescription)
    {
        Task task = new Task(taskDescription);

        tasks.add(task);

        saveTask();
    }

    public void updateTaskDescription(int taskId, String newTaskDescription)
    {
        Task task = getTaskById(taskId);

        if (task != null)
        {
            try
            {
                task.setDescription(newTaskDescription);
                task.setUpdatedAt(new Date());

                saveTask();
            }
            catch (IllegalArgumentException e)
            {
                System.err.println("Invalid task description: " + newTaskDescription);
            }
        }
        else {
            System.err.println("Task not found");
        }
    };

    public void updateTaskStatus(String newTaskStatus, int taskId)
    {
        Task task = getTaskById(taskId);

        if (task != null)
        {
            try
            {
                if (Objects.equals(newTaskStatus, "in-progress"))
                {
                    newTaskStatus = "in_progress";
                }

                TaskStatus taskStatus = TaskStatus.valueOf(newTaskStatus);

                task.setStatus(taskStatus);
                task.setUpdatedAt(new Date());

                saveTask();
            }
            catch (IllegalArgumentException e)
            {
                System.err.println("Invalid task status: " + newTaskStatus);
            }
        }
        else {
            System.err.println("Task not found");
        }
    };

    public boolean deleteTask(int taskId)
    {
        boolean removedTask = tasks.removeIf(task -> task.getId() == taskId);

        if (removedTask)
        {
            saveTask();
        }

        return removedTask;
    }

    public void removeTask (int taskId)
    {
        boolean wasTaskRemoved = deleteTask(taskId);

        if (wasTaskRemoved)
        {
            System.out.println("Task successfully removed.");
        }
        else
        {
            System.err.println("Task with ID " + taskId + " was not found.");
        }
    }

    public List<Task> getTasks() {
        return tasks;
    }

    public Task getTaskById(int taskId)
    {
        return tasks.stream().filter(task -> task.getId() == taskId).findFirst().orElse(null);
    }

    public List<Task> getTasksByStatus(TaskStatus taskStatus)
    {
        return tasks.stream()
                .filter(task -> task.getStatus() == taskStatus)
                .collect(Collectors.toList());
    }

    public void printAllTasks ()
    {
        getTasks().forEach(task -> {
            System.out.println("ID: " + task.getId());
            System.out.println("Title: " + task.getDescription());
            System.out.println("Status: " + task.getStatus());
            System.out.println("Created at: " + task.getFormattedCreatedAt());
            System.out.println("Updated at: " + task.getFormattedUpdatedAt());
            System.out.println("------------");
        });
    }

    public void printTasksByStatus(String status)
    {
        switch (status.toLowerCase())
        {
            case "todo", "in-progress", "done":
            {
                TaskStatus taskStatus = getTaskStatusFromString(status);

                List<Task> tasks = getTasksByStatus(taskStatus);

                tasks.forEach(task -> {
                    System.out.println("ID: " + task.getId());
                    System.out.println("Title: " + task.getDescription());
                    System.out.println("Status: " + task.getStatus());
                    System.out.println("Created at: " + task.getFormattedCreatedAt());
                    System.out.println("Updated at: " + task.getFormattedUpdatedAt());
                    System.out.println("------------");
                });
                break;
            }

            default:
            {

            }

        }

    }

    private TaskStatus getTaskStatusFromString(String status)
    {
        return switch (status)
        {
            case "todo" -> TaskStatus.todo;
            case "in-progress" -> TaskStatus.in_progress;
            case "done" -> TaskStatus.done;
            default -> throw new IllegalStateException("Unknown status: " + status);
        };
    }
}