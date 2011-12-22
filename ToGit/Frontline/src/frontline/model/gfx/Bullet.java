/*
 * Copyright (c) 2011 BleepBloop Software, All Rights Reserved
 *
 * Unpublished copyright.  All rights reserved.
 */
package frontline.model.gfx;

import frontline.model.ActionScene;
import java.awt.Graphics2D;
import java.awt.geom.AffineTransform;

import javax.swing.ImageIcon;

import frontline.model.gfx.core.Sprite;
import frontline.model.util.Vector2;

/**
 * TODO: type comment.
 *
 * @version Aug 23, 2011, submitted by Nick Chavez
 */
public class Bullet extends Sprite{
    
    public static float speed = .5f;
    public Vector2 oldPos;
    public double angle;
    
    public Bullet (){
        super(new ImageIcon().getImage());
        singleImg = ActionScene.depot.characters.get("etc").get("bulletalt");
        oldPos = new Vector2();
    }
    
    public void update(long timePassed){
        oldPos = position;
        position.X += velocity.X * timePassed + (acceleration.X * accelTime.X * accelTime.X); //Updates position based 
        position.Y += velocity.Y * timePassed + (acceleration.Y * accelTime.Y * accelTime.Y);//on velocity, acceleration,
                                                                                             //And time.                                                                                      
        if(animation != null)//Checks to see if this Sprite has an animation to potentially update it.
            animation.update(timePassed); 
    }
    public void draw(Graphics2D g){
        AffineTransform affineTransform = new AffineTransform();
        affineTransform.translate(position.X, position.Y);
        affineTransform.rotate(Math.toRadians(angle - 90));   
        g.drawImage(singleImg, affineTransform, null);
    }
}
