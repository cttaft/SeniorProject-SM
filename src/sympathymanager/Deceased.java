/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package sympathymanager;

/**
 *
 * @author Charlie
 */
public interface Deceased {

       public void setFname( String first );
       public String getFname();
       public void setMI( String mI);
       public String getMI();
       public void setLName( String last );
       public String getLName();
       public void setTown(String towns);
       public String getTown();
       public void display();
       public void setURL(String url);
       public String getURL();
        public void setPicture(String url);
       public String getPicture();
       public void setLikelihood(int like);
       public int getLikelihood();

}
