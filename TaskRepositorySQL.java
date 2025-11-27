import java.io.FileInputStream;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;
import java.util.Properties;

public class TaskRepositorySQL {
	private static String URL;
	private static String USER;
	private static String PASSWORD;

	static {
	    try {
	        Properties props = new Properties();
	        FileInputStream fis = new FileInputStream("db.properties");
	        props.load(fis);

	        URL = props.getProperty("db.url");
	        USER = props.getProperty("db.user");
	        PASSWORD = props.getProperty("db.password");

	    } catch (Exception e) {
	        e.printStackTrace();
	    }
	}
    public static Connection getConnection() throws SQLException {
        // TODO: return connection here
    	return DriverManager.getConnection(URL, USER, PASSWORD);

    }
 
    public static int insertTask(Task t) {
        String sql = "INSERT INTO tasks (title, priority, date, status) VALUES (?, ?, ?, ?)";

        try (Connection conn = getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql, PreparedStatement.RETURN_GENERATED_KEYS)) {

            stmt.setString(1, t.getTitle());
            stmt.setInt(2, t.getPriority());
            stmt.setString(3, t.getDate());
            stmt.setBoolean(4, t.isStatus());

            stmt.executeUpdate();

            // Retrieve the auto-generated ID
            ResultSet rs = stmt.getGeneratedKeys();
            if (rs.next()) {
                return rs.getInt(1);   // return the new task's ID
            }

        } catch (Exception e) {
            e.printStackTrace();
        }

        return -1; // something failed
    }

    public static void deleteTaskById(int id) {
        String sql = "DELETE FROM tasks WHERE id = ?";

        try (Connection conn = getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setInt(1, id);
            stmt.executeUpdate();

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public static void updateTaskInDB(Task t) {
    	String sql = "UPDATE tasks SET title = ?, priority = ?, date = ?, status = ? WHERE id = ?";
    	try(Connection conn = getConnection();
    		PreparedStatement ps = conn.prepareStatement(sql)){
    		ps.setString(1, t.getTitle());
            ps.setInt(2, t.getPriority());
            ps.setString(3, t.getDate());
            ps.setBoolean(4, t.isStatus());
            ps.setInt(5, t.getID());

            ps.executeUpdate();

        } catch (SQLException e) {
            e.printStackTrace();
        }
    	}
    
    public static void markCompleteInDB(int id) {
        String sql = "UPDATE tasks SET status = 1 WHERE id = ?";

        try (Connection conn = getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setInt(1, id);
            stmt.executeUpdate();

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
    public static List<Task> getAllTasksFromDB() {
        List<Task> list = new ArrayList<>();

        String sql = "SELECT * FROM tasks ORDER BY id";

        try (Connection conn = getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql);
             ResultSet rs = stmt.executeQuery()) {

            while (rs.next()) {
                Task t = new Task(
                    rs.getInt("id"),
                    rs.getString("title"),
                    rs.getInt("priority"),
                    rs.getString("date"),
                    rs.getBoolean("status")
                );
                list.add(t);
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return list;
    }
    public static Task getTaskById(int id) {
    	String sql = "SELECT id, title, priority, date, status FROM tasks WHERE id = ?";
    	try(Connection conn = getConnection();
    		PreparedStatement stmt = conn.prepareStatement(sql)){
    		
    		stmt.setInt(1, id);
    		try(ResultSet rs = stmt.executeQuery()){
    			if(rs.next()) {
    				return new Task(
    				rs.getInt("id"),
    		        rs.getString("title"),
    		        rs.getInt("priority"),
    		        rs.getString("date"),
    		        rs.getBoolean("status")
    				);
    			}
    		}
    	} catch(SQLException e) {
    		e.printStackTrace();
    	}
    	return null;
    }
    private static List<Task> loadTasksFromQuery(String sql) {
        List<Task> list = new ArrayList<>();

        try (Connection conn = getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql);
             ResultSet rs = stmt.executeQuery()) {

            while (rs.next()) {
                Task t = new Task(
                    rs.getInt("id"),
                    rs.getString("title"),
                    rs.getInt("priority"),
                    rs.getString("date"),
                    rs.getBoolean("status")
                );
                list.add(t);
            }

        } catch (Exception e) {
            e.printStackTrace();
        }

        return list;
    }
    // SORT: by numeric priority (1,2,3)

    public static List<Task> getTasksSortedByPriority() {
        String sql = "SELECT id, title, priority, date, status FROM tasks ORDER BY priority ASC, id ASC";
        return loadTasksFromQuery(sql);
    }

    // SORT: by date as string
    public static List<Task> getTasksSortedByDate() {
        String sql = "SELECT id, title, priority, date, status FROM tasks ORDER BY date ASC, id ASC";
        return loadTasksFromQuery(sql);
    }

    // SORT: by status (incomplete first, then complete)
    public static List<Task> getTasksSortedByStatus() {
        String sql = "SELECT id, title, priority, date, status FROM tasks ORDER BY status ASC, id ASC";
        return loadTasksFromQuery(sql);
    }



   

}
