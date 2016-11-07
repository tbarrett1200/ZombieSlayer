package main;

/*******************************************************************************
 * Thomas Barrett
 * Period 1
 * Mar 24, 2016
 * Description: Writes a world represented as a <String,String>HashMap to a file
 ******************************************************************************/

import java.io.File;
import java.io.FileNotFoundException;
import java.util.Formatter;
import java.util.HashMap;

public class WorldWriter {

	private Formatter write;
	private File file;

	public WorldWriter(String path) throws FileNotFoundException
	{
		file = new File("bin/"+path);
		write = new Formatter(file);
	}

	public void writeObject(HashMap<String,String> obj)
	{
		write.format("{\n");
		for (String key: obj.keySet())
		{
			write.format("\t%s : %s\n", key, obj.get(key));
		}
		write.format("}\n");
		write.flush();
	}
}
