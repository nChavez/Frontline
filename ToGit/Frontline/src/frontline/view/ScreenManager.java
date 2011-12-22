/*
 * Copyright (c) 2011 BleepBloop Software, All Rights Reserved
 *
 * Unpublished copyright.  All rights reserved.
 */
package frontline.view;

import java.awt.DisplayMode;
import java.awt.Graphics2D;
import java.awt.GraphicsConfiguration;
import java.awt.GraphicsDevice;
import java.awt.GraphicsEnvironment;
import java.awt.Toolkit;
import java.awt.Window;
import java.awt.image.BufferStrategy;
import java.awt.image.BufferedImage;

import javax.swing.JFrame;

import frontline.view.ViewCore.Display;
/**
 * TODO: type comment.
 *
 * @mode Aug 17, 2011, submitted by Nick Chavez
 */
public class ScreenManager {

    private GraphicsDevice device;
    public JFrame frame;

    public Display mode;
    public String title;
    /**
        Creates a new ScreenManager object.
    */
    public ScreenManager(Display mode) {
        title = "Frontline";
        this.mode = mode;
        if(mode == Display.FULLSCREEN){
            GraphicsEnvironment environment =
                GraphicsEnvironment.getLocalGraphicsEnvironment();
            device = environment.getDefaultScreenDevice();
            }else 
         if(mode == Display.WINDOW){
             setUpWindow();
         }
        }


    /**
        Returns a list of compatible display modes for the
        default device on the system.
    */
    public DisplayMode[] getCompatibleDisplayModes() {
        if(mode == Display.FULLSCREEN)
            return device.getDisplayModes();
        return null;
    }


    /**
        Returns the first compatible mode in a list of modes.
        Returns null if no modes are compatible.
    */
    public DisplayMode findFirstCompatibleMode(
        DisplayMode modes[]){
        if(mode == Display.FULLSCREEN){
            DisplayMode goodModes[] = device.getDisplayModes();
            for (int i = 0; i < modes.length; i++) {
                for (int j = 0; j < goodModes.length; j++) {
                    if (displayModesMatch(modes[i], goodModes[j])) {
                        return modes[i];
                    }
                }
            }
            return null;
        }
        return null;
    }


    /**
        Returns the current display mode.
    */
    public DisplayMode getCurrentDisplayMode() {
        if(mode == Display.FULLSCREEN){
            return device.getDisplayMode();
        }
        return null;
    }


    /**
        Determines if two display modes "match". Two display
        modes match if they have the same resolution, bit depth,
        and refresh rate. The bit depth is ignored if one of the
        modes has a bit depth of DisplayMode.BIT_DEPTH_MULTI.
        Likewise, the refresh rate is ignored if one of the
        modes has a refresh rate of
        DisplayMode.REFRESH_RATE_UNKNOWN.
    */
    public boolean displayModesMatch(DisplayMode mode1,
        DisplayMode mode2){
        if(mode == Display.FULLSCREEN){
            if (mode1.getWidth() != mode2.getWidth() ||
                    mode1.getHeight() != mode2.getHeight()){
            return false;
            }
            if (mode1.getBitDepth() != DisplayMode.BIT_DEPTH_MULTI &&
                    mode2.getBitDepth() != DisplayMode.BIT_DEPTH_MULTI &&
                    mode1.getBitDepth() != mode2.getBitDepth()){
                return false;
            }
            if (mode1.getRefreshRate() !=
                DisplayMode.REFRESH_RATE_UNKNOWN &&
                mode2.getRefreshRate() !=
                    DisplayMode.REFRESH_RATE_UNKNOWN &&
                    mode1.getRefreshRate() != mode2.getRefreshRate()){
                return false;
            }
            return true;
        }
        return false;
    }


    /**
        Enters full screen mode and changes the display mode.
        If the specified display mode is null or not compatible
        with this device, or if the display mode cannot be
        changed on this system, the current display mode is used.
        <p>
        The display uses a BufferStrategy with 2 buffers.
    */
    public void setFullScreen(DisplayMode displayMode){
            frame = new JFrame(title);
            frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
            frame.setUndecorated(true);
            frame.setIgnoreRepaint(true);
            frame.setResizable(false);
            //frame.setIconImage(new ImageIcon(getClass().getResource("../../../Resources/Images/fl.png")).getImage()); //Sets the icon.
            device.setFullScreenWindow(frame);

            if (displayMode != null &&
            device.isDisplayChangeSupported()){
                try {
                    device.setDisplayMode(displayMode);
                }
                catch (IllegalArgumentException ex) { }
                // fix for mac os x
                frame.setSize(displayMode.getWidth(),
                        displayMode.getHeight());
            }
            // avoid potential deadlock in 1.4.1_02
                try {
                    frame.createBufferStrategy(2);
                    }
                catch (Exception  ex) {
                // ignore
                }
        }


