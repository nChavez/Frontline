/*
 * Copyright (c) 2011 BleepBloop Software, All Rights Reserved
 *
 * Unpublished copyright.  All rights reserved.
 */
package frontline.model.gfx;

import java.awt.Image;
import java.util.HashMap;

import frontline.model.gfx.core.Animation;
import frontline.model.util.Directions;

/**
 * TODO: type comment.
 *
 * @version Aug 17, 2011, submitted by Nick Chavez
 */
public class Legs{
    
    public int direction;
    public int ubDirection;
    public Image sheet;
    public Animation animation;
    public HashMap<Integer, Animation> animations;
    
    public Legs(HashMap<Integer, Animation> animations){
        this.animations = animations;
        animation = animations.get(Directions.NORTH);
        ubDirection = Directions.SOUTH;
    }
    
    public void update(long timePassed){
        if(animation != null)//Checks to see if this Sprite has an animation to potentially update it.
            animation.update(timePassed); 
        }
        /**
         * So, legDirection can be one of 8 directions and ub can be one of 16 directions on same scale.
         * If the difference between ubDirection and legDirection is greater than 4, use the reverse, which would be:
         * if(direction >=8) newDirection = direction - 8 and if (direction < 8) newDirection = direction + 8
         */
    
    public void setDirection(int direction){
        this.direction = direction;
        if(Math.abs(ubDirection - direction) > 4){
            if(direction >= 8){ direction -= 8;}
            else{ direction += 8;}
        }
        animation = animations.get(direction); 
    }
    public int getWidth(){
        return animation.sheet.getWidth(null)/4;
    }
    public int getHeight(){
        return animation.sheet.getHeight(null)/8;
    }
    
    

}
