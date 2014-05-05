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

public class WorkWithURLS
{
    private String pictureUrl;
    Element picture;
    private Document doc;

    public WorkWithURLS(String JsoupURL)
  {
       try
        {
            doc = Jsoup.connect(JsoupURL).get();
        }
        catch ( Exception e )
        {
            System.out.println("Error Connecting to Jsoup ");
        }

  }
    public String getPicture(  )
    {

        picture = doc.select("img[id = ctl00_MainContentPlaceholder_Photo").first();
        pictureUrl = picture.attr("src");

        return pictureUrl;

    }

    public String getTown(  )
    {
        String town = "";


        town = doc.select("span[itemprop=addressLocality").text();

        return town.split(" ")[0];
    }
}
