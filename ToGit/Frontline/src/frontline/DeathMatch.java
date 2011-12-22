/*
 * Copyright (c) 2011 BleepBloop Software, All Rights Reserved
 *
 * Unpublished copyright.  All rights reserved.
 */
package frontline;

import java.awt.AWTException;
import java.awt.MouseInfo;
import java.awt.Robot;
import java.awt.event.KeyEvent;

import frontline.controller.InputManager;
import frontline.controller.MouseManager;
import frontline.model.ActionScene;
import frontline.model.util.Vector2;
import frontline.view.ViewCore;
import frontline.view.ViewCore.Display;
import java.io.IOException;

/**
 * TODO: type comment.
 *
 * @version Aug 17, 2011, submitted by Nick Chavez
 */
public class DeathMatch{
    
    public enum Scene {
        ACTION, PAUSE, INTRO
    }
    public boolean running;
    public ActionScene action;
    public InputManager controller;
    public ViewCore view;
    public Scene currentScene;
    
    public DeathMatch(Display mode, String where) throws IOException{
        currentScene = Scene.ACTION;
        action = new ActionScene(where);
        view = new ViewCore(mode, action);
        controller = new InputManager(action, view.screenmanager.getWindow());
    }
    
    public void init(){
        action.running = true;
    }
    
    public void run() { //Starts program. 
         try{
            init();
            gameLoop(); //Starts Main game loop.
        }finally {
            try{
            view.restoreScreen(); //Ends by closing window.
            }catch(Exception e){
                Robot robot;
                try {
                    robot = new Robot();
                    robot.keyPress(KeyEvent.VK_ESCAPE);
                } catch (AWTException e1) {             
                    e1.printStackTrace();
                }            
            }
            System.exit(0);
        }
    }
    
    public void gameLoop(){
        long startTime = System.currentTimeMillis(); //This is the time the program started at, in milliseconds since some date in 1970. Idk why.
        long cumTime = startTime; //This is startTime, except it changes in real time, while startTime remains constant.

        while (action.running) { //This lets the Main loop run.
            long timePassed = System.currentTimeMillis() - cumTime; // This is the time that has passed between now and the last time the method was called. 
                                                                    //Basically it tells you how much time has passed in between cycles.
            cumTime += timePassed; //Updates cumTime to reflect real time.       
            update(timePassed);
            handleDrag();
            if(!action.running){
                break;
            }
            
            try {
                Thread.sleep(20); //Lets the computer take a short nap so that it doesn't overload. This is the only thing I know how to use threads for. lol.
            } catch (Exception ex) {}
        }
    }
    
    public void update(long timePassed){
        action.update(timePassed);
        action.player.currentWeapon.holdTimePassed += timePassed;
        view.draw();
    }
    
    public void handleDrag(){
        if(controller.mouseManager.currentState == MouseManager.MOUSE_PRESSED){
            controller.mouseManager.handleMouseMoved((int)(MouseInfo.getPointerInfo().getLocation().x - view.getWindow().getLocation().x), 
                    (int)(MouseInfo.getPointerInfo().getLocation().y - view.getWindow().getLocation().y));
        }
        holding();
    }
    
    public void holding(){
        if(controller.mouseManager.currentState == MouseManager.MOUSE_PRESSED && action.player.currentWeapon.holdTimePassed >= action.player.currentWeapon.delay){
            controller.mouseManager.shoot(
                    new Vector2(action.player.position.X + action.player.getWidth()/2, 
                            action.player.position.Y + action.player.getHeight()/2),
                    new Vector2(
                    (MouseInfo.getPointerInfo().getLocation().x - view.getWindow().getLocation().x),
                    (MouseInfo.getPointerInfo().getLocation().y - view.getWindow().getLocation().y)));
            controller.mouseManager.oldState = MouseManager.MOUSE_PRESSED;
            controller.mouseManager.currentState = MouseManager.MOUSE_PRESSED;
            action.player.currentWeapon.holdTimePassed = 0;
        }
    }
    
    public static void main(String [] args) throws IOException{
        DeathMatch dm = new DeathMatch(Display.WINDOW, args[0]);
        dm.run();
    }
}
