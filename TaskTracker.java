import java.util.Scanner;

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
			in.close();
		}

	}

}