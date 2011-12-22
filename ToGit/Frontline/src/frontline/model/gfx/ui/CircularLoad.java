/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package frontline.model.gfx.ui;

import frontline.model.gfx.core.Sprite;
import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.geom.AffineTransform;

/** Copyright 2011, Bleep Bloop Software.
 *  Unpublished Copyright, All Rights Reserved
 *
 */
/**
 *
 * @author Nick
 */
public class CircularLoad extends Sprite{
   
    public int angle;
    public float speed; //Degrees/ms
    public boolean visible;
    
    public CircularLoad(Image singleImg){
        super(singleImg);
        angle = 0;
        speed = .5f;
        visible = false;
    }
    
    public void update(long timePassed){
        super.update(timePassed);
        angle+= speed * timePassed;
        if(angle >= 360) angle -= 360;
    }
    
    public void draw(Graphics2D g){
        AffineTransform affineTransform = new AffineTransform();
        affineTransform.translate(position.X, position.Y);
        affineTransform.rotate(Math.toRadians(angle), 8.5, 8.5);
        g.drawImage(singleImg, affineTransform, null);
    }
}
