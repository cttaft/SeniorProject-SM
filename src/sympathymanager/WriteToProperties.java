/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package sympathymanager;

/**
 *
 * @author Charlie
 */
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.util.Properties;
public class WriteToProperties {
public static void main(String[] args) {

	Properties prop = new Properties();
	OutputStream output = null;

	try {

		output = new FileOutputStream("config.properties");

		// set the properties value
        prop.setProperty("domain", "localhost");
		prop.setProperty("database", "deathchecker");
		prop.setProperty("dbuser", "cttaft");
		prop.setProperty("dbpassword", "thomas22");

		// save properties to project root folder
		prop.store(output, null);

	} catch (IOException io) {
		io.printStackTrace();
	} finally {
		if (output != null) {
			try {
				output.close();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}

	}
  }
}
