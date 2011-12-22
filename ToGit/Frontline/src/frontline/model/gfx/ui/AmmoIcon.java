/*
 * Copyright (c) 2011 Morgan Stanley & Co. Incorporated, All Rights Reserved
 *
 * Unpublished copyright.  All rights reserved.  This material contains
 * proprietary information that shall be used or copied only within 
 * Morgan Stanley, except with written permission of Morgan Stanley.
 */
package frontline.model.gfx.ui;

import frontline.model.ActionScene;
import java.awt.Color;
import java.awt.Image;
import java.awt.Rectangle;

import frontline.model.gfx.core.Animation;
import frontline.model.gfx.core.Sprite;
import frontline.model.util.Services;

/**
 * TODO: type comment.
 *
 * @version $Revision:$, submitted by $Author:$
 * @author chavenic: Aug 26, 2011, 8:32:37 AM
 */
public class AmmoIcon extends Sprite{
    
    public enum State {
        FULL, EMPTY, FLASHING
    }
    public static Animation flashing;
    public static Image full;
    public static Image empty;
    public State state;
    
    public AmmoIcon(){
        if(flashing == null && full == null && empty == null){
            flashing = new Animation(ActionScene.depot.ui.get("ammoicon"), 2, 100);
            full = Services.makeColorTransparent(
                    Services.crop(
                            Services.toBufferedImage(flashing.sheet),
                            new Rectangle(
                                    0, 0, 
                                    flashing.sheet.getWidth(null)/2, 
                                    flashing.sheet.getHeight(null)
                            )
                    ),
                    Color.BLACK);
            empty = ActionScene.depot.ui.get("emptyammoicon");
        }
        fill();
    }
    
    public void flash(){
        animation = flashing;
        singleImg = null;
        state = State.FLASHING;
    }
    
    public void fill(){
        animation = null;
        singleImg = full;
        state = State.FULL;
    }
    
    public void empty(){
        animation = null;
        singleImg = empty;
        state = State.EMPTY;
    }
}
