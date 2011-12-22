/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package frontline.Launches;

import frontline.DeathMatch;
import frontline.view.ViewCore.Display;
import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author Consuelo
 */
public class LaunchWindowed {
    public static void main(String [] args){
        try {
            DeathMatch dm = new DeathMatch(Display.WINDOW, "-a");
            dm.run();
        } catch (IOException ex) {
            Logger.getLogger(LaunchWindowed.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
}
