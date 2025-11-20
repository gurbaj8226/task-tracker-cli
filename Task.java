public class Task {
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
