/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package frontline.model.gfx;

import frontline.model.gfx.core.Component;
import frontline.model.gfx.Fighter.SoldierClass;
import frontline.model.util.Services;
import frontline.model.util.Vector2;
import java.util.Random;

/** Copyright 2011, Bleep Bloop Software.
 *  Unpublished Copyright, All Rights Reserved
 */
/**
 *
 * @author Nick
 */
public class AIFighter extends Fighter implements Component{
    
    int randCounter = 50;
    int shootCounter = 2;
    int rand = 0;
    int shoot = 0;
    Vector2 target = Services.playerPos;
    
    public AIFighter(SoldierClass type){
        super(type);
        randomize();
    }
    
    public void update(long timePassed){
        super.update(timePassed);
        control();
    }
    
    public void control(){
    
        if(toHitBounds()){
            int dir;
            if((legs.direction/2) <= 4){
                randomize();
            }else{
                randomize();
            }
        }
        if(rand >= randCounter){
            rand = 0;
            randomize();
        }
        if(shoot >= shootCounter){
            shoot = 0;
            aim(target);
            fire(target);
        
        }
        target = Services.playerPos;
        rand++;
        shoot++;
    }
    
    public void randomize(){
        Random rand = new Random();
        int diff = rand.nextInt(3);
        diff--;
        diff = diff * 2;
        int dir = legs.direction + diff;
        if(dir > 15 || dir < 0){
            randomize();
        }else{
            move(dir);
        }
        if(toHitBounds()){
            randomize();
        }
    }
    
    public boolean toHitBounds(){
        int direction = legs.direction;
        int y2 = 600 - 10 - Services.topInset - 45;
        int y1 = Services.topInset;
        int x2 = 800 + Services.leftInset + Services.rightInset + 10;
        int x1 = Services.leftInset;
        boolean ans = false;
        if ((direction >= 13 || direction <= 3) && position.Y <= y1
                || direction >= 5 && direction <= 11 && position.Y + legs.getHeight() >= y2
                || direction >= 1 && direction <= 7 && position.X + upperBody.getHeight() >= x2
                || direction >= 9 && direction <= 15 && position.X <= x1) {
            ans = true;
        }
        return ans;
    }
    
    public void aim(Vector2 aim){
    int x = (int)aim.X;
    int y = (int)aim.Y;
    double angle = Services.getAngle(position.X + getWidth()/2, 
                position.Y + getHeight()/2, 
                x, y);
        
            
            //setDirection(angle);
            
    }

    
    public void fire(Vector2 mousePos){
            Vector2 shooterVec = new Vector2(position.X, position.Y);
            if(currentWeapon.reloadTimePassed == -1){
                    Services.enemyBullets.add(shooterVec , new Vector2(mousePos.X, mousePos.Y), Weapon.accuracy[currentWeapon.type]);
                    currentWeapon.bulletsLeft--;
            }
            
    }
    
}
