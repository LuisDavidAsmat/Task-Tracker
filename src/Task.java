import java.text.SimpleDateFormat;
import java.util.Date;

public class Task {
    private static int counter = 1;
    private int id;
    private String description;
    private TaskStatus status; // The status of the task (todo, in-progress, done)
    private Date createdAt;
    private Date updatedAt;

    // Default constructor needed for Jackson deserialization
    public Task() {}

    public Task(String taskDescription)
    {
        this.id = counter++;
        this.description = taskDescription;
        this.status = TaskStatus.todo;
        this.createdAt = new Date();
        this.updatedAt = new Date();
    }

    // helper methods
    public static void updateCounter(int maxId) {
        counter = maxId + 1;
    }

    private String formatDate (Date date)
    {
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");

        return simpleDateFormat.format(date);
    }

    public String getFormattedCreatedAt ()
    {
        return  formatDate(this.getCreatedAt());
    };

    public String getFormattedUpdatedAt ()
    {
        return formatDate(this.getUpdatedAt());
    };

    // getters and setters
    public int getId() {
        return id;
    }

    public void setId (int taskId)
    {
        this.id = taskId;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public TaskStatus getStatus() {
        return status;
    }

    public void setStatus(TaskStatus status) {
        this.status = status;
    }

    public Date getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(Date createdAt) {
        this.createdAt = createdAt;
    }

    public Date getUpdatedAt() {
        return updatedAt;
    }

    public void setUpdatedAt(Date updatedAt) {
        this.updatedAt = updatedAt;
    }
}
