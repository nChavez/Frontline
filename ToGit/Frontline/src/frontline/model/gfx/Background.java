/*
 * Copyright (c) 2011 BleepBloop Software, All Rights Reserved
 *
 * Unpublished copyright.  All rights reserved.
 */
package frontline.model.gfx;

import java.awt.Graphics2D;

import frontline.model.gfx.core.Component;
import frontline.model.gfx.core.Sprite;
import frontline.model.util.Services;

/**
 * TODO: type comment.
 *
 * @version Aug 17, 2011, submitted by Nick Chavez
 */
public class Background extends Sprite implements Component {

    public Background(){
        super();
        loadResources();
    }
    
    @Override
    public void draw(Graphics2D g) {
       /*g.drawImage(singleImg, 
               Services.leftInset, Services.topInset, 
               800 + Services.leftInset, 600 + Services.topInset,
               0, 0,
               getWidth(), getHeight(),
               null);*/
    }

    @Override
    public void update(long timePassed) {
        
    }

    @Override
    public void loadResources() {
        //singleImg = Services.loadImage(getClass().getResource("../../../Resources/Images/Etc/bg.png"));
    }

}
