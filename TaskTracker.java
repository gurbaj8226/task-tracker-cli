import java.util.Scanner;
import java.util.ArrayList;

public class TaskTracker {
	public static void main(String[] args) {
		System.out.println("--- TASK TRACKER ---");
		System.out.println("1. Add Task");
		System.out.println("2. View Tasks");
		System.out.println("3. Mark Complete");
		System.out.println("4. Delete");
		System.out.println("5. Exit");
		Scanner in = new Scanner(System.in);
		System.out.println("Choose option: ");
		int choice = in.nextInt();

	}

}

class Tracker {
	private static ArrayList<Task> tasks = new ArrayList<>();

	public static void addTask(String title, String Priority, String date, boolean status) {
		tasks.add(new Task(title, Priority, date, status));
	}
	public static void viewTasks() {
		for(Task t: tasks) {
			System.out.println(t.getInfo());
		}
	}
	public static void removeTask(int num) {
		tasks.remove(num-1);
	}
	public static void markComplete(int num) {
		tasks.get(num-1).setStatus(true);
	}
	
}

class Task {
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
		return title+" ("+Priority+", Due: "+date+")";
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
