public class Task {
	private int id;
	private String title;
	private int priority;
	private String date;
	private boolean status;

	public Task(int id, String title, int priority, String date, boolean status) {
		this.id = id;
		this.title = title;
		this.priority = priority;
		this.date = date;
		this.status = status;

	}
	public Task(String title, int priority, String date, boolean status) {
		this.title = title;
		this.priority = priority;
		this.date = date;
		this.status = status;

	}
	private String priorityLabel() {
		return switch(priority) {
		case 1 -> "High";
		case 2 -> "Medium";
		case 3 -> "Low";
		default -> "Unknown";
		};
	}
	public int getID() {
		return id;
	}
	public void setID(int id) {
		this.id = id;
	}
	public String toString() {
		return id + ". " + title + " | " + priorityLabel() + " | " + date + " | " + (status ? "Complete" : "Incomplete");
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
	public int getPriority() {
		return priority;
	}

	/**
	 * @param priority the priority to set
	 */
	public void setPriority(int priority) {
		this.priority = priority;
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
