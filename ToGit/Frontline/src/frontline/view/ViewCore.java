/*
 * Copyright (c) 2011 BleepBloop Software, All Rights Reserved
 *
 * Unpublished copyright.  All rights reserved.
 */
package frontline.view;

import java.awt.Cursor;
import java.awt.DisplayMode;
import java.awt.Font;
import java.awt.Graphics2D;
import java.awt.Point;
import java.awt.Toolkit;
import java.awt.Window;
import java.awt.image.BufferedImage;

import javax.swing.JFrame;

import frontline.model.ActionScene;
import frontline.model.util.Services;

/**
 * TODO: type comment.
 *
 * @version Aug 17, 2011, submitted by Nick Chavez
 */
public class ViewCore {
    public ScreenManager screenmanager; //The frame wrapper class.
    public Display mode;
    private static DisplayMode modes[] = {
        new DisplayMode(800, 600, 32, 0),
        new DisplayMode(800, 600, 24, 0),
        new DisplayMode(800, 600, 16, 0),
    };
    public ActionScene engine;
    
    public enum Display{FULLSCREEN, WINDOW}
    
    public ViewCore (Display mode, ActionScene engine){
        this.mode = mode;
        this.engine = engine;
        init();
    }
    
    public void init(){
        screenmanager = new ScreenManager(mode);
        if(mode == Display.FULLSCREEN){
            DisplayMode dm = screenmanager.findFirstCompatibleMode(modes);
            screenmanager.setFullScreen(dm);         
        }  
        
        BufferedImage cursorImg = new BufferedImage(16, 16, BufferedImage.TYPE_INT_ARGB);
        // Create a new blank cursor.
        Cursor blankCursor = Toolkit.getDefaultToolkit().createCustomCursor(
                cursorImg, new Point(0, 0), "blank cursor"); 
        // Set the blank cursor to the JFrame. 
        ((JFrame)screenmanager.getWindow()).getContentPane().setCursor(blankCursor);
        Font f = new Font("Dialog",Font.BOLD, 17);
        ((JFrame)screenmanager.getWindow()).setFont(f);

        Services.topInset = screenmanager.getWindow().getInsets().top;
        Services.leftInset = screenmanager.getWindow().getInsets().left;
        Services.bottomInset = screenmanager.getWindow().getInsets().bottom;
        Services.rightInset = screenmanager.getWindow().getInsets().right;
    }
    
    public void draw(){
        Graphics2D g = screenmanager.getGraphics();
        engine.draw(g);
        screenmanager.update(); //Refreshes the window/screen.
    }
    
    public Window getWindow(){
        return screenmanager.getWindow();
    }
    
    public void restoreScreen(){
        screenmanager.restoreScreen();
    }
}
