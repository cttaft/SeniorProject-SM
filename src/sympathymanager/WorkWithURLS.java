/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package sympathymanager;


/**
 *
 * @author Charlie
 */
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import java.util.regex.*;

import org.jsoup.Connection;
import org.jsoup.select.Elements;

public class WorkWithURLS
{
    private String pictureUrl;
    Element picture;
    private Document doc;
    private Connection connection;

    public WorkWithURLS( String JsoupURL )
    {
        try
        {

            connection = Jsoup.connect(JsoupURL);
            connection.timeout(0);
            doc = connection.get();
        }
        catch ( Exception e )
        {
            System.out.println("Error Connecting to Jsoup ");
            e.printStackTrace();
        }

    }

    public String getPicture()
    {

        picture = doc.select("img[id = ctl00_MainContentPlaceholder_Photo").first();
        if ( picture != null )
        {
            pictureUrl = picture.attr("src");
        }

        return pictureUrl;

    }

    public String getTown()
    {
        String town = "";

        Elements townElement = doc.select("span[itemprop=addressLocality");
        if ( townElement != null )
        {
            town = townElement.text();

            return town.split(" ")[0];
        }
        return "NotSupported";
    }
}
