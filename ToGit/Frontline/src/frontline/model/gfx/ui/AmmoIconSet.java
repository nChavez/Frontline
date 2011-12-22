/*
 * Copyright (c) 2011 BleepBloop Software, All Rights Reserved
 *
 * Unpublished copyright.  All rights reserved.
 */
package frontline.model.gfx.ui;

import java.awt.Graphics2D;

import frontline.model.gfx.Weapon;
import frontline.model.gfx.ui.AmmoIcon.State;
import frontline.model.util.Vector2;

/**
 * TODO: type comment.
 *
 * @version $Revision:$, submitted by $Author:$
 * @author chavenic: Aug 26, 2011, 8:41:03 AM
 */
public class AmmoIconSet {
    
    public AmmoIcon[][] icons;
    public Vector2 position;
    public Vector2 nextFullIcon;
    public Weapon weapon;
    public long reloadTimePassed;
    public long reloadTime;
    
    public AmmoIconSet(Weapon weapon){
        this.weapon = weapon;
        int width, height;
        if(Weapon.bulletsPerRound[weapon.type] % 10 == 0){
            width = Weapon.bulletsPerRound[weapon.type] / 10;
            height = 10;
        }else{
            width = Weapon.bulletsPerRound[weapon.type] / 2;
            height = 2;
        }
        icons = new AmmoIcon[width][height];
        position = new Vector2();
        initIcons();
        this.nextFullIcon = new Vector2();
        this.reloadTime = weapon.reloadTime;
        this.reloadTimePassed = -1;
    }
    
    public void fill(){
        for(int x = 0; x < icons.length; x++){
            for(int y = 0; y < icons[0].length; y++){
                icons[x][y].fill();
            }
        }
    }
    
    public void empty(){
        for(int x = 0; x < icons.length; x++){
            for(int y = 0; y < icons[0].length; y++){
                icons[x][y].empty();
            }
        } 
    }
    
    public void flash(){
        for(int x = 0; x < icons.length; x++){
            for(int y = 0; y < icons[0].length; y++){
                icons[x][y].flash();
            }
        } 
    }
    
    public boolean isFull(int x, int y){
        return icons[x][y].state == State.FULL;
    }
    
    public void setState(int x, int y, State state){
        icons[x][y].state = state;
        if(state == State.FULL){
            icons[x][y].fill();
        }
        if(state == State.EMPTY){
            icons[x][y].empty();
        }
        if(state == State.FLASHING){
            icons[x][y].flash();
        }
    }
    
    public void draw(Graphics2D g){
        for(int x = 0; x < icons.length; x++){
            for(int y = 0; y < icons[0].length; y++){
                icons[x][y].position.X = position.X + ((11  - 2) * x) - (y);
                icons[x][y].position.Y = position.Y + ((3 - 1) * y);
                icons[x][y].draw(g);
            }
        }
    }
    
    public void initIcons(){
        for(int x = 0; x < icons.length; x++){
            for(int y = 0; y < icons[0].length; y++){
                icons[x][y] = new AmmoIcon();
                icons[x][y].position.X = position.X + ((11  - 2) * x) - (y);
                icons[x][y].position.Y = position.Y + + ((3 - 1) * y);
            }
        }
    }
    
    public void next(){
        if((int)nextFullIcon.X == icons.length - 1 && (int)nextFullIcon.Y == icons[0].length - 1){
            setState(icons.length - 1, icons[0].length - 1, State.EMPTY);   
            nextFullIcon.Y += 1;
            reloadTimePassed = 0;
        }
        else if((int)nextFullIcon.Y == icons[0].length - 1 && (int)nextFullIcon.X != icons.length - 1){
            setState((int)nextFullIcon.X, (int)nextFullIcon.Y, State.EMPTY);
            nextFullIcon.X += 1;
            nextFullIcon.Y = 0;
        }else if((int)nextFullIcon.Y <= icons[0].length - 1){
            setState((int)nextFullIcon.X, (int)nextFullIcon.Y, State.EMPTY);
            nextFullIcon.Y += 1;
        }
        
    }
    
    public void refill(){
            nextFullIcon.X = 0;
            nextFullIcon.Y = 0;
            fill();
    }
    
    public void update(long timePassed){
        if(weapon.roundDenom == 0){
            empty();
        }else{
            if(reloadTimePassed >= 0){
                reloadTimePassed += timePassed;
            }
            if((int)nextFullIcon.X == icons.length - 1 && (int)nextFullIcon.Y == icons[0].length &&reloadTimePassed >= reloadTime && weapon.roundDenom - Weapon.bulletsPerRound[weapon.type] >= 0 && weapon.roundDenom > 0){
                reloadTimePassed = -1;
                refill();   
            }
            if(weapon.resetAmmo && weapon.bulletsLeft == Weapon.bulletsPerRound[weapon.type]){
                reloadTimePassed = -1;
                refill();
                weapon.resetAmmo = false;
            }else{
                reloadTimePassed = -1;
                refill(weapon.bulletsLeft);
                weapon.resetAmmo = false;
            }
        }
        
        
    }
    public int getWidth(){
        return (icons.length * 9) + 2;
    }

    public void refill(int amount){
        int maxX = icons.length - ((int)amount / icons[0].length) - 1;
        int maxY = icons[0].length - (amount % icons[0].length);
        fill();
        for(int x = 0; x <= maxX; x++){
            for(int y = 0; (y < maxY || x != maxX) && y < icons[x].length; y++){
                icons[x][y].empty();
            }
        }
        nextFullIcon = new Vector2(maxX, maxY);
    }
}
