package mustache;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.Scanner;

abstract class Highscore {
	
	public static int[] read() {
		Scanner scanner = null;
		File fr = new File("res/Highscore.txt");
		
		try {
			scanner = new Scanner(new FileReader(fr));
			
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
	
	public static void write(int hS, int hW) {
		PrintWriter ecrivain;

	    try {
			ecrivain =  new PrintWriter(new FileWriter("res/Highscore.txt"));
			ecrivain.println(hS + "," + hW);
		    ecrivain.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
	    
	}
}
