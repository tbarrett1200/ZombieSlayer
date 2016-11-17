package main;

/*******************************************************************************
 * Thomas Barrett
 * Period 1
 * Mar 24, 2016
 * Description: Reads a world from file into a <String,String>HashMap
 ******************************************************************************/

import java.io.File;
import java.io.FileNotFoundException;
import java.util.HashMap;
import java.util.Scanner;

public class WorldReader{

	private Scanner scan;
	private File file;

	public WorldReader(String path) throws FileNotFoundException
	{
		file = new File(path);
		scan = new Scanner(file);
	}

	public boolean hasNext()
	{
		if (scan.hasNext("\\{"))
			return true;
		return false;
	}
	public HashMap<String,String> nextObject()
	{
		HashMap<String,String> map = new HashMap<String,String>();


		if (scan.hasNext("\\{"))
		{
			scan.next("\\{");
			scan.nextLine();
			while (scan.hasNext("[a-zA-Z\\-]+"))
			{
				String a=scan.next("\\w+");
				scan.next(":");
				String b=scan.next("(\\w|-)+");
				scan.nextLine();
				map.put(a, b);
			}
			scan.next("}");
			return map;
			}
		else return null;

	}
}
