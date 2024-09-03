public class TaskCLI
{
    private final TaskManager taskManager;

    public TaskCLI(TaskManager taskManager) {
        this.taskManager = taskManager;
    }

    public void handleUserCommand (String[] commands)
    {
        if (commands.length == 0)
        {
            System.out.println("Please provide a command.");
            return;
        }

        String command = commands[0];

        switch (command)
        {
            case "add":
            {
                // join commands "add + description". Subtract after the "
                String taskDescription = String.join(" ", commands).substring(4).trim();
                taskManager.addTask(taskDescription);
                System.out.println("Task added successfully");
                break;
            }
            case "update":
            {
                int taskId = Integer.parseInt(commands[1]);
                String newTaskDescription = String.join(" ", commands).substring(
                        commands[0].length()+commands[1].length()+2
                        ).trim();
                taskManager.updateTaskDescription(taskId, newTaskDescription);
                System.out.println("Task updated successfully");
                break;
            }
            case "mark-in-progress":
            {
                int taskId = Integer.parseInt(commands[1]);
                String newTaskStatus = String.valueOf(TaskStatus.in_progress);
                taskManager.updateTaskStatus(newTaskStatus, taskId);
                System.out.println("Task status updated successfully");
                break;
            }
            case "mark-done":
            {
                int taskId = Integer.parseInt(commands[1]);
                String newTaskStatus = String.valueOf(TaskStatus.done);
                taskManager.updateTaskStatus(newTaskStatus, taskId);
                System.out.println("Task status updated successfully");
                break;
            }
            case "delete":
            {
                int taskId = Integer.parseInt(commands[1]);
                taskManager.deleteTask(taskId);
                System.out.println("Task deleted successfully");
                break;
            }
            case "list":
            {
                if (commands.length == 1)
                {
                    taskManager.printAllTasks();
                }
                else if (commands.length == 2)
                {
                    String status = commands[1].toLowerCase();
                    taskManager.printTasksByStatus(status);
                }
                else {
                    System.err.println("Invalid number of arguments for 'list' command.");
                }
                break;
            }
        }
    }
}
