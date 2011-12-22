/*
 * Copyright (c) 2011 BleepBloop Software, All Rights Reserved
 *
 * Unpublished copyright.  All rights reserved.
 */
package frontline.controller;

import java.awt.event.MouseEvent;

import frontline.model.ActionScene;
import frontline.model.gfx.Weapon;
import frontline.model.util.Services;
import frontline.model.util.Vector2;

/**
 * TODO: type comment.
 *
 * @version Aug 17, 2011, submitted by Nick Chavez
 */
public class MouseManager {

    private ActionScene engine;
    
    public static final int MOUSE_RELEASED = 0;
    public static final int MOUSE_PRESSED = 1;
    
    public int oldState;
    public int currentState;
   
    public MouseManager(ActionScene engine){
        this.engine = engine;
    }
    
    public void handleMouseMoved(int x, int y){
        double angle = Services.getAngle(engine.player.position.X + engine.player.getWidth()/2, 
                engine.player.position.Y + engine.player.getHeight()/2, 
                x, y);
        
            
            engine.player.setDirection(angle);
            engine.ui.reticule.position.X = x - engine.ui.reticule.getWidth()/2;
            engine.ui.reticule.position.Y = y - engine.ui.reticule.getHeight()/2;
            
    }
    
    public void handleMouseClicked(MouseEvent e){
        e.consume();
    }
    
    public void handleMousePressed(MouseEvent e){
        oldState = currentState;
        currentState = MOUSE_PRESSED;
        e.consume();
    }
    
    public void handleMouseReleased(MouseEvent e){
        oldState = currentState;
        currentState = MOUSE_RELEASED;
        engine.ui.reticule.animation = engine.ui.unshot;
        e.consume();
    }
    
    public void shoot(Vector2 shooterVec, Vector2 mousePos){
            if(engine.player.currentWeapon.bulletsLeft != 0 && engine.player.currentWeapon.roundDenom != 0 && engine.player.currentWeapon.reloadTimePassed == -1){
                    engine.ui.reticule.animation = engine.ui.shot;
                    engine.playerBullets.add(shooterVec , new Vector2(mousePos.X, mousePos.Y), Weapon.accuracy[engine.player.currentWeapon.type]);
                    engine.player.currentWeapon.bulletsLeft--;
                    engine.ui.weaponBase.ammo.next(); 
            }
    }
    
    
}

