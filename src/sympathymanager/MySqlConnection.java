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


import javax.swing.JOptionPane;



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
    private Deceased dbDead;

    public MySqlConnection()
    {
        DBProperties properties = new DBProperties();
        try
        {
            //this will load the MySQL driver, each DB has its own driver
            Class.forName("com.mysql.jdbc.Driver");
            // setup the connection with the DB.
            String connectionString = "jdbc:mysql://" + properties.getDomain() + "/" + properties.getDatabase() + "?" +
              "user=" + properties.getUser() + "&password=" + properties.getPassword();
            connect = DriverManager
              .getConnection(connectionString);
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

        while ( resultSet.next() )
        {


            String firstName = resultSet.getString("FirstName");
            String MiddleInitial = resultSet.getString("MiddleInitial");
            String lastName = resultSet.getString("LastName");
            String ID = resultSet.getString("ID");


            Deceased tempDeceased = new BGDeceased(firstName, lastName, MiddleInitial, ID);

            dataDeathList.add(tempDeceased);


        }
    }
       private void writeConfirmedResultSet( ResultSet resultSet ) throws SQLException
    {

        while ( resultSet.next() )
        {


            String firstName = resultSet.getString("FirstName");
            String MiddleInitial = resultSet.getString("MiddleInitial");
            String lastName = resultSet.getString("LastName");
            String ID = resultSet.getString("ID");
            String date = resultSet.getString("ConfirmedDead.Date").substring(0, 10);
           String picUrl = resultSet.getString("ConfirmedDead.PictureUrl");

            Deceased tempDeceased = new BGDeceased(firstName, lastName, MiddleInitial, ID);
            if(date!=null)
             tempDeceased.setDate(date);
            if(picUrl!=null)
                tempDeceased.setPicture(picUrl);
            dataDeathList.add(tempDeceased);


        }
    }

    private Deceased extractDeceased( ResultSet resultSet )
    {

        try
        {

            String firstName = resultSet.getString("FirstName");
            String MiddleInitial = resultSet.getString("MiddleInitial");
            String lastName = resultSet.getString("LastName");
            String ID = resultSet.getString("ID");
            String town = resultSet.getString("Town");
            dbDead = new BGDeceased(firstName, lastName, MiddleInitial, ID);
            dbDead.setTown(town);
            return dbDead;
        }
        catch ( Exception e )
        {
            e.printStackTrace();
            return dbDead;
        }


    }

    public Deceased minMatch( Deceased person )
    {
        try
        {
            String nameSQL = "select * from DEATHCHECKER.MEMBERLIST Where LOWER(Trim(FirstName))  LIKE ? and LOWER(Trim(LastName)) LIKE ?";
            preparedStatement = connect.prepareStatement(nameSQL);
            preparedStatement.setString(1, person.getFname().toLowerCase());
            preparedStatement.setString(2, person.getLName().toLowerCase());
            resultSet = preparedStatement
              .executeQuery();
            if ( !resultSet.next() )
            {
                person.setLikelihood(0);
                return null;
            }
            else
            {
                person.setLikelihood(1);
                return extractDeceased(resultSet);
            }
        }
        catch ( Exception e )
        {
            e.toString();
            return null;
        }
        finally
        {
            close();
        }


    }

    public Deceased findBaseMatch( Deceased person )
    {
        try
        {
            String nameSQL = "select * from DEATHCHECKER.MEMBERLIST Where LOWER(Trim(FirstName))  LIKE ? and LOWER(Trim(LastName)) LIKE ?";
            preparedStatement = connect.prepareStatement(nameSQL);
            preparedStatement.setString(1, "%" + person.getFname().toLowerCase().trim() + "%");
            preparedStatement.setString(2, "%" + person.getLName().toLowerCase().trim() + "%");
            resultSet = preparedStatement
              .executeQuery();
            if ( !resultSet.next() )
            {
                person.setLikelihood(0);

            }
            else
            {
                person.setLikelihood(1);
                return extractDeceased(resultSet);

            }
        }
        catch ( Exception e )
        {
            e.printStackTrace();
        }
        return null;
    }

    public void findMatch( Deceased person )
    {
        try
        {
            String nameSQL = "select * from DEATHCHECKER.MEMBERLIST Where LOWER(Trim(FirstName))  LIKE ? and LOWER(Trim(LastName)) LIKE ?";
            preparedStatement = connect.prepareStatement(nameSQL);
            preparedStatement.setString(1, "%" + person.getFname().toLowerCase().trim() + "%");
            preparedStatement.setString(2, "%" + person.getLName().toLowerCase().trim() + "%");
            resultSet = preparedStatement
              .executeQuery();
            if ( !resultSet.next() )
            {
                person.setLikelihood(0);
                return;
            }
            else
            {
                person.setLikelihood(1);
                String middleNameSQL = "select * from DEATHCHECKER.MEMBERLIST Where LOWER(Trim(FirstName)) LIKE ? and LOWER(Trim(LastName)) COLLATE UTF8_GENERAL_CI LIKE ? and LOWER(Trim(MiddleInitial)) LIKE ?";
                preparedStatement = connect.prepareStatement(middleNameSQL);
                preparedStatement.setString(1, "%" + person.getFname().toLowerCase().trim() + "%");
                preparedStatement.setString(2, "%" + person.getLName().toLowerCase().trim() + "%");
                preparedStatement.setString(3, "%" + person.getMI().toLowerCase().trim() + "%");
                resultSet = preparedStatement
                  .executeQuery();
                if ( resultSet.next() )
                {
                    person.setLikelihood(2);
                    String townNameSQL = "select * from DEATHCHECKER.MEMBERLIST Where LOWER(Trim(FirstName)) LIKE ? and LOWER(Trim(LastName)) LIKE ? and Lower(Trim(middleInitial)) LIKE ? and LOWER(Trim(Town)) LIKE ?";
                    preparedStatement = connect.prepareStatement(townNameSQL);
                    preparedStatement.setString(1, "%" + person.getFname().toLowerCase().trim() + "%");
                    preparedStatement.setString(2, "%" + person.getLName().toLowerCase().trim() + "%");
                    preparedStatement.setString(3, "%" + person.getMI().toLowerCase().trim() + "%");
                    preparedStatement.setString(4, person.getTown().toLowerCase().trim());
                    resultSet = preparedStatement
                      .executeQuery();
                    if ( resultSet.next() )
                    {
                        person.setLikelihood(3);
                    }
                    return;
                }
                else
                {
                    String townNameSQL = "select * from DEATHCHECKER.MEMBERLIST Where LOWER(Trim(FirstName)) LIKE ? and LOWER(Trim(LastName)) LIKE ? and LOWER(Trim(Town)) LIKE ?";
                    preparedStatement = connect.prepareStatement(townNameSQL);
                    preparedStatement.setString(1, "%" + person.getFname().toLowerCase().trim() + "%");
                    preparedStatement.setString(2, "%" + person.getLName().toLowerCase().trim() + "%");
                    preparedStatement.setString(3, "%" + person.getTown().toLowerCase().trim() + "%");
                    resultSet = preparedStatement
                      .executeQuery();
                    if ( resultSet.next() )
                    {
                        person.setLikelihood(2);

                    }
                    return;
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

        return;


    }

    public boolean submit(Deceased dead, String picture)
    {
        try{
            String submitSQL = "insert into DEATHCHECKER.ConfirmedDead ( MemberId, PictureUrl)"
        + " values ( ?, ?)";
            preparedStatement = connect.prepareStatement(submitSQL);

            preparedStatement.setInt(1, Integer.parseInt(dead.getId()));
             preparedStatement.setString(2, picture);
            preparedStatement
                      .execute();

        }catch(Exception e)
        {
            e.printStackTrace();
            JOptionPane.showMessageDialog(null, e.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
            return false;
        }
        return true;
    }

     public boolean manualSubmit(String id)
    {
        try{
            String submitSQL = "insert into DEATHCHECKER.ConfirmedDead ( MemberId, PictureUrl)"
        + " values ( ?, ?)";
            preparedStatement = connect.prepareStatement(submitSQL);

            preparedStatement.setInt(1, Integer.parseInt(id));
             preparedStatement.setString(2, "http://ak-static.legacy.net/obituaries/images/premiumobit/premiumobit_candle.jpg?v=79.0.0.10421");
            preparedStatement
                      .execute();

        }catch(Exception e)
        {
            e.printStackTrace();
            JOptionPane.showMessageDialog(null, e.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
            return false;
        }
        return true;
    }

     public boolean revert(String id)
    {
        try{
            String deleteSQL = "delete from DEATHCHECKER.ConfirmedDead where memberid = ?"
       ;
            preparedStatement = connect.prepareStatement(deleteSQL);

            preparedStatement.setString(1, id);

            preparedStatement
                      .execute();

        }catch(Exception e)
        {
            e.printStackTrace();
            JOptionPane.showMessageDialog(null, e.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
            return false;
        }
        return true;
    }

    public ArrayList<Deceased> GetConfirmedList()
    {
        try{
         String nameSQL = "select * from DEATHCHECKER.MEMBERLIST, DEATHCHECKER.CONFIRMEDDEAD Where MEMBERLIST.ID = CONFIRMEDDEAD.MEMBERID Order By CONFIRMEDDEAD.Date Desc ";
            preparedStatement = connect.prepareStatement(nameSQL);

            resultSet = preparedStatement
              .executeQuery();
            writeConfirmedResultSet(resultSet);

        }catch(Exception e)
        {
            e.printStackTrace();
        }
        finally{
             return dataDeathList;
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
