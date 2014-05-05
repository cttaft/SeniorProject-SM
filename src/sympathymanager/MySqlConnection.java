/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package sympathymanager;


import java.io.Closeable;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import java.util.ArrayList;

/**
 *
 * @author Charlie
 */
public class MySqlConnection
{
    private Connection connect = null;
    private Statement statement = null;
    private PreparedStatement preparedStatement = null;
    private ResultSet resultSet = null;
    ArrayList<Deceased> dataDeathList = new ArrayList<Deceased>();

    public ArrayList<Deceased> readDataBase() throws Exception
    {
        try
        {
            // this will load the MySQL driver, each DB has its own driver
            Class.forName("com.mysql.jdbc.Driver");
            // setup the connection with the DB.
            connect = DriverManager
              .getConnection("jdbc:mysql://localhost/deathchecker?" + "user=cttaft&password=thomas22");
            statement = connect.createStatement();
            resultSet = statement
              .executeQuery("select * from DEATHCHECKER.MEMBERLIST");
            writeResultSet(resultSet);
            return dataDeathList;
        }
        catch ( Exception e )
        {
            throw e;
        }
        finally
        {
            close();
        }
    }

    private void writeResultSet( ResultSet resultSet ) throws SQLException
    {
        // resultSet is initialised before the first data set
        while ( resultSet.next() )
        {
            // it is possible to get the columns via name
            // also possible to get the columns via the column number
            // which starts at 1
            // e.g., resultSet.getSTring(2);

            String firstName = resultSet.getString("FirstName");
            String MiddleInitial = resultSet.getString("MiddleInitial");
            String lastName = resultSet.getString("LastName");
            int ID = resultSet.getInt("ID");
           dataDeathList.add( new BGDeceased(firstName, lastName, MiddleInitial ));

        }
    }

    private void close()
    {
        try
        {
            resultSet.close();
            statement.close();
            connect.close();
        }
        catch ( Exception e )
        {
        }
    }
}