    /**
        Gets the graphics context for the display. The
        ScreenManager uses double buffering, so applications must
        call update() to show any graphics drawn.
        <p>
        The application must dispose of the graphics object.
    */
    public Graphics2D getGraphics() {
        if(mode == Display.FULLSCREEN){
            Window window = device.getFullScreenWindow();
            if (window != null) {
                BufferStrategy strategy = window.getBufferStrategy();
                return (Graphics2D)strategy.getDrawGraphics();
            }
            else {
                return null;
            }
        }else{
          //Checks for frame's existence.
            if (frame != null) {
                
               BufferStrategy s = frame.getBufferStrategy();
               return (Graphics2D) s.getDrawGraphics();
            }
            else {

                return null;
            }
        }
    }


    /**
        Updates the display.
    */
    public void update() {
        Window window;
        if(mode == Display.FULLSCREEN){
            window = device.getFullScreenWindow();
        }else{
            window = frame;
        }
        if (window != null) {
            BufferStrategy strategy = window.getBufferStrategy();
            if (!strategy.contentsLost()) {
                strategy.show();
            }
        }
        // Sync the display on some systems.
        // (on Linux, this fixes event queue problems)
        Toolkit.getDefaultToolkit().sync();
    }


    /**
        Returns the window currently used in full screen mode.
        Returns null if the device is not in full screen mode.
    */
    public JFrame getFullScreenWindow() {
        if(mode == Display.FULLSCREEN){
            return (JFrame)device.getFullScreenWindow();
        }
        return null;
    }


    /**
        Returns the width of the window currently used in full
        screen mode. Returns 0 if the device is not in full
        screen mode.
    */
    public int getWidth() {
        if(mode == Display.FULLSCREEN){
            Window window = device.getFullScreenWindow();
            if (window != null) {
                return window.getWidth();
            }
            else {
                return 0;
            }
        }
        return 800;
    }


    /**
        Returns the height of the window currently used in full
        screen mode. Returns 0 if the device is not in full
        screen mode.
    */
    public int getHeight() {
        if(mode == Display.FULLSCREEN){
            Window window = device.getFullScreenWindow();
            if (window != null) {
                return window.getHeight();
            }   
            else {
                return 0;
            }
        }
        return 600;
    }


    /**
        Restores the screen's display mode.
    */
    public void restoreScreen() {
        Window window;
        if(mode == Display.FULLSCREEN){
            window = device.getFullScreenWindow();
        }else{
            window = frame;
        }
        if (window != null) {
            window.dispose();
        }
        device.setFullScreenWindow(null);
    }


    /**
        Creates an image compatible with the current display.
    */
    public BufferedImage createCompatibleImage(int w, int h,
        int transparancy)
    {
        Window window = device.getFullScreenWindow();
        if (window != null) {
            GraphicsConfiguration gc =
                window.getGraphicsConfiguration();
            return gc.createCompatibleImage(w, h, transparancy);
        }
        return null;
    }
    
    public synchronized void setUpWindow() {

        frame = new JFrame(title); //Sets Title to Proton Pong.

        frame.setSize(800, 600);  //Sets size. This is the best size possible.
        frame.setUndecorated(false); //Makes the window look like an OS window.
        frame.setIgnoreRepaint(true); //Actually I have no idea what this does. lol.
        frame.setResizable(false); //You can't make the window bigger or smaller.
        frame.setLocationRelativeTo(null); //Centres the window.
        //frame.setIconImage(new ImageIcon(getClass().getResource("../../../Resources/Images/fl.png")).getImage()); //Sets the icon.
        frame.setVisible(true); //Makes it visible
        frame.setSize(800 + frame.getInsets().right, 600 + frame.getInsets().bottom); //Adjusts for OS's window borders. 
        frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        
        //Services.bottomInset = frame.getInsets().bottom;
        //Services.topInset = frame.getInsets().top;
        //Services.leftInset = frame.getInsets().left;
        //Services.rightInset = frame.getInsets().right;
        
        
        frame.createBufferStrategy(2); //Starts double-buffering.
    } 
    
    public Window getWindow(){
        if(mode == Display.FULLSCREEN){
            return getFullScreenWindow();
        }else{
            return frame;
        }
    }
}
