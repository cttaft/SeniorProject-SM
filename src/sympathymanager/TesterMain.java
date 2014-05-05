/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package sympathymanager;

/**
 *
 * @author Charlie
 */
public class TesterMain {
    public static void main(String []args)
    {
        BostonGlobe BG = new BostonGlobe();
        BG.jsoupConnection();
        BG.getURLS();
        
    }

}
