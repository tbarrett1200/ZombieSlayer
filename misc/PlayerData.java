package misc;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.PrintWriter;
import java.util.Scanner;

public class PlayerData {
	
	public int coins;
	public String gun;

	private PrintWriter write;
	private Scanner read;
	private File file;
	
	
	public PlayerData(String user)
	{
		file = new File("bin/users/user-"+user+".txt");
		
		try {
			read = new Scanner(file);
			coins = read.nextInt();
			read.nextLine();
			gun=read.nextLine();
			read.close();
			read=null;
		} catch (FileNotFoundException e) {
			coins=0;
			gun="basic";
		}	
	}
	
	public void exportData()
	{
		try {
			write = new PrintWriter(file);
			write.println(coins);
			write.println(gun);
			write.close();
			write=null;
		} catch (FileNotFoundException e) {}
		
		
	}

}
