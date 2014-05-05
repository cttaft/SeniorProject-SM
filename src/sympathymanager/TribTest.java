/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package sympathymanager;


import java.util.regex.Matcher;
import java.util.regex.Pattern;
import org.jsoup.Connection;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.select.Elements;

/**
 *
 * @author Charlie
 */
public class TribTest {
    public static void main(String []args)
    {
        Document doc = null;
       try
        {
            Connection connect = Jsoup.connect(
              "http://www.tributes.com/search/obituaries/?solr=&first=&last=&city=&state=MA&search_type=24&dod=&keywords=");
            doc = connect.get();
        }
        catch ( Exception e )
        {
            System.out.println("Error Connecting to JSoup");
        }

        Elements obitNames = doc.select("div[class=searchResultContent");
        String results = obitNames.get(4).text();
         Pattern findAge = Pattern.
          compile("Age(\\s)", Pattern.CASE_INSENSITIVE);
         Matcher age = findAge.matcher(results);
          while ( age.find() )
        {
            System.out.println(age.group());
        }
          System.out.println(results);

    }
}
