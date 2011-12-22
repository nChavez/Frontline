/*
 * Copyright (c) 2011 BleepBloop Software, All Rights Reserved
 *
 * Unpublished copyright.  All rights reserved.
 */
package frontline.model.gfx.core;

import frontline.model.ActionScene;
import java.awt.Graphics2D;
import java.awt.Image;

import frontline.model.gfx.ui.EnemyMeter;
import frontline.model.gfx.ui.HealthMeter;
import frontline.model.gfx.ui.Prompt;
import frontline.model.util.Services;

/**
 * TODO: type comment.
 *
 * @version Aug 17, 2011, submitted by Nick Chavez
 */
public class Announcements implements Component{

     public final String message = "Frontline: Milestone Build 1 $ "
            + "Nicolas Chavez -- Stuyvesant HS, $ 8026 247St Bellerose NY, 11426 DOB: 3/24/95  $ "
            + " $ "
            + "Frontline is a game I made over the summer in my spare time "
            + "with the Java programming language. I decided to undertake "
            + "this project because I felt that I wanted to go beyond the "
            + "AP Class I took in school. I combined my love of art with "
            + "my passion for computer science and quantification in "
            + "general to produce a form of art that is mathematically based. " 
            + "I started the project and immediately knew it was a way "
            + "to explore, on my own, the things I wasn't taught in the "
            + "classroom. I feel very rewarded and accomplished with this "
            + "and I am glad I had the opportunity to pursue this fusion "
            + "of my interests. $ "
            + " $ "
            + "Controls: $ "
            + "Use the WASD keys to move and the mouse to point and shoot. "
            + "Use the P key "
            + "to pause the game. Use the 1, 2, 3, 4 and 5 keys to switch "
            + "weapons and the R key to reload. $ "
            + " $ "
            + "                     Press Enter to Continue.";
    
    public EnemyMeter enemyMeter;
    public HealthMeter playerMeter; 
    public Image victory;
    public Image defeat;
    public Image paused;
    public Prompt intro;
    public boolean isPaused;
    
    public Announcements(){
        loadResources();
    }
    
    @Override
    public void draw(Graphics2D g) {
        if(enemyMeter != null && enemyMeter.percentage <= 0){
            g.drawImage(victory, (800 - victory.getWidth(null))/2, (600 - victory.getHeight(null))/2, null);
        }
        if(playerMeter != null && playerMeter.percentage <= 0){
            g.drawImage(defeat, (800 - defeat.getWidth(null))/2, (600 - defeat.getHeight(null))/2, null);
        }
        if(isPaused){
            g.drawImage(paused, (800 - paused.getWidth(null))/2, (600 - paused.getHeight(null))/2, null);
        }
        if(intro.active){
            intro.draw(g);
        }
    }

    @Override
    public void update(long timePassed) {
        // TODO Auto-generated method stub
        
    }

    @Override
    public void loadResources() {
        victory = ActionScene.depot.etc.get("victory"); 
        defeat = ActionScene.depot.etc.get("defeat"); 
        paused = ActionScene.depot.etc.get("paused"); 
        intro = new Prompt(message);
        isPaused = true;
    }

}
