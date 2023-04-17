import java.io.FileNotFoundException;
import java.io.IOException;

public class Main {
	
	public static void main(String[] args) throws FileNotFoundException, IOException {
		Blocks.readFile("ethereumP1data.csv");
		Blocks.sortBlocksByNumber();
		
		Window w = new Window();
	}

}
