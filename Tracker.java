import java.util.ArrayList;
import java.util.List;

public class Tracker {

    private static ArrayList<Task> tasks = new ArrayList<>();

    // Load tasks from DB at startup (or after changes)
    public static void reloadFromDB() {
        tasks = new ArrayList<>(TaskRepositorySQL.getAllTasksFromDB());
    }

    // View tasks directly from SQL (always freshest)
    public static void viewTasksFromDB() {
        List<Task> list = TaskRepositorySQL.getAllTasksFromDB();
        if (list.isEmpty()) {
            System.out.println("No tasks found.");
            return;
        }
        for (Task t : list) {
            System.out.println(t);
        }
    }

    // Add a task (SQL insert + add to memory)
    public static void addTask(String title, int priority, String date, boolean status) {
        Task t = new Task(title, priority, date, status);
        int generatedId = TaskRepositorySQL.insertTask(t);   // insert into DB and return auto ID
        if(generatedId != -1) {
        t.setID(generatedId);
        tasks.add(t);
    }
        else {
        	System.out.println("Failed to insert task into database.");
        }
    }

    // Delete task (SQL delete + remove from memory)
    public static void removeTask(int id) {
        TaskRepositorySQL.deleteTaskById(id);
        reloadFromDB();
    }

    // Mark task complete
    public static void markComplete(int id) {
        TaskRepositorySQL.markCompleteInDB(id);
        reloadFromDB();
    }

    // Update task
    public static void updateTask(int id, String title, String priority, String date, String status) {

    	Task t = TaskRepositorySQL.getTaskById(id);
        if (t == null) {
            System.out.println("Invalid ID.");
            return;
        }

        // Title update
        if (!title.trim().isEmpty()) {
            t.setTitle(title);
        }

        // Priority update (if user typed something)
        if (!priority.trim().isEmpty()) {
            try {
                int newPriority = Integer.parseInt(priority.trim());
                if (newPriority >= 1 && newPriority <= 3) {
                    t.setPriority(newPriority);
                } else {
                    System.out.println("Invalid priority value. Keeping old priority.");
                }
            } catch (NumberFormatException e) {
                System.out.println("Invalid priority input. Keeping old priority.");
            }
        }

        // Date update
        if (!date.trim().isEmpty()) {
            t.setDate(date);
        }

        // Status update
        if (!status.trim().isEmpty()) {
            if (status.equalsIgnoreCase("complete")) {
                t.setStatus(true);
            } else if (status.equalsIgnoreCase("incomplete")) {
                t.setStatus(false);
            } else {
                System.out.println("Invalid status input. Keeping old status.");
            }
        }

        // Persist changes
        TaskRepositorySQL.updateTaskInDB(t);
        reloadFromDB();
    }

    // Get a single task by ID
    public static Task getTaskByIdFromCache(int id) {
        for (Task t : tasks) {
            if (t.getID() == id) 
            	return t;
        }
        return null;
    }

    // Sorting (still done in memory)
    public static void sortByPrioritySQL() {
        List<Task> sorted = TaskRepositorySQL.getTasksSortedByPriority();
        tasks = new ArrayList<>(sorted);
        if(tasks.isEmpty()) {
        	System.out.println("No tasks found.");
        	return;
        }
        for (Task t : sorted) {
            System.out.println(t);
        }
    }

    public static void sortByDateSQL() {
        List<Task> sorted = TaskRepositorySQL.getTasksSortedByDate();
        tasks = new ArrayList<>(sorted);
        if(tasks.isEmpty()) {
        	System.out.println("No tasks found.");
        	return;
        }
        for (Task t : sorted) {
            System.out.println(t);
        }
    }

    public static void sortByStatusSQL() {
        List<Task> sorted = TaskRepositorySQL.getTasksSortedByStatus();
        tasks = new ArrayList<>(sorted);
        if(tasks.isEmpty()) {
        	System.out.println("No tasks found.");
        	return;
        }
        for (Task t : sorted) {
            System.out.println(t);
        }
    }


    public static ArrayList<Task> getTasks() {
        return tasks;
    }

    public static int getSize() {
        return tasks.size();
    }
}
