/*
 * Copyright (c) 2011 BleepBloop Software, All Rights Reserved
 *
 * Unpublished copyright.  All rights reserved.
 */
package frontline.model.gfx.core;

import java.awt.Graphics2D;
import java.awt.Image;
import java.util.HashMap;

import frontline.model.util.Directions;
import frontline.model.util.Vector2;

/**
 * TODO: type comment.
 *
 * @version Aug 17, 2011, submitted by Nick Chavez
 */
public class Sprite{
    
    public Animation animation; //The current (or only) animation the sprite has.
    public HashMap<String, Animation> animations; //In a more complex game, a sprite could have more than one Animation.
    public Vector2 position; //A vector for the coordinates in the window.
    public Vector2 velocity; //Velocity, not speed. 
    public Vector2 acceleration; //Sounds groovy, but hard to use. Imo.
    public String currentAnimation; //This would hold a key to that HashMap up there.
    public Image singleImg; //If the sprite does not have an animation, it is just one picture. This holds that.
    public Vector2 accelTime; //Time since acceleration started.
    
    //Kind of obvious.
    public Sprite(){
        position = new Vector2(0,0);
        velocity = new Vector2(0,0);
        acceleration = new Vector2(0,0);
        accelTime = new Vector2(0,0);
    }
    
    //Still pretty ovious.
    public Sprite(Animation animation) {
        this.animation = animation;
        position = new Vector2(0,0);
        velocity = new Vector2(0,0);
        acceleration = new Vector2(0,0);
        accelTime = new Vector2(0,0);
    }
    
    //Even more so.
    public Sprite (Image singleImg){
        this.singleImg = singleImg;
        position = new Vector2(0,0);
        velocity = new Vector2(0,0);
        acceleration = new Vector2(0,0);
        accelTime = new Vector2(0,0);
    }
    
    //Takes a set of animations.
    public Sprite(HashMap<String, Animation> animations){
        this.animations = animations;
        animation = animations.get(Directions.SOUTH);
        
        position = new Vector2(0,0);
        velocity = new Vector2(0,0);
        acceleration = new Vector2(0,0);
        accelTime = new Vector2(0,0);
    }

    //Updates. No shit. Tell me more.
    public void update(long timePassed) {
        //Real acceleration. This would work if something never stopped accelerating.
        //I just put it in here so I wouldn't have to google it later.
        //velocity.x += acceleration.x * accelTime.x;
        //velocity.y += acceleration.y * accelTime.y;+
        position.X += velocity.X * timePassed + (acceleration.X * accelTime.X * accelTime.X); //Updates position based 
        position.Y += velocity.Y * timePassed + (acceleration.Y * accelTime.Y * accelTime.Y);//on velocity, acceleration,
                                                                                             //And time.                                                                                      
        if(animation != null)//Checks to see if this Sprite has an animation to potentially update it.
            animation.update(timePassed); 
    }
    
    public int getHeight(){//Gets height, which never really changes.
        if(animation != null)
            return animation.sheet.getHeight(null); //Returns the height of the spritesheet in animation.
        return singleImg.getHeight(null);//Returns the height of the lonely image.
    }
    
    public int getWidth(){//Gets width, which changes in some cases, like when there are multiple animations.
        if(animation != null)
            return animation.frameWidth; //Returns the width of the spritesheet in animation.
        return singleImg.getWidth(null); //Returns the width of the lonely image.
    }
    
    //Draws itself onto screen.
    public void draw(Graphics2D g){
        if(animation != null){//Draws according to Animation's rectangle.
            g.drawImage(animation.sheet, 
                    (int)position.X, (int)position.Y,
                    (int)position.X + animation.frameWidth, (int)position.Y + getHeight(),
                    animation.getRectangle().x, animation.getRectangle().y, 
                    animation.getRectangle().x + animation.frameWidth, animation.getRectangle().y + getHeight(),
                    null
            ); //Basically it takes an image, a destination rectangle, a source rectangle and an image observer.
        }else{
            g.drawImage(singleImg, (int)position.X, (int)position.Y, null); //Draws straight from the lonely image.
        }
    }
    
    //Returns the animation.
    public Animation getAnimation(){
        return animation;
    }
    
}
