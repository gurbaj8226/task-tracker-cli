import java.util.Scanner;

public class TaskTracker {
	public static void main(String[] args) {
		Scanner in = new Scanner(System.in);
		Tracker.reloadFromDB();
		while (true) {
			System.out.print("--- TASK TRACKER ---");
			System.out.print("\n1. Add Task");
			System.out.print("\n2. View Tasks");
			System.out.print("\n3. Mark Complete");
			System.out.print("\n4. Delete");
			System.out.print("\n5. Update Task");
			System.out.print("\n6. Sort Tasks");
			System.out.print("\n7. Exit");
			
			System.out.print("\nChoose option: ");
			int choice;
			try {
				choice = in.nextInt();
			} catch(Exception e) {
				in.nextLine();
				System.out.println("Please enter a valid number.");
				continue;
			}
			in.nextLine();
			switch (choice) {
			case 1:
				System.out.print("Enter Title: ");
				String title = in.nextLine();
				
				System.out.print("Enter Priority (1 = High, 2 = Medium, 3 = Low): ");
				int priority;
				try {
					priority = in.nextInt();
				}catch(Exception e) {
					in.nextLine();
					System.out.println("Invalid priority. Task not added.");
					break;
				}
				in.nextLine();
				
				System.out.print("Enter Date (YYYY-MM-DD): ");
				String date = in.nextLine();
				
				Tracker.addTask(title, priority, date, false);
				System.out.println("\nTask Added!");
				break;
			case 2:
				Tracker.viewTasksFromDB();
				break;
			case 3:
				System.out.print("\nEnter task ID to mark as complete: ");
				int id;
				try {
					id = in.nextInt();
				} catch(Exception e) {
					in.nextLine();
					System.out.println("Invalid ID");
					break;
				}
				in.nextLine();
				Tracker.markComplete(id);
				System.out.println("\nTask marked complete");
				break;
			case 4:
				System.out.print("\nEnter task ID to delete: ");
				int identification;
				try {
					identification = in.nextInt();
				} catch(Exception e) {
					in.nextLine();
					System.out.println("Invalid ID");
					break;
				}
				in.nextLine();
				Tracker.removeTask(identification);
				System.out.println("\nTask Deleted!");
				break;
			case 5:
				System.out.println("Enter Task ID to be updated: ");
				int ID;
				try {
					ID = in.nextInt();
				} catch(Exception e) {
					in.nextLine();
					System.out.println("Invalid ID");
					break;
				}
				in.nextLine();
				
				Task old = TaskRepositorySQL.getTaskById(ID);
				if(old == null) {
					System.out.println("INVALID TASK ID!");
					break;
				}
				
				System.out.print("\nCurrent Title: " + old.getTitle() +" ||  New Title: ");
				String newTitle = in.nextLine();
				
				System.out.print("\nCurrent Priority: " + old.getPriority() +" || New Priority: ");
				String newPriority = in.nextLine();
				
				System.out.print("\nCurrent Date: " + old.getDate() +" || New Date: ");
				String newDate = in.nextLine();
				
				System.out.print("\nCurrent Status: " + (old.isStatus() ? "complete": "incomplete") +" || New Status (Enter 'complete' or leave as blank): ");
				String status = in.nextLine();
				
				Tracker.updateTask(ID, newTitle, newPriority, newDate, status);
				System.out.print("\nTask has been updated!");
		
				break;
			case 6:
				System.out.println("Pick Method of Sortation: ");
				System.out.println("1. Sort By Priority");
				System.out.println("2. Sort By Date");
				System.out.println("3. Sort By Status");
				System.out.print("Choose: ");
				int c;
				try {
					c = in.nextInt();
				} catch(Exception e) {
					in.nextLine();
					System.out.println("Invalid option");
					break;
				}
				in.nextLine();
				
				switch(c) {
				case 1:
					System.out.println("\n--- Sorted By Priority ---");
					Tracker.sortByPrioritySQL();
					break;
				case 2: 
					System.out.println("\n--- Sorted By Date ---");
					Tracker.sortByDateSQL();
					break;
				case 3:
					System.out.println("\n--- Sorted By Status ---");
					Tracker.sortByStatusSQL();
					break;
				default:
					System.out.println("Invalid Option!");
				}
				System.out.println("Tasks have Been Sorted!");
				break;
			case 7:
				System.out.println("Goodbye!");
				in.close();
				return;
			default:
				System.out.println("Invalid option!");
			}
		}

	}

}