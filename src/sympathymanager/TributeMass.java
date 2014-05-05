/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package sympathymanager;


import java.util.ArrayList;

import org.jsoup.Connection;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

/**
 *
 * @author Charlie
 */
public class TributeMass {


  private Connection connect;
    private Document doc;
    private Elements obitNames;
    private ArrayList<Deceased> DeceasedList = new ArrayList<Deceased>();
    ArrayList<String> URLlist = new ArrayList<String>();

    public Connection jsoupConnection()
    {
        try
        {
            connect = Jsoup.connect(
              "http://www.tributes.com/search/obituaries/?solr=&first=&last=&city=&state=MA&search_type=24&dod=&keywords=");
            doc = connect.get();
        }
        catch ( Exception e )
        {
            System.out.println("Error Connecting to JSoup");
        }

        return connect;
    }

    public ArrayList<Deceased> getNames()
    {
        obitNames = doc.select("span[class=actionText");

        for ( int count = 0; count < obitNames.size(); count++ )
        {
            DeceasedList.add(new BGDeceased(obitNames.get(count).text()));
        }


        return DeceasedList;
    }
//
//    public ArrayList<String> getURLS()
//    {
//        Elements links = doc.select(
//          "a[id^=ctl00_ctl00_ContentPlaceHolder1_ContentPlaceHolder1_uxNameResult");
//        for ( Element link : links )
//        {
//
//            String href = link.attr("href");
//            URLlist.add(href);
//        }
//        return URLlist;
//    }
//
//    public ArrayList<String> getPictures()
//    {
//
//
//
//        ArrayList<String> picURLlist = new ArrayList<String>();
//
//
//
//
//        for ( int i = 0; i < URLlist.size(); i++ )
//        {
//             WorkWithURLS wwu = new WorkWithURLS(URLlist.get(i));
//            picURLlist.add(wwu.getPicture());
//        }
//        return picURLlist;
//    }
//
//    public String getPictures( int index )
//    {
//
//        String picture;
//
//        WorkWithURLS wwu = new WorkWithURLS(URLlist.get(index));
//
//
//        picture = wwu.getPicture();
//
//        return picture;
//    }
//
//    public String getTowns( int index)
//    {
//        String town = "";
//        WorkWithURLS wwu = new WorkWithURLS(URLlist.get(index));
//        town = wwu.getTown();
//
//        return town;
//
//    }
}
