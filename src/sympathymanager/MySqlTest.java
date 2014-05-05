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
           System.out.println(msc.readDataBase());

        }
        catch(Exception e )
        {
            System.out.println(e.toString());
        }

    }
}
