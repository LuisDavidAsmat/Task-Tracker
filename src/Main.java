


public class Main
{
    public static void main(String[] args)
    {
        TaskFileHandler taskFileHandler = new TaskFileHandler();
        TaskManager taskManager = new TaskManager(taskFileHandler);
        TaskCLI taskCLI = new TaskCLI(taskManager);
        taskCLI.handleUserCommand(args);
    }
}