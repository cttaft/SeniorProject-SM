/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package sympathymanager;

/**
 *
 * @author Charlie
 */
public class MySqlTest {

    public static void main(String []args)
    {
        MySqlConnection msc = new MySqlConnection();
        try{
          System.out.println(msc.findMatches(new BGDeceased("Paul", "Baglio", "J")));

        }
        catch(Exception e )
        {
            System.out.println(e.toString());
        }

    }
}
