/*
 * Copyright (c) 2011 BleepBloop Software, All Rights Reserved
 *
 * Unpublished copyright.  All rights reserved.
 */
package frontline.model.gfx.ui;

import frontline.model.ActionScene;
import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.Image;

import javax.swing.ImageIcon;

import frontline.model.util.Services;

/**
 * TODO: type comment.
 *
 * @version Aug 17, 2011, submitted by Nick Chavez
 */
public class HealthMeter {

    public Image fill;
    public Image base;
    public Image mask;
    public double percentage;
    
    public HealthMeter(){
        base = ActionScene.depot.ui.get("healthbase");
        fill = ActionScene.depot.ui.get("healthfill");
        mask = ActionScene.depot.ui.get("healthmask");
        
        percentage = 100;
    }
    
    public void draw(Graphics2D g){
        g.drawImage(base,
                Services.leftInset, 600 - (base.getHeight(null) * 2),
                Services.leftInset + (base.getWidth(null) * 2), 600,
                0, 0,
                base.getWidth(null), base.getHeight(null),
                null);
        
      
        if(percentage > 0){
        g.drawImage(fill,
                (int)((60 - (((100 - percentage)/100)) * ((fill.getWidth(null)*2) - 10))), 600 - (base.getHeight(null) * 2) + 18,
                (int)((Services.leftInset + (fill.getWidth(null) * 2) + 60 - (((100 - percentage)/100)) * ((fill.getWidth(null) * 2) - 10))), 600 - (base.getHeight(null) * 2) + 18 + (fill.getHeight(null) * 2),
                0, 0,
                fill.getWidth(null), fill.getHeight(null),
                null);
        }
        g.drawImage(mask,
                Services.leftInset, 600 - (mask.getHeight(null) * 2),
                Services.leftInset + (mask.getWidth(null) * 2), 600,
                0, 0,
                mask.getWidth(null), mask.getHeight(null),
                null);
        
        
        if(percentage > 99){
            g.setColor(Color.WHITE);
            g.drawString((int)percentage + "", Services.leftInset + 31, 600 - (mask.getHeight(null) * 2) + 34);
        }
        if(percentage > 0 && percentage < 100){
            g.setColor(Color.WHITE);
            g.drawString((int)percentage + "", Services.leftInset + 40, 600 - (mask.getHeight(null) * 2) + 34);
        }
    }
    
}

