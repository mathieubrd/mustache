package mustache;

import java.io.FileNotFoundException;
import java.io.FileReader;
import java.util.Scanner;

abstract class Highscore {
	
	public static int[] read() {
		Scanner scanner = null;
		try {
			scanner = new Scanner(new FileReader("res/Highscore.txt"));
			
			String str = null;
			while (scanner.hasNextLine()) {
				str = scanner.nextLine();
				String[] tabStr = str.split(",");
				int[] tabInt = new int[2];
				tabInt[0] = Integer.parseInt(tabStr[0]);
				tabInt[1] = Integer.parseInt(tabStr[1]);
				return tabInt;
			}
			scanner.close();
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		}
		
		return null;
	}
	
	public static void write() {
		
	}
}
