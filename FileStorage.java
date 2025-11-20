import java.io.*;
import java.nio.file.*;
import java.util.*;
public class FileStorage {
	private static final String PATH = "data/tasks.txt";
	
	public static List <String> loadLines() {
		try {
			Path p = Paths.get(PATH);
			if(!Files.exists(p)) return new ArrayList<>();
			return Files.readAllLines(p);
		} catch(IOException e) {
			System.out.println("Error Loading File: "+e.getMessage());
			return new ArrayList<>();
		}
	}
	
	public static void saveLines(List<String> lines) {
		try {
			Files.createDirectories(Paths.get("data"));
			Files.write(Paths.get(PATH), lines);
		} catch (IOException e) {
			System.out.println("Error saving file: "+ e.getMessage());
		}
	}
}
