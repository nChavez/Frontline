/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package frontline.model.gfx.ui;

import frontline.model.ActionScene;
import frontline.model.gfx.core.Component;
import frontline.model.gfx.core.Sprite;
import frontline.model.util.Services;
import java.awt.FontMetrics;
import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.Rectangle;
import java.util.ArrayList;

/** Copyright 2011, Bleep Bloop Software.
 *  Unpublished Copyright, All Rights Reserved
 *
 */
/**
 *
 * @author Nick
 */
public class Prompt extends Sprite implements Component{
    
    public Image back;
    public Image front;
    public String text;
    public boolean active;
    public final int width = 450;
    public final int height = 530;
    public final int border = 2;
    public final int textBorder = 10;
    public Rectangle textBounds;
    
    public Prompt(String text){
        loadResources();
        this.text = text;
        position.X = ((800-width)/2) + Services.leftInset - Services.rightInset;
        position.Y = ((600-height)/2) + Services.topInset - Services.bottomInset;
        
        textBounds = new Rectangle(((int)position.X) + border + textBorder,
                ((int)position.Y) + border + textBorder * 2,
                width - (2*border) - (2*textBorder),
                height - (2*border) - (2*textBorder));
        active = true;
    }
    
    @Override
    public void draw(Graphics2D g){
        if(active){
            g.drawImage(back, (int)position.X, (int)position.Y,
                (int)(position.X + width), (int)(position.Y + height),
                0, 0, 1, 1, null);
            g.drawImage(front, (int)position.X + 2, (int)position.Y + 2,
                (int)(position.X + width - 2), (int)(position.Y + height - 2),
                0, 0, 1, 1, null);
            drawText(g);
        }
    }

    @Override
    public void loadResources() {
        back = ActionScene.depot.ui.get("backColor");
        front = ActionScene.depot.ui.get("frontColor");
    }
    
    public void drawText(Graphics2D g){
        String txtCpy = text;
        ArrayList<String> ans = new ArrayList<String>();
        String temp = "";
        String[] split = txtCpy.split(" ");
        for(int i = 0; i < split.length; i++){
                if(!fits( temp + split[i], g) && !(split[i].compareTo("$") == 0)){
                    ans.add(temp);
                    temp = "";
                }else if(split[i].compareTo("$") == 0){
                    ans.add(temp);
                    temp = "";
                    i++;
                }
                temp += split[i] + " ";
        }
        ans.add(temp);
        FontMetrics fm   = g.getFontMetrics(g.getFont());
        java.awt.geom.Rectangle2D rect = fm.getStringBounds(ans.get(0), g);
        int linespace = (int)rect.getHeight();
        int i = 0;
        for(String line : ans){
            g.drawString(line, textBounds.x, textBounds.y + (linespace * i));
            i++;     
        }
    }
    public boolean fits(String line, Graphics2D g){
        FontMetrics fm   = g.getFontMetrics(g.getFont());
        java.awt.geom.Rectangle2D rect = fm.getStringBounds(line, g);
        return (rect.getWidth() < textBounds.width);
    }
}
