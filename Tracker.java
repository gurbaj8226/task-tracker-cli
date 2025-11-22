import java.time.LocalDate;
import java.time.format.DateTimeParseException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

public class Tracker {
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
		public static void sortByPriority() {
			Collections.sort(tasks, (a,b) -> {
				return priorityValue(a) - priorityValue(b);
			});
		}
		public static int priorityValue(Task t) {
			if(t.getPriority().startsWith("High")) {
				return 1;
			}
			if(t.getPriority().startsWith("Med")) {
				return 2;
			}
			return 3;
		}
		public static void sortByStatus() {
			Collections.sort(tasks, (a,b) ->{
				return Boolean.compare(a.isStatus(), b.isStatus());
			});
		}
		public static void sortByDate() {
			Collections.sort(tasks, (a,b) -> {
				return parseDate(a).compareTo(parseDate(b));
			});
		}
		public static LocalDate parseDate(Task t) {
			try {
			return LocalDate.parse(t.getDate());
		} catch (DateTimeParseException e) {
			return LocalDate.MAX;
		}
	}
}