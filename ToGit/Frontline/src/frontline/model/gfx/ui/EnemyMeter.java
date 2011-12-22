/*
 * Copyright (c) 2011 BleepBloop Software, All Rights Reserved
 *
 * Unpublished copyright.  All rights reserved.
 */
package frontline.model.gfx.ui;

import frontline.model.ActionScene;
import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics2D;
import java.awt.Image;

import javax.swing.ImageIcon;

import frontline.model.gfx.Fighter;

/**
 * TODO: type comment.
 *
 * @version Aug 24, 2011, submitted by Nick Chavez
 */
public class EnemyMeter {
    
    public Image fill;
    public Image base;
    public Fighter enemy;
    public double percentage;
    
    public EnemyMeter(Fighter enemy){
        this.enemy = enemy;
        base = ActionScene.depot.ui.get("otherBar");
        fill = ActionScene.depot.ui.get("otherFill");
  
        percentage = 100;
    }
    
    public void draw(Graphics2D g){
        if(percentage > 0){
            g.drawImage(base, 
                    (int)enemy.position.X + 2, (int)enemy.position.Y - 5,
                    (int)enemy.position.X + base.getWidth(null) + 2,
                    (int)enemy.position.Y - 5 + base.getHeight(null),
                    0, 0,
                    base.getWidth(null), base.getHeight(null),
                    null);
                for(int i = 0; i <= (percentage/4.4); i++){
                    g.drawImage(fill, (int)enemy.position.X + 2 + (i) + 2, (int)enemy.position.Y - 5 + 3, null);
                }
                g.setColor(Color.BLACK);
                g.setFont(new Font("Dialog", Font.BOLD, 10));
                if(percentage < 100){
                    g.drawString(((int)percentage) + "", (int)enemy.position.X + 2 + base.getWidth(null) - 22,  (int)enemy.position.Y - 5 + 9);
                }else if(percentage > 99){
                    g.drawString(((int)percentage) + "", (int)enemy.position.X + 2 + base.getWidth(null) - 25,  (int)enemy.position.Y - 5 + 9);    
                }
            g.setFont(new Font("Dialog", Font.BOLD, 17));
        }
    }
}
