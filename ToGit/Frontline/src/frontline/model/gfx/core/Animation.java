/*
 * Copyright (c) 2011 BleepBloop Software, All Rights Reserved
 *
 * Unpublished copyright.  All rights reserved.
 */
package frontline.model.gfx.core;

import java.awt.Image;
import java.awt.Rectangle;

/**
 * TODO: type comment.
 *
 * @version Aug 17, 2011, submitted by Nick Chavez
 */
public class Animation {

    public int sceneIndex; //What frame the animation is on.
    private long movieTime; //How long the animation has been going on for, resetting at zero every new cycle.
    private long totalTime; //How much time the animation SHOULD go on for. Does not change.
    public int frameWidth; //The width, in pixels, of each frame/picture.
    public long frameTime; //How long a single frame should last.
    public int numFrames; //The number of frames.
    public Image sheet; //The image file that has all the images for one animation. A spritesheet.
    public boolean stopped;
    
    //Takes in a spritesheet, a number of frames in the sheet, and the time the frameTime.
    public Animation(Image sheet, int numFrames, long frameTime) { 
        this.sheet = sheet;
        this.numFrames = numFrames;
        this.frameTime = frameTime;
        frameWidth = sheet.getWidth(null)/numFrames; //Calculates frame width assuming all frames are the same width.
        totalTime = numFrames * frameTime; //Calculates the total time.
        start(); //Begins animation.
    }
    
    //You cannot start the animation in two places.
    public synchronized void start() {
        movieTime = 0; //Sets realtime to zero.
        sceneIndex = 0; //Resets to the first frame in the sequence.
        stopped = false;
    }

    //Checks whether or not the next frame should be moved to. Also, it updates the real time, or movieTime.
    public synchronized void update(long timePassed) {
        if (numFrames > 1) {
            movieTime += timePassed; //Updates movieTime.
            if (movieTime >= totalTime) {//Checks to see if the sequence should be restarted.
                start();
            }
            while (movieTime > (sceneIndex + 1) * frameTime) { //Checks to move on to the next frame.
                sceneIndex++;
            }
        }
    }

    //Returns the rectangle that represents the box that surrounds the image on the sheet.
    //Its like what piece of the rectangle we want so that we can crop it later on.
    //Dependent on what frame we're up to. For example, a rectangle would specify this:
    /*
     *          -------------++++++++-------------
     *          |            +  I   +    You're  |
     *          |            + want +    next    |
     *          |            + this +    bub!    |
     *          |            + part +            |
     *          |            +  of  +            |
     *          |            + the  +            |
     *          |            + sheet+            |
     *          -------------++++++++-------------
     * 
     */
    public Rectangle getRectangle(){
        int x1 = sceneIndex * frameWidth;
        int y1 = 0;
        int x2 = x1 + frameWidth;
        int y2 = sheet.getHeight(null);
        Rectangle ans = new Rectangle(x1, y1, x2, y2);
        return ans;
    }

    

}
