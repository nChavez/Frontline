/*
 * Copyright (c) 2011 BleepBloop Software, All Rights Reserved
 *
 * Unpublished copyright.  All rights reserved.
 */
package frontline.model.gfx;

import java.awt.Image;
import java.awt.Rectangle;
import java.util.HashMap;
import java.awt.Graphics2D;

import frontline.model.gfx.core.Sprite;
import frontline.model.util.Directions;
import frontline.model.util.Vector2;

/**
 * TODO: type comment.
 *
 * @version Aug 23, 2011, submitted by Nick Chavez
 */
public class MouseFollower {
    public int direction;
    public double angle;
    public Image sheet;
    public HashMap<Integer, Sprite> spriteset;
    public Sprite sprite;
    
    public MouseFollower(HashMap<Integer, Sprite> spriteset){
        this.spriteset = spriteset;
        sprite = spriteset.get(Directions.NORTH);
    }
    
    public void update (long timePassed){
    }
   public void setDirection(double angle){
       this.angle = angle;
       direction = (int) ((angle/22.5));
       
       if(angle > 11.25 && angle <= 33.75){
           direction = Directions.NORTHNORTHEAST;
           
       }else if(angle > 33.75 && angle <= 56.25){
           direction = Directions.NORTHEAST;  
       }else if(angle > 56.25 && angle <= 78.75){
           direction = Directions.EASTNORTHEAST;
       }else if(angle > 78.75 && angle <= 101.25){
           direction = Directions.EAST;
       }else if(angle > 101.25 && angle <= 123.75){
           direction = Directions.EASTSOUTHEAST;
       }else if(angle > 123.75 && angle <= 146.25){
           direction = Directions.SOUTHEAST;
       }else if(angle > 146.25 && angle <= 168.75){
           direction = Directions.SOUTHSOUTHEAST;
       }else if(angle > 168.75 && angle <= 191.25){
           direction = Directions.SOUTH;
       }else if(angle > 191.25 && angle <= 213.75){
           direction = Directions.SOUTHSOUTHWEST;
       }else if(angle > 213.75 && angle <= 236.25){
           direction = Directions.SOUTHWEST;
       }else if(angle > 236.25 && angle <= 258.75){
           direction = Directions.WESTSOUTHWEST;
       }else if(angle > 258.75 && angle <= 281.25){
           direction = Directions.WEST;
       }else if(angle > 281.25 && angle <= 303.75){
           direction = Directions.WESTNORTHWEST;
       }else if(angle > 303.75 && angle <= 326.25){
           direction = Directions.NORTHWEST;
       }else if(angle > 326.25 && angle <= 348.75){
           direction = Directions.NORTHNORTHWEST;
       }else if(angle > 348.75 || angle <= 11.25){
           direction = Directions.NORTH;
       }
       sprite = spriteset.get(direction);
    }
    public int getWidth(){
        return sheet.getWidth(null);
    }
    public int getHeight(){
        return sheet.getHeight(null)/16;
    }
    
    public Rectangle getRectangle(){
        Rectangle ans = new Rectangle(0, (sheet.getHeight(null)/16) * direction, 
                    sheet.getWidth(null), sheet.getHeight(null)/16);
        return ans;
    }
    
    public void draw(Graphics2D g, Vector2 position){
        g.drawImage(sheet, 
                (int)position.X, (int)position.Y,
                (int)(position.X + getWidth() ), (int)(position.Y + getHeight()),
                getRectangle().x, getRectangle().y, 
                getRectangle().x + getRectangle().width,
                getRectangle().y + getRectangle().height,
                null
        );
    }
}
