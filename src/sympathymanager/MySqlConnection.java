/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package sympathymanager;


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
    private ArrayList<Deceased> dataDeathList = new ArrayList<Deceased>();
    private ArrayList<Deceased> matchingDBList = new ArrayList<>();

    public MySqlConnection()
    {
        try
        {
            //this will load the MySQL driver, each DB has its own driver
            Class.forName("com.mysql.jdbc.Driver");
            // setup the connection with the DB.
            connect = DriverManager
              .getConnection("jdbc:mysql://localhost/deathchecker?" +
              "user=cttaft&password=thomas22");
        }
        catch ( Exception e )
        {
            e.toString();
        }
    }

    public ArrayList<Deceased> readDataBase() throws Exception
    {
        try
        {

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
            dataDeathList.add(new BGDeceased(firstName, lastName, MiddleInitial));

        }
    }

    public void minMatch( Deceased person )
    {
        try
        {
            String nameSQL = "select * from DEATHCHECKER.MEMBERLIST Where LOWER(FirstName)  LIKE ? and LOWER(LastName) LIKE ?";
            preparedStatement = connect.prepareStatement(nameSQL);
            preparedStatement.setString(1, person.getFname().toLowerCase());
            preparedStatement.setString(2, person.getLName().toLowerCase());
            resultSet = preparedStatement
              .executeQuery();
            if ( !resultSet.next() )
            {
                person.setLikelihood(0);

            }
            else
            {
                person.setLikelihood(1);
            }
        }
        catch ( Exception e )
        {
            e.toString();
        }
        finally
        {
            close();
        }


    }

    public Deceased findBaseMatch(Deceased person)
    {
          try
        {
            String nameSQL = "select * from DEATHCHECKER.MEMBERLIST Where LOWER(FirstName)  LIKE ? and LOWER(LastName) LIKE ?";
            preparedStatement = connect.prepareStatement(nameSQL);
            preparedStatement.setString(1, person.getFname().toLowerCase());
            preparedStatement.setString(2, person.getLName().toLowerCase());
            resultSet = preparedStatement
              .executeQuery();
            if ( !resultSet.next() )
            {
                person.setLikelihood(0);

            }
            else
            {
                person.setLikelihood(1);

            }
        }catch(Exception e)
        {
            e.printStackTrace();
        }
         return person;
    }

    public Deceased findMatch( Deceased person )
    {
        try
        {
            String nameSQL = "select * from DEATHCHECKER.MEMBERLIST Where LOWER(FirstName)  LIKE ? and LOWER(LastName) LIKE ?";
            preparedStatement = connect.prepareStatement(nameSQL);
            preparedStatement.setString(1, person.getFname().toLowerCase());
            preparedStatement.setString(2, person.getLName().toLowerCase());
            resultSet = preparedStatement
              .executeQuery();
            if ( !resultSet.next() )
            {
                person.setLikelihood(0);
                return person;
            }
            else
            {
                person.setLikelihood(1);
                String middleNameSQL = "select * from DEATHCHECKER.MEMBERLIST Where LOWER(FirstName) LIKE ? and LOWER(LastName) COLLATE UTF8_GENERAL_CI LIKE ? and LOWER(MiddleInitial) LIKE ?";
                preparedStatement = connect.prepareStatement(middleNameSQL);
                preparedStatement.setString(1, person.getFname().toLowerCase());
                preparedStatement.setString(2, person.getLName().toLowerCase());
                preparedStatement.setString(3, person.getMI().toLowerCase());
                resultSet = preparedStatement
                  .executeQuery();
                if ( resultSet.next() )
                {
                    person.setLikelihood(2);
                    String townNameSQL = "select * from DEATHCHECKER.MEMBERLIST Where LOWER(FirstName) LIKE ? and LOWER(LastName) LIKE ? and Lower(middleInitial) LIKE ? and LOWER(Town) LIKE ?";
                    preparedStatement = connect.prepareStatement(townNameSQL);
                    preparedStatement.setString(1, person.getFname().toLowerCase());
                    preparedStatement.setString(2, person.getLName().toLowerCase());
                    preparedStatement.setString(3, person.getMI().toLowerCase());
                    preparedStatement.setString(4, person.getTown().toLowerCase());
                    resultSet = preparedStatement
                      .executeQuery();
                    if ( resultSet.next() )
                    {
                        person.setLikelihood(3);
                    }
                    return person;
                }
                else
                {
                    String townNameSQL = "select * from DEATHCHECKER.MEMBERLIST Where LOWER(FirstName) LIKE ? and LOWER(LastName) LIKE ? and LOWER(Town) LIKE ?";
                    preparedStatement = connect.prepareStatement(townNameSQL);
                    preparedStatement.setString(1, person.getFname().toLowerCase());
                    preparedStatement.setString(2, person.getLName().toLowerCase());
                    preparedStatement.setString(3, person.getTown().toLowerCase());
                    resultSet = preparedStatement
                      .executeQuery();
                    if ( resultSet.next() )
                    {
                        person.setLikelihood(2);

                    }
                    return person;
                }

            }
        }
        catch ( Exception e )
        {
            e.toString();
        }
        finally
        {
            close();
        }

        return person;


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
