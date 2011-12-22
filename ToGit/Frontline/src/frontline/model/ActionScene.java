/*
 * Copyright (c) 2011 BleepBloop Software, All Rights Reserved
 *
 * Unpublished copyright.  All rights reserved.
 */
package frontline.model;

import Resources.ResourceLoader;
import frontline.model.gfx.AIFighter;
import java.awt.Graphics2D;
import java.awt.Rectangle;
import java.io.IOException;
import java.util.HashMap;
import java.util.Iterator;


import frontline.model.gfx.Background;
import frontline.model.gfx.Bullet;
import frontline.model.gfx.Bullets;
import frontline.model.gfx.Fighter;
import frontline.model.gfx.Weapon;
import frontline.model.gfx.Fighter.SoldierClass;
import frontline.model.gfx.core.Announcements;
import frontline.model.gfx.core.Component;
import frontline.model.gfx.core.tiles.Map;
import frontline.model.gfx.core.tiles.Tile;
import frontline.model.gfx.ui.UserInterface;
import frontline.model.util.Services;
import frontline.model.util.Vector2;
/**
 * TODO: type comment.
 *
 * @version Aug 17, 2011, submitted by Nick Chavez
 */
public class ActionScene extends GameScene{
    
    public boolean running;
    public HashMap <Integer, Component> components;
    public Fighter player;
    public Fighter enemy;
    public UserInterface ui;
    public Bullets playerBullets;
    public Bullets enemyBullets;
    public Announcements announcements;
    public Map grid;
    public static ResourceLoader depot;

    
    public ActionScene(String where) throws IOException{
        /*try {
            System.setOut(new PrintStream("C:/mylog.txt"));
        } catch (FileNotFoundException ex) {
            Logger.getLogger(ActionScene.class.getName()).log(Level.SEVERE, null, ex);
        }*/
        components = new HashMap<Integer, Component>();
        initComponents(where);
    }
    
    public void update(long timePassed){
        if(!announcements.isPaused){
            Integer[] keys = components.keySet().toArray(new Integer[0]); 
            for(int i = 0; i < keys.length; i++){
                components.get(keys[i]).update(timePassed);
            }
                checkBulletCollisions();
        }
    }
    
    public void initComponents(String where) throws IOException{
        depot = new ResourceLoader();

        components.put(Component.BACKGROUND, new Background());
        components.put(Component.TILEGRID, (depot.process(depot.mapInputs.get("MapOne"), depot.tilesets.get("overworld"))));
        components.put(Component.PLAYER, new Fighter(SoldierClass.SOLDIER));
        components.put(Component.USERINTERFACE, new UserInterface(((Fighter)components.get(Component.PLAYER)), (Fighter)components.get(Component.ENEMY)));
        components.put(Component.PLAYERBULLETS, new Bullets());
        components.put(Component.ENEMYBULLETS, new Bullets());
        components.put(Component.ENEMY, new AIFighter(SoldierClass.ENEMY));
        components.put(Component.ANNOUNCEMENTS, new Announcements());
        
        player = (Fighter)components.get(Component.PLAYER);
        enemy = (Fighter)components.get(Component.ENEMY);
        ui = (UserInterface)components.get(Component.USERINTERFACE);
        playerBullets = (Bullets)components.get(Component.PLAYERBULLETS);
        announcements = (Announcements)components.get(Component.ANNOUNCEMENTS);
        enemyBullets = (Bullets)components.get(Component.ENEMYBULLETS);
        grid = (Map)components.get(Component.TILEGRID);
        Services.grid = this.grid;
        Services.enemyBullets = enemyBullets;
        
        enemy.position = new Vector2(400, 300);
        enemy.setDirection(180);
        
        player.position = new Vector2(200, 350);
        
        ui.otherMeter.enemy = enemy;
    
        ((Announcements)components.get(Component.ANNOUNCEMENTS)).enemyMeter = ui.otherMeter;
        ((Announcements)components.get(Component.ANNOUNCEMENTS)).playerMeter = ui.health;
        
    }
    
    public void moveFighter(Fighter fighter, int direction){
      if(fighter.toHitBounds(direction) || toHitBlocked(fighter)){
          stopFighter(fighter);
          fighter.legs.setDirection(direction);
      }else{
          fighter.legs.setDirection(direction);
          fighter.moving = true;
          fighter.setVelocity(direction);
      }
    }
    
    public void stopFighter(Fighter fighter){
        fighter.velocity.X = 0f;
        fighter.velocity.Y = 0f;
        fighter.moving = false;
    }
    
    
    public void draw(Graphics2D g){
        Integer[] keys = components.keySet().toArray(new Integer[0]); 
        for(int i = 0; i < keys.length; i++){
            components.get(keys[i]).draw(g);
        } 
    }
    
    public synchronized void checkBulletCollisions(){
        for(Iterator<Bullet> itr = playerBullets.liveBullets.iterator(); itr.hasNext();){
            Bullet bill = itr.next();
            Rectangle billRect = new Rectangle((int)bill.position.X, (int)bill.position.Y, bill.getWidth(), bill.getHeight());
            Rectangle enemyRect = new Rectangle((int)enemy.position.X, (int)enemy.position.Y,
                    enemy.getWidth(), enemy.getHeight());
            
            Rectangle tileRect = grid.getRect(bill.position);           
            if(grid.isBlocked(bill.position) && tileRect.intersectsLine(bill.oldPos.X, bill.oldPos.Y, bill.position.X, bill.position.Y)){
                itr.remove();
            }
            
            else if(billRect.intersects(enemyRect) || 
                    enemyRect.intersectsLine(bill.oldPos.X, bill.oldPos.Y, bill.position.X, bill.position.Y)){
                ui.otherMeter.percentage -= Weapon.accuracy[player.currentWeapon.type];
                itr.remove();
            }        
            
            
        }
        
        for(Iterator<Bullet> itr = enemyBullets.liveBullets.iterator(); itr.hasNext();){
            Bullet bill = itr.next();
            Rectangle billRect = new Rectangle((int)bill.position.X, (int)bill.position.Y, bill.getWidth(), bill.getHeight());
            Rectangle playerRect = new Rectangle((int)player.position.X, (int)player.position.Y,
                    player.getWidth(), player.getHeight());
            Rectangle tileRect = grid.getRect(bill.position);
            if(grid.isBlocked(bill.position) && tileRect.intersectsLine(bill.oldPos.X, bill.oldPos.Y, bill.position.X, bill.position.Y)){
                itr.remove();
            }
            
            else if(billRect.intersects(playerRect) || 
                    playerRect.intersectsLine(bill.oldPos.X, bill.oldPos.Y, bill.position.X, bill.position.Y)){
                ui.health.percentage -= Weapon.accuracy[enemy.currentWeapon.type];
                ui.blood.visible = true;
                itr.remove();
            }
        }
        
        Services.playerPos = player.position;
    }
    
    public boolean toHitBlocked(Fighter fighter){
        boolean ans = false;
        return ans;
    }
    
}
