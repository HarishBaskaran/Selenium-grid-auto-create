package utils;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

public class Endpoints {
	
	protected static Properties prop = new Properties();
	static InputStream input = null;

	static int nodes = 0;

	public static int getNodesCount() {
		try {
			input = new FileInputStream("src\\main\\resources\\config.properties");
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		}
		try {
			prop.load(input);
		} catch (IOException e) {
			e.printStackTrace();
		}
		nodes = Integer.parseInt(prop.getProperty("nodesCount"));

		return nodes;
	}
}