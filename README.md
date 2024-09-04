# Task Tracker 
Task tracker is a project used to track and manage tasks. A simple command line interface (CLI) to track what you need to do, what you have done, and what you are currently working on. This project helped me to develop the following programming skills:
* Working with the filesystem.
* Handling user inputs.
* Building a simple CLI application.

This project is based on a backend project idea from [https://roadmap.sh/projects/task-tracker](https://roadmap.sh/projects/task-tracker)
# Project Features
* Basic CRUD operations for managing tasks.
* Simple data persistance by saving tasks to a json file.
## Installation

1. Clone the repository and enter src folder.
   ```bash
   git clone https://github.com/LuisDavidAsmat/Task-Tracker.git
   cd src
3. Compile the code.
   ```bash
   javac Main.java Task.java TaskCLI.java TaskFileHandler.java TaskManager.java TaskStatus.java 
4. Run the application by using the following commands.
   ```bash
    # Adding a new task
    java Main add "Buy groceries"
       
    # Updating tasks
    java Main update 1 "Buy groceries and cook dinner"
    
    # Marking a task as in progress or done
    java Main mark-in-progress 1
    java Main mark-done 1
    
    # Listing all tasks
    java Main list
    
    # Listing tasks by status
    java Main list done
    java Main list todo
    java Main list in-progress

    # Delete tasks 
    java Main delete 1
