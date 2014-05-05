/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package sympathymanager;


import java.util.ArrayList;
import org.jsoup.Connection;

/**
 *
 * @author Charlie
 */
public interface ConnectToPaper
{
    public Connection jsoupConnection();

    public ArrayList<Deceased> getNames();

}
