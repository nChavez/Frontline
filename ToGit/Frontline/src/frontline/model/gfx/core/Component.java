/*
 * Copyright (c) 2011 BleepBloop Software, All Rights Reserved
 *
 * Unpublished copyright.  All rights reserved.
 */
package frontline.model.gfx.core;

import java.awt.Graphics2D;

/**
 * TODO: type comment.
 *
 * @version Aug 17, 2011, submitted by Nick Chavez
 */
public interface Component {
  
    public static final int BACKGROUND = 0;
    public static final int TILEGRID = 1;
    public static final int PLAYER = 2;   
    public static final int ENEMY = 3;
    public static final int PLAYERBULLETS = 4; 
    public static final int ENEMYBULLETS = 5;
    public static final int BLOODS = 6;
    public static final int USERINTERFACE = 7;
    public static final int ANNOUNCEMENTS = 8;
    public static final int PROMPT = 9;
    public void draw(Graphics2D g);
    public void update(long timePassed);
    public void loadResources();
}
