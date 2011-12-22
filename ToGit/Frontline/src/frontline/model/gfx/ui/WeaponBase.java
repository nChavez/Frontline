/*
 * Copyright (c) 2011 BleepBloop Software, All Rights Reserved
 *
 * Unpublished copyright.  All rights reserved.
 */
package frontline.model.gfx.ui;

import frontline.model.ActionScene;
import java.awt.Color;
import java.awt.FontMetrics;
import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.Rectangle;

import frontline.model.gfx.Weapon;
import frontline.model.util.Services;
import frontline.model.util.Vector2;

/**
 * TODO: type comment.
 *
 * @version Aug 24, 2011, submitted by Nick Chavez
 */
public class WeaponBase {

    public Image weaponBase;
    public Weapon weapon;
    public Image weaponImage;
    public AmmoIconSet[] sets;
    public AmmoIconSet ammo;
    public Weapon [] stock;
    
    public WeaponBase(Weapon[] stock){
        this.stock = stock;
        this.weapon = stock[0];
        weaponImage = weapon.sheet;
        weaponImage = Services.crop(Services.toBufferedImage(weaponImage), new Rectangle(0, 192, 64, 240));
        weaponImage = Services.makeColorTransparent(weaponImage, Color.BLACK);
        weaponBase = ActionScene.depot.ui.get("weaponbase");
        initSets();
        
    }
    
    public void draw(Graphics2D g){
        g.drawImage(weaponBase,
                Services.leftInset + 800 - (weaponBase.getWidth(null) * 2), 600 - (weaponBase.getHeight(null) * 2),
                800, 600,
                0, 0,
                weaponBase.getWidth(null), weaponBase.getHeight(null),
                null);
        
        drawWeaponName(g);
        g.drawImage(weaponImage, 
                Services.leftInset + 800 - (weaponBase.getWidth(null) * 2) + 225, 600 - (weaponBase.getHeight(null) * 2) - 30,
                Services.leftInset + 800 - (weaponBase.getWidth(null) * 2) + 225 + (weaponImage.getWidth(null) * 2), 600 - (weaponBase.getHeight(null) * 2) + (weaponImage.getHeight(null) * 2) - 30,
                0, 0,
                weaponImage.getWidth(null), weaponImage.getHeight(null),
                null);
        drawWeaponAmmoCount(g);
        ammo.draw(g);
        
    }
    
    public void setWeapon(Weapon weapon){
        this.weapon = weapon;
        weaponImage = weapon.sheet;
        weaponImage = Services.crop(Services.toBufferedImage(weaponImage), new Rectangle(0, 192, 64, 240));
        weaponImage = Services.makeColorTransparent(weaponImage, Color.BLACK);
        
    }
    
    public void drawWeaponName(Graphics2D g){
        g.setColor(Color.WHITE);
        FontMetrics fm   = g.getFontMetrics(g.getFont());
        java.awt.geom.Rectangle2D rect = fm.getStringBounds(Weapon.names[weapon.type], g);
        int x = (165 - (int)(rect.getWidth()))  / 2 + Services.leftInset + 800 - ((weaponBase.getWidth(null) * 2)) + 33;
        int y = (15 - (int)(rect.getHeight())) / 2  + fm.getAscent() + 600 - (weaponBase.getHeight(null) * 2) + 2;
        g.drawString(Weapon.names[weapon.type], x, y);  
    }
    
    public void drawWeaponAmmoCount(Graphics2D g){
        g.setColor(Color.WHITE);
        FontMetrics fm   = g.getFontMetrics(g.getFont());
        java.awt.geom.Rectangle2D rect = fm.getStringBounds(weapon.bulletsLeft + "/" + weapon.roundDenom, g);
        int x = (165 - (int)(rect.getWidth()))  / 2 + Services.leftInset + 800 - ((weaponBase.getWidth(null) * 2)) + 33;
        int y = (15 - (int)(rect.getHeight())) / 2  + fm.getAscent() + 600 - (weaponBase.getHeight(null) * 2) + 2 + 30;
        if(weapon.bulletsLeft <=  0 || weapon.reloadTimePassed != -1){
            g.setColor(Color.RED);
        }
        g.drawString(weapon.bulletsLeft + "/" + weapon.roundDenom, x, y);  
        g.setColor(Color.WHITE);
    }
    
    public void drawAmmoIcons(Graphics2D g){
        if(Weapon.bulletsPerRound[weapon.type] % 10 == 0){
            for(int x = 0; x < 2; x++){
                for(int y = 0; y < 3; y++){
                    
                }
            }
        }
    }
    
    public void update(long timePassed){
        ammo.update(timePassed);
    }
    
    public void initSets(){
        sets = new AmmoIconSet[stock.length];
        for(int i = 0; i < stock.length; i++){
            sets[i] = new AmmoIconSet(stock[i]);
            sets[i].position = new Vector2(Services.leftInset + 800 - (weaponBase.getWidth(null) * 2) + 35 - (sets[i].getWidth()) + 3, 600 - (weaponBase.getHeight(null) * 2) + 25);
        }
        ammo = sets[0];
    }
}
