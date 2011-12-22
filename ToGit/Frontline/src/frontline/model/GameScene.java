package frontline.model;

import java.awt.Graphics2D;
import java.util.HashMap;

import frontline.DeathMatch;
import frontline.model.gfx.core.Component;


/*
 * Copyright (c) 2011 BleepBloop Software, All Rights Reserved
 *
 * Unpublished copyright.  All rights reserved.  
 */

/**
 * A generic GameScene. I should probably scrap it though, since I'm not using it.
 * You might need it for threads though, so I'll leave it in here.
 *
 * @version 1.00, submitted by Nicolas Chavez
 * @author chavenic: Jul 25, 2011, 8:57:36 AM
 */
public abstract class GameScene{
    
    public DeathMatch game;
    public HashMap <Integer, Component> components;
    public boolean running;
    public int mspf = 100; //mspf stands for MiliSeconds Per Frame, which is how long a frame should last.
    
    public abstract void draw(Graphics2D g);
    public abstract void update(long timePassed);
    public void stop(){
        running = false;
    }
}

