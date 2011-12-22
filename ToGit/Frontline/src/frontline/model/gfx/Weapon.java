/*
 * Copyright (c) 2011 BleepBloop Software, All Rights Reserved
 *
 * Unpublished copyright.  All rights reserved.
 */
package frontline.model.gfx;

import frontline.model.ActionScene;
import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.Rectangle;
import java.util.HashMap;

import frontline.model.gfx.core.Sprite;
import frontline.model.util.Directions;
import frontline.model.util.Services;
import frontline.model.util.Vector2;

/**
 * TODO: type comment.
 *
 * @version Aug 23, 2011, submitted by Nick Chavez
 */
public class Weapon{
    
    public static final int MACHINE_GUN = 0;
    public static final int RIFLE = 1;
    public static final int SEMI_AUTOMATIC = 2;
    public static final int PISTOL = 3;
    public static final int MUSKET = 4;
    public static final int INFINITE = 5;
    public static final int SNIPER_RIFLE = 6;
    public static final int SHOTGUN = 7;
    public static final int BAZOOKA = 8;
    public static final int SILENCER = 9;
    public static final int AK_47 = 10;
    public static final int GATLING_GUN = 11;
    public static final int ROCKET_LAUNCHER = 12;
    
    public static final int DEFAULT = MACHINE_GUN;
    public static final String[] names = {
        "MACHINE GUN", "RIFLE", "SEMI-AUTOMATIC", "PISTOL", "MUSKET", "INFINITE",
        "SNIPER RIFLE", "SHOTGUN", "BAZOOKA", "SILENCER",
        "AK-47", "GATLING GUN", "ROCKET-LAUNCHER"
    };
    public static final int[] rateOfFire = {
      0, 150, 300, 700, 3000, 200
    };   
    public static final int[]bulletsPerRound = {
      100, 30, 20, 6, 2, 50  
    };
    public static final int[] rounds = {
        3, 50, 20, 10, 2, 50000
    };
    public static final int[] firepower = {
        5, 10, 8, 7, 3, 2 
    };
    public static final int[] accuracy = {
        1, 3, 4, 4, 1, 1
    };
    public final long reloadTime = 3000;
    public final long delay;
    public final int type;
    
    public int bulletsLeft;  
    public long holdTimePassed;
    public long reloadTimePassed;
    public int roundDenom;
    public int direction;
    public double angle;
    public Image sheet;
    public HashMap<Integer, Sprite> spriteset;
    public Sprite sprite;
    public boolean resetAmmo;
    
    public Weapon(int weaponType){
        type = weaponType;
        delay = rateOfFire[type];
        loadResources();
        sprite = spriteset.get(Directions.NORTH);
        bulletsLeft = bulletsPerRound[type];
        roundDenom = rounds[type] * bulletsPerRound[type];
        resetAmmo = false;
        reloadTimePassed = -1;
    }
    
