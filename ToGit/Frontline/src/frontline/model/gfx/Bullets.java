/*
 * Copyright (c) 2011 BleepBloop Software, All Rights Reserved
 *
 * Unpublished copyright.  All rights reserved.
 */
package frontline.model.gfx;

import java.awt.Graphics2D;
import java.util.ArrayList;
import java.util.Iterator;

import frontline.model.gfx.core.Component;
import frontline.model.util.Services;
import frontline.model.util.Vector2;

/**
 * TODO: type comment.
 *
 * @version Aug 17, 2011, submitted by Nick Chavez
 */
public class Bullets implements Component{

    public ArrayList<Bullet> liveBullets;
    
    public Bullets(){
        liveBullets = new ArrayList<Bullet>();
    }
    
    @Override
    public synchronized void draw(Graphics2D g) {
        try{
        for(Bullet bill : liveBullets){
            try{
            bill.draw(g);
            }catch(Exception e){}
        }
        }catch(Exception e){}
    }

    @Override
    public synchronized void update(long timePassed) {
        for(Bullet bill : liveBullets){
            bill.update(timePassed);
        } 
        Iterator<Bullet> itr = liveBullets.iterator();
        for(int i = 0; i < liveBullets.size(); i++){
            if(itr.hasNext()){
                if(isOutOfBounds(itr.next())){
                    itr.remove();
                }
            }
        }
    }

    @Override
    public void loadResources() {
        // TODO Auto-generated method stub
        
    }
    
    public void add(Vector2 origin, Vector2 mousePos, int accuracy){
        Bullet bill = new Bullet();
        bill.position = origin;
        double angle = Services.getAngle(origin.X, origin.Y, mousePos.X, mousePos.Y);
        angle += 90;
        angle = Services.randomize(angle, accuracy);
        bill.velocity.X = -(float) (Math.cos(Math.toRadians(angle)) * Bullet.speed);
        bill.velocity.Y = -(float) (Math.sin(Math.toRadians(angle)) * Bullet.speed);
        bill.angle = angle;
        bill.update(31);
        liveBullets.add(bill);
    }
    
    public boolean isOutOfBounds(Bullet bill){
        return bill.position.X > 800 + Services.leftInset + Services.rightInset || bill.position.X < Services.leftInset ||
            bill.position.Y < Services.topInset || bill.position.Y > 600 - Services.topInset - Services.bottomInset - 10;
        }
}
