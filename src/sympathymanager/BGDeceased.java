/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package sympathymanager;


/**
 *
 * @author Charlie
 */
public class BGDeceased implements Deceased
{
    private String firstName;
    private String lastName;
    private String mInitial;
    private String town;
    private String URL;
    private String picture;
    private int likelihood;     

    public BGDeceased()
    {
        firstName = null;
        lastName = null;
        mInitial = null;
        town = null;
        likelihood = 0;

    }

    public BGDeceased( String name )
    {

        String delims = "[, ]";
        String[] allNames = name.split(delims);
        setLName(allNames[0]);
        setFname(allNames[2]);
        if ( allNames.length >= 4 )
        {
            setMI(allNames[3].substring(0,1));
        }
        else
        {
            setMI(" ");
        }
        town = null;
    }

    public BGDeceased( String first, String last, String MI )
    {
        firstName = first;
        lastName = last;
        mInitial = MI;


    }

    public void setLName( String name )
    {
        lastName = name;
    }

    public String getLName()
    {
        return lastName;
    }

    public void setFname( String name )
    {
//       String delims = "[, ]";
//       String[] allNames = name.split(delims);
//       String fName = allNames[2];
//       firstName = fName;
        firstName = name;
    }

    public String getFname()
    {
        return firstName;
    }

    public void setMI( String name )
    {
//       String delims = "[, .]";
//       String[] allNames = name.split(delims);
//       if(allNames.length >=4 )
//       {
//            String initial = allNames[3];
//            mInitial = initial;
//       }
//       else
//           mInitial = null;
        mInitial = name;

    }

    public String getMI()
    {
        return mInitial;
    }

    public void setTown( String towns )
    {
        town = towns;
    }

    public void setURL( String url )
    {
        URL = url;
    }

     public String getURL( )
    {
        return URL;
    }

    public String getTown()
    {
        return town;
    }

    public void setPicture( String pic )
    {
        picture = pic;
    }

     public String getPicture( )
    {
        return picture;
    }

    public void setLikelihood( int like )
    {
        likelihood = like;
    }
    public int getLikelihood()
    {
        return likelihood;
    }

    public void display()
    {
        System.out.printf("%s|%s|%s\n", firstName, mInitial, lastName);

    }

    public String toString()
    {
        return String.format("%s|%s|%s|\n", firstName, mInitial, lastName);
    }
}
