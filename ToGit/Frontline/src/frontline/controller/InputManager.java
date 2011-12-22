/*
 * Copyright (c) 2011 BleepBloop Software, All Rights Reserved
 *
 * Unpublished copyright.  All rights reserved.
 */
package frontline.controller;

import java.awt.Window;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;
import java.awt.event.MouseWheelEvent;
import java.awt.event.MouseWheelListener;
import frontline.model.ActionScene;

/**
 * TODO: type comment.
 *
 * @version Aug 17, 2011, submitted by Nick Chavez
 */
public class InputManager implements MouseListener, MouseWheelListener, MouseMotionListener, KeyListener{

    private Window window;
    public ActionScene engine;
    public boolean setListeners;
    private KeyManager keyManager;
    public MouseManager mouseManager;
    
    public InputManager (ActionScene engine, Window window){  
        this.engine = engine;
        keyManager = new KeyManager(engine);
        mouseManager = new MouseManager(engine);
        this.window = window;
        setListeners();
        setListeners = false;
    }

    //If a key is pressed, it depends on what scene is open,
    //and goes to that method.  
    public void keyPressed(KeyEvent e) {
        int keyCode = e.getKeyCode();  
        keyManager.handleKeyPressed(keyCode);   
        e.consume();
    }
 
    //If a key is released, it depends on what scene is open,
    //and goes to that method.  
    public void keyReleased(KeyEvent e) {
        if(!engine.announcements.isPaused){
            int keyCode = e.getKeyCode();
            keyManager.handleKeyReleased(keyCode);
        }
        e.consume();
    }

    //I think that I explained that you would only use this if you were
    //Making the next Microsoft Word. Which you're not. I think.
    //Btw, e.consume() and <whatever>.dispose basically releases
    //The stuff from memory. Since we don't need the KeyEvent after 
    //We've processed it, we can throw this out.
    public void keyTyped(KeyEvent e) {
        e.consume();
    }
    public void mouseDragged(MouseEvent e) {
        e.consume();
    }

    public void mouseMoved(MouseEvent e) {
        if(!engine.announcements.isPaused){
            mouseManager.handleMouseMoved(e.getX(), e.getY());
        }
        e.consume();
    }

    public void mouseWheelMoved(MouseWheelEvent e) {
        e.consume();
    }

    public void mouseClicked(MouseEvent e) {
        if(!engine.announcements.isPaused){
            mouseManager.handleMouseClicked(e);
        }
        e.consume();
    }

    public void mousePressed(MouseEvent e) {
        if(!engine.announcements.isPaused){
            mouseManager.handleMousePressed(e);
        }
        e.consume();
    }

    public void mouseReleased(MouseEvent e) {
        if(!engine.announcements.isPaused){
            mouseManager.handleMouseReleased(e);
        }
        e.consume();
    }

    public void mouseEntered(MouseEvent e) {
        e.consume();
    }

    public void mouseExited(MouseEvent e) {
        e.consume();
    }
    //Basically sets up for hearing.
    public void setListeners(){
        this.window.setFocusTraversalKeysEnabled(false); // Makes the Tab button meaningless.
        this.window.addKeyListener(this); //Makes this a key listener. If you erase this line, input would die!!
        this.window.addMouseListener(this);
        this.window.addMouseMotionListener(this);
        this.window.addMouseWheelListener(this);
        setListeners = true;
    }
    
}

