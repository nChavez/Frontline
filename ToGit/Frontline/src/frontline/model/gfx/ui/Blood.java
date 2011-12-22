/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package frontline.model.gfx.ui;

import frontline.model.ActionScene;
import frontline.model.gfx.core.Component;
import frontline.model.gfx.core.Sprite;
import frontline.model.util.Services;
import java.awt.Graphics2D;
import java.awt.geom.AffineTransform;

/** Copyright 2011, Bleep Bloop Software.
 *  Unpublished Copyright, All Rights Reserved
 *
 */
/**
 *
 * @author Nick
 */
public class Blood extends Sprite implements Component {

    public final int timeToStay = 300;
    
    public boolean visible = false;
    public long timeOn = 0;
    
    public Blood(){
        loadResources();
        position.X = Services.leftInset;
        position.Y = Services.topInset;
    }
    
    public void update(long timePassed){
        super.update(timePassed);
        if(timeOn >= timeToStay){
            timeOn = 0;
            visible = false;
        }
        if(visible)
        timeOn += timePassed;
    
    }
    
    public void draw(Graphics2D g){
        if(visible){
            super.draw(g);
        }
    }
    
    @Override
    public void loadResources() {
        singleImg = ActionScene.depot.ui.get("bloodMask");
    }
    
}
