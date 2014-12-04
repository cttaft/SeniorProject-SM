/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package sympathymanager;


import java.util.Date;


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
    private String id;
    private String date;

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

        String thelastName = name.split(", ")[0];
      //  String delims = "[, ]";
        String[] afterComma = name.split(", ")[1].split(" ");

        String[]town = name.split("- ");
        setLName(thelastName);
        setFname(afterComma[0]);
        if ( afterComma.length >= 3 )
        {
            if (afterComma[1].length() > 0)
            {
                setMI(afterComma[1].substring(0,1));
            }
        }
        else
        {
            setMI(" ");
        }
        if(town.length > 1)
         setTown(town[1]);
    }

    public BGDeceased( String first, String last, String MI )
    {
        firstName = first;
        lastName = last;
        mInitial = MI;


    }
     public BGDeceased( String first, String last, String MI, String id )
    {
        firstName = first;
        lastName = last;
        mInitial = MI;
        this.id = id;


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

    public void setId(String id)
    {
        this.id = id;
    }
    public String getId()
    {
        return id;
    }

    public void setDate(String date)
    {
        this.date = date;
    }
    public String getDate()
    {
        return this.date;
    }
    public String toString()
    {
        if(mInitial!=null && date!=null)
            return String.format("%s %s. %s\n", firstName, mInitial, lastName);
        else if(date==null && mInitial!=null)
            return String.format("%s %s. %s\n", firstName, mInitial, lastName);
        else if(date!=null && mInitial == null)
             return String.format("%s %s\n", firstName,  lastName);
        else
             return String.format("%s %s\n", firstName,  lastName);

    }
    public boolean equals(Object Dead)
    {
        if(((BGDeceased)Dead).getFname().trim().equalsIgnoreCase(this.firstName.trim())  &&
          ((BGDeceased)Dead).getLName().trim().equalsIgnoreCase(this.lastName.trim()) )
        {return true;}
        return false;
    }


}
