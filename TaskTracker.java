import java.util.Scanner;
import java.util.ArrayList;
import java.util.List;

public class TaskTracker {
	public static void main(String[] args) {

		Tracker.load(FileStorage.loadLines());
		while (true) {
			System.out.print("--- TASK TRACKER ---");
			System.out.print("\n1. Add Task");
			System.out.print("\n2. View Tasks");
			System.out.print("\n3. Mark Complete");
			System.out.print("\n4. Delete");
			System.out.print("\n5. Update Task");
			System.out.print("\n6. Exit");
			Scanner in = new Scanner(System.in);
			System.out.print("\nChoose option: ");
			int choice = in.nextInt();
			in.nextLine();
			switch (choice) {
			case 1:
				System.out.print("Title: ");
				String title = in.nextLine();
				System.out.print("Priority: ");
				String Priority = in.nextLine();
				System.out.print("Date: ");
				String date = in.nextLine();
				Tracker.addTask(title, Priority, date, false);
				FileStorage.saveLines(Tracker.toLines());
				System.out.println("\nTask Added!");
				break;
			case 2:
				Tracker.viewTasks();
				break;
			case 3:
				System.out.print("\nEnter task number to mark as complete: ");
				Tracker.markComplete(in.nextInt());
				in.nextLine();
				FileStorage.saveLines(Tracker.toLines());
				break;
			case 4:
				System.out.print("\nEnter task number to delete: ");
				Tracker.removeTask(in.nextInt());
				in.nextLine();
				FileStorage.saveLines(Tracker.toLines());
				break;
			case 5:
				System.out.println("Enter Task Number to be updated: ");
				int index = in.nextInt();
				in.nextLine();
				if(index < 1 || index > Tracker.getSize()) {
					System.out.println("Invalid Task Number! ");
					break;
				}
				Task old = Tracker.getTasks().get(index - 1);
				System.out.println("Enter new information, leave empty if satisfactory: ");
				System.out.print("\nCurrent Title: " + old.getTitle() +" ||  New Title: ");
				String newTitle = in.nextLine();
				System.out.print("\nCurrent Priority: " + old.getPriority() +" || New Priority: ");
				String newPriority = in.nextLine();
				System.out.print("\nCurrent Date: " + old.getDate() +" || New Date: ");
				String newDate = in.nextLine();
				System.out.print("\nCurrent Status: " + (old.isStatus() ? "complete": "incomplete") +" || New Status (Enter 'complete' or leave as blank): ");
				String status = in.nextLine();
				Tracker.updateTask(index, newTitle, newPriority, newDate, status);
				System.out.print("\nTask has been updated!");
				FileStorage.saveLines(Tracker.toLines());
				break;
			case 6:
				FileStorage.saveLines(Tracker.toLines());
				System.out.println("Goodbye!");
				in.close();
				return;
			default:
				System.out.println("Invalid option!");
			}

		}

	}

	public static class Tracker {
		private static ArrayList<Task> tasks = new ArrayList<>();

		public static void load(List<String> lines) {
			for (String line : lines) {
				String[] parts = line.split("\\|");
				if (parts.length == 4) {
					boolean done = parts[3].trim().equalsIgnoreCase("complete");
					tasks.add(new Task(parts[0].trim(), parts[1].trim(), parts[2].trim(), done));
				}
			}
		}

		// Convert tasks to text lines for saving
		public static List<String> toLines() {
			List<String> lines = new ArrayList<>();
			for (Task t : tasks) {
				lines.add(t.title + "|" + t.Priority + "|" + t.date + "|" + (t.status ? "complete" : "incomplete"));
			}
			return lines;
		}
		
		public static void viewTasks() {
			if (tasks.isEmpty()) {
				System.out.println("No tasks found!");
			} else {
				for (int i = 0; i < tasks.size(); i++) {
					System.out.println((i + 1) + ". " + tasks.get(i));
				}
			}
		}

		public static void markComplete(int index) {
			if (index > 0 && index <= tasks.size()) {
				tasks.get(index - 1).status = true;
			} else {
				System.out.println("Invalid task number!");
			}
		}
		
		public static void removeTask(int index) {
			if (index > 0 && index <= tasks.size()) {
				tasks.remove(index - 1);
			} else {
				System.out.println("Invalid task number!");
			}
		}
		public static int getSize() {
			return tasks.size();
		}
		public static ArrayList<Task> getTasks() {
			return tasks;
		}
		
		public static void addTask(String title, String Priority, String date, boolean status) {
			tasks.add(new Task(title, Priority, date, status));
		}
		public static void updateTask(int index, String title, String Priority, String date, String status) {
			Task t = tasks.get(index - 1);
			if(!title.trim().isEmpty()) {
				t.setTitle(title);
			}
			if(!Priority.trim().isEmpty()) {
				t.setPriority(Priority);
			}
			if(!date.trim().isEmpty()) {
				t.setDate(date);
			}
			boolean newStatus = status.equalsIgnoreCase("complete");
			if(!status.trim().isEmpty()) {
				t.setStatus(newStatus);
			}
		}
	}

public static class Task {
	String title;
	String Priority;
	String date;
	boolean status;

	public Task(String title, String Priority, String date, boolean status) {
		this.title = title;
		this.Priority = Priority;
		this.date = date;
		this.status = status;

	}
	
	public String getInfo() {
		return title + " | " + Priority + " | " + date + " | " + (status ? "Complete" : "Incomplete");
	}
	/**
	 * @return the title
	 */
	public String getTitle() {
		return title;
	}

	/**
	 * @param title the title to set
	 */
	public void setTitle(String title) {
		this.title = title;
	}

	/**
	 * @return the priority
	 */
	public String getPriority() {
		return Priority;
	}

	/**
	 * @param priority the priority to set
	 */
	public void setPriority(String priority) {
		Priority = priority;
	}

	/**
	 * @return the date
	 */
	public String getDate() {
		return date;
	}

	/**
	 * @param date the date to set
	 */
	public void setDate(String date) {
		this.date = date;
	}

	/**
	 * @return the status
	 */
	public boolean isStatus() {
		return status;
	}

	/**
	 * @param status the status to set
	 */
	public void setStatus(boolean status) {
		this.status = status;
	}

}
}