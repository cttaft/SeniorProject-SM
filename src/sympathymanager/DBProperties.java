/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package sympathymanager;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;
/**
 *
 * @author Charlie
 */
public class DBProperties {
    private String domain;
    private String database;
    private String user;
    private String password;
    private String url;

    public DBProperties()
    {
        Properties prop = new Properties();
        String propFileName = "config.properties";

	try {




		InputStream inputStream = new FileInputStream(propFileName);
		prop.load(inputStream);
		if (inputStream == null) {
			throw new FileNotFoundException("property file '" + propFileName + "' not found in the classpath");
		}

		// set the properties value
        domain =  prop.getProperty("domain");
		 database =  prop.getProperty("database");
		 user =  prop.getProperty("dbuser");
		 password =  prop.getProperty("dbpassword");
         url = prop.getProperty("LegacyUrl");

		// save properties to project root folder

	} catch (IOException io) {
		io.printStackTrace();


	}
    }
    public String getDomain()
    {
       return domain;
    }

    public String getDatabase()
    {
        return database;
    }

    public String getUser()
    {
        return user;
    }
    public String getPassword()
    {
        return password;
    }
    public String getUrl()
    {
        return url;
    }

}