    public void update (long timePassed){
        if(reloadTimePassed != -1){
            reloadTimePassed += timePassed;   
        }
        if(bulletsLeft <= 0 && roundDenom > bulletsPerRound[type] && reloadTimePassed == -1){
            reloadTimePassed = 0;
        }
        if(reloadTimePassed >= reloadTime){
            if(roundDenom - bulletsPerRound[type] < bulletsPerRound[type] - bulletsLeft){
                bulletsLeft += roundDenom - bulletsPerRound[type];
                roundDenom = bulletsPerRound[type];
                resetAmmo = true;
            }else{       
                roundDenom -= bulletsPerRound[type] - bulletsLeft;
                bulletsLeft = bulletsPerRound[type]; 
                resetAmmo = true;
            }
            reloadTimePassed = -1;
        }
        if(roundDenom <= 0){
            bulletsLeft = 0;
            roundDenom = 0;
        }
        /*
         * 
         * THE PROBLEM WITH ROUNDS LEFT AND BULLETS LEFT IS THAT THERE ARE TWO GUNS!!!!!!!
         * THE ENEMY'S AND THE PLAYERS!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!
         * --FIXED
         * 
         * LAST ROUND JUST DISAPPEARS...
         */
    }
   public void setDirection(double angle){
       this.angle = angle;
       direction = (int) ((angle/22.5));
       
       if(angle > 11.25 && angle <= 33.75){
           direction = Directions.NORTHNORTHEAST;
           
       }else if(angle > 33.75 && angle <= 56.25){
           direction = Directions.NORTHEAST;  
       }else if(angle > 56.25 && angle <= 78.75){
           direction = Directions.EASTNORTHEAST;
       }else if(angle > 78.75 && angle <= 101.25){
           direction = Directions.EAST;
       }else if(angle > 101.25 && angle <= 123.75){
           direction = Directions.EASTSOUTHEAST;
       }else if(angle > 123.75 && angle <= 146.25){
           direction = Directions.SOUTHEAST;
       }else if(angle > 146.25 && angle <= 168.75){
           direction = Directions.SOUTHSOUTHEAST;
       }else if(angle > 168.75 && angle <= 191.25){
           direction = Directions.SOUTH;
       }else if(angle > 191.25 && angle <= 213.75){
           direction = Directions.SOUTHSOUTHWEST;
       }else if(angle > 213.75 && angle <= 236.25){
           direction = Directions.SOUTHWEST;
       }else if(angle > 236.25 && angle <= 258.75){
           direction = Directions.WESTSOUTHWEST;
       }else if(angle > 258.75 && angle <= 281.25){
           direction = Directions.WEST;
       }else if(angle > 281.25 && angle <= 303.75){
           direction = Directions.WESTNORTHWEST;
       }else if(angle > 303.75 && angle <= 326.25){
           direction = Directions.NORTHWEST;
       }else if(angle > 326.25 && angle <= 348.75){
           direction = Directions.NORTHNORTHWEST;
       }else if(angle > 348.75 || angle <= 11.25){
           direction = Directions.NORTH;
       }
       sprite = spriteset.get(direction);
    }
    public int getWidth(){
        return sheet.getWidth(null);
    }
    public int getHeight(){
        return sheet.getHeight(null)/16;
    }
    
    public Rectangle getRectangle(){
        Rectangle ans = new Rectangle(0, (sheet.getHeight(null)/16) * direction, 
                    sheet.getWidth(null), sheet.getHeight(null)/16);
        return ans;
    }
    
    public void draw(Graphics2D g, Vector2 position){
        g.drawImage(sheet, 
                (int)position.X - 16, (int)position.Y,
                (int)(position.X + getWidth() - 16), (int)(position.Y + getHeight()),
                getRectangle().x, getRectangle().y, 
                getRectangle().x + getRectangle().width,
                getRectangle().y + getRectangle().height,
                null
        );
    }
    
 public void loadResources(){
        sheet = ActionScene.depot.weapons.get("" + Weapon.names[type].replace(" ", "_"));
        Sprite[] sprites = new Sprite[16];
        for(int i = 0; i < 16; i++){
            Image singleSheet = Services.crop(Services.toBufferedImage(sheet),
                    new Rectangle(
                            0, (sheet.getHeight(null)/16) * i, sheet.getWidth(null), sheet.getHeight(null)/16
                            ));
            sprites[i] = new Sprite(Services.makeColorTransparent(singleSheet, Color.BLACK));
        }
        
        spriteset = new HashMap<Integer, Sprite>();
        for(int i = 0; i < 16; i++){
            spriteset.put(i, sprites[i]);
        }
    }
 
     public void manualReload(){
         //reload!
         //Two Cases:
         //One: Has enough -> roundDenom - bulletsPerRound[type] >= bulletsPerRound[type] - bulletsLeft  
         //Two: Can only fill partially -> 0 < roundDenom - bulletsPerRound[type] < bulletsPerRound[type] - bulletsLeft
         if(canReload()){
             if(roundDenom - bulletsPerRound[type] >= bulletsPerRound[type] - bulletsLeft){
                 //Case 1
                 reloadTimePassed = 0;
                 
             }
             else if(roundDenom - bulletsPerRound[type] < bulletsPerRound[type] - bulletsLeft){
                 //Case 2
                 System.out.println("Case 2");
                 reloadTimePassed = 0;
             }
         }
         
     }
     
     public boolean canReload(){
         boolean ans = false;
         if(roundDenom - bulletsPerRound[type] > 0 && bulletsLeft != bulletsPerRound[type] && bulletsLeft >= 0 && reloadTimePassed == -1){
             ans = true;
         }
         return ans;
     }

}
