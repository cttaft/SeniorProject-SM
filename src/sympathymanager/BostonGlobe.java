/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package sympathymanager;


import java.util.ArrayList;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.select.Elements;
import org.jsoup.nodes.Element;
import org.jsoup.*;

/**
 *
 * @author Charlie
 */
public class BostonGlobe implements ConnectToPaper
{
    private Connection connect;
    private Document doc;
    private Elements obitNames;
    private ArrayList<Deceased> DeceasedList = new ArrayList<Deceased>();
    private ArrayList<String> URLlist = new ArrayList<String>();
    private ArrayList<Deceased> dbList = new ArrayList<Deceased>();
    private MySqlConnection msc;
    public Connection jsoupConnection()
    {
        try
        {
            connect = Jsoup.connect(
              "http://www.legacy.com/obituaries/bostonglobe/obituary-browse.aspx?recentdate=0&type=1&view=1");
            connect.timeout(0);
            doc = connect.get();
        }
        catch ( Exception e )
        {
            System.out.println("Error Connecting to JSoup");
            e.printStackTrace();
        }

        return connect;
    }

    public ArrayList<Deceased> getNames()
    {
        msc = new MySqlConnection();
        obitNames = doc.select("span[class=Name");
        ArrayList<Deceased> MatchList = new ArrayList<>();

        for ( int count = 0; count < obitNames.size(); count++ )
        {
            DeceasedList.add(new BGDeceased(obitNames.get(count).text()));
        }
        getURLS();
        for(Deceased dead: DeceasedList)
        {
            Deceased dbDead = msc.findBaseMatch(dead);
            if(dead.getLikelihood() == 1)
            {
                MatchList.add(dead);
                dbList.add(dbDead);
            }
        }
        for( Deceased dead: MatchList)
        {
           // dead.setTown(getTowns(DeceasedList.indexOf(dead)));
            dead.setURL(URLlist.get(DeceasedList.indexOf(dead)));
            dead.setPicture(getPictures(dead.getURL()));

        }
        for(Deceased dead: MatchList)
        {
            msc.findMatch(dead);

        }

        return MatchList;
    }
    public ArrayList<Deceased> getDbList()
    {
        return dbList;
    }

    public ArrayList<String> getURLS()
    {
        Elements links = doc.select(
          "a[id^=ctl00_ctl00_ContentPlaceHolder1_ContentPlaceHolder1_uxNameResult");
        for ( Element link : links )
        {

            String href = link.attr("href");
            URLlist.add(href);
        }
        return URLlist;
    }

    public ArrayList<String> getPictures()
    {



        ArrayList<String> picURLlist = new ArrayList<String>();




        for ( int i = 0; i < URLlist.size(); i++ )
        {
             WorkWithURLS wwu = new WorkWithURLS(URLlist.get(i));
            picURLlist.add(wwu.getPicture());
        }
        return picURLlist;
    }

    public String getPictures( String url )
    {

        String picture;

        WorkWithURLS wwu = new WorkWithURLS(url);


        picture = wwu.getPicture();

        return picture;
    }

    public String getTowns( int index)
    {
        String town = "";
        WorkWithURLS wwu = new WorkWithURLS(URLlist.get(index));
        town = wwu.getTown();

        return town;

    }
}
