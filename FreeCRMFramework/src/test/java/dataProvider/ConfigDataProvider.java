package dataProvider;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.Properties;

public class ConfigDataProvider {
	
	static Properties pro;
	
	public ConfigDataProvider() throws IOException {
		
		File src=new File("./Configuration/config.properties");
		try {
			FileInputStream fis=new FileInputStream(src);
			
			pro=new Properties();
			pro.load(fis);
			
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.getMessage();
		}
	}
	
	public static String getApplicationURL() {
		String url=pro.getProperty("url");
		return url;
	}
	
	public String getChromePath() {
		String chromePath=pro.getProperty("chromePath");
		return chromePath;
	}
	
	public String getIEPath() {
		String iePath=pro.getProperty("iePath");
		return iePath;
	}
	
	public String getGechoPath() {
		String gechoPath=pro.getProperty("gechoPath");
		return gechoPath;
	}
	

}
