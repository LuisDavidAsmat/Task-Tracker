import java.io.*;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

public class TaskFileHandler
{
    private static final String JSON_FILE_PATH = "tasks.json";

    public List<Task> loadTasks ()
    {
        File jsonFile = new File(JSON_FILE_PATH);

        if (!jsonFile.exists())
        {
            createJsonFile();

            return new ArrayList<>();
        }

        String fileContent;
        try (BufferedReader bufferedReader = new BufferedReader(new FileReader(jsonFile))) {
            fileContent = bufferedReader.lines().collect(Collectors.joining());
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

        if (fileContent.isEmpty()) {
            return new ArrayList<>();
        }

        // primitive attempt to check if JSON is valid
        if (!(fileContent.startsWith("[") || fileContent.startsWith("{")) )
        {
            return new ArrayList<>();
        }

        String[] arrayOfTasks = getTaskEntries();

        List<Task> listOfTasks = new ArrayList<>();

        for (String singleTask : arrayOfTasks)
        {
            singleTask = removeSurroundingCharacters(singleTask, '{', '}');

            // Split the task with all their fields by commas, respecting the JSON format
            String[] taskFields = singleTask.split(",(?=(?:[^\"]*\"[^\"]*\")*[^\"]*$)");

            Task task = new Task();

            for (String field : taskFields)
            {
                String[] keyValue = field.split(":",2);

                String key = keyValue[0].trim().replace("\"", "");

                String value = keyValue[1].trim().replace("\"", "");

                switch (key)
                {
                    case "id":
                        int taskId = Integer.parseInt(value.split(",")[0].trim());
                        task.setId(taskId);
                        break;

                    case "description":
                        task.setDescription(value);
                        break;

                    case "status":
                        task.setStatus(TaskStatus.valueOf(value));
                        break;

                    case "createdAt":
                        Date createdAt = parseDate(value);
                        task.setCreatedAt(createdAt);
                        break;

                    case "updatedAt":
                        Date updatedAt = parseDate(value);
                        task.setUpdatedAt(updatedAt);
                        break;
                }
            }

            listOfTasks.add(task);
        }

        return listOfTasks;
    }

    private static void createJsonFile()
    {
        try (FileWriter fileWriter = new FileWriter(JSON_FILE_PATH))
        {
            fileWriter.write("[]");
        }
        catch (IOException e)
        {
            throw new RuntimeException(e);
        }
    }

    private static String removeSurroundingCharacters (String input, char startChar, char endChar)
    {
        // Remove surrounding brackets
        input = input.trim();

        if (input.startsWith(String.valueOf(startChar)) && input.endsWith(String.valueOf(endChar)))
        {
            return input.substring(1, input.length() - 1).trim();
        }

        return input;
    }

    private static String[] getTaskEntries() {
        StringBuilder stringBuilder = new StringBuilder();

        try (BufferedReader bufferedReader = new BufferedReader(new FileReader(JSON_FILE_PATH)))
        {
            String fileLine;

            while ((fileLine = bufferedReader.readLine()) != null)
            {
                stringBuilder.append(fileLine);
            }
        }
        catch (IOException e)
        {
            throw new RuntimeException(e);
        }

        String fileContent = removeSurroundingCharacters(stringBuilder.toString(), '[', ']');

        // Split content into individual task objects
        return fileContent.split("(?<=\\}),");
    }

    public void saveTasksToFile(List<Task> tasks)
    {
        try (FileWriter fileWriter = new FileWriter(JSON_FILE_PATH))
        {
            fileWriter.write("[");

            for (int i = 0; i < tasks.size(); i++)
            {
                Task task = tasks.get(i);

                fileWriter.write("{");
                fileWriter.write("\"id\": " + task.getId() + ",");
                fileWriter.write("\"description\": \"" + task.getDescription() + "\",");
                fileWriter.write("\"status\": \"" + task.getStatus() + "\",");
                fileWriter.write("\"createdAt\": \"" + task.getFormattedCreatedAt() + "\",");
                fileWriter.write("\"updatedAt\": \"" + task.getFormattedUpdatedAt() + "\"");
                fileWriter.write("}");

                if (i < tasks.size() - 1) {
                    fileWriter.write(",");
                }
            }

            fileWriter.write("]");
        }
        catch (IOException e) {
            throw new RuntimeException(e);
        }
    };

    private Date parseDate(String dateString) {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        try {
            return sdf.parse(dateString);
        } catch (ParseException e) {
            throw new RuntimeException("Failed to parse date: " + dateString, e);
        }
    }

}
