


public class Main
{
    public static void main(String[] args)
    {
        TaskFileHandler taskFileHandler = new TaskFileHandler();
        TaskManager taskManager = new TaskManager(taskFileHandler);
        TaskCLI taskCLI = new TaskCLI(taskManager);
        taskCLI.handleUserCommand(args);
        // Example: Add a task
        //taskManager.addTask("Walk the dog");
       //taskManager.addTask("Write the report");
        //taskManager.addTask("Descansar no teu peito");
        //taskManager.updateTaskDescription(2, "Here it comes");
        //taskManager.updateTaskStatus("in-progress", 2);
        //taskManager.updateTaskStatus("in-progress", 1);
        //taskManager.deleteTask(2);

        // Load and display tasks
        //taskManager.printAllTasks();
        //taskManager.printTasksByStatus("done");
        //taskManager.printTasksByStatus("in-progress");
    }
}