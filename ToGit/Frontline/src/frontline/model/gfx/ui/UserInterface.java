/*
 * Copyright (c) 2011 BleepBloop Software, All Rights Reserved
 *
 * Unpublished copyright.  All rights reserved.
 */
package frontline.model.gfx.ui;

import frontline.model.ActionScene;
import java.awt.Graphics2D;
import java.awt.Image;

import frontline.model.gfx.Fighter;
import frontline.model.gfx.core.Animation;
import frontline.model.gfx.core.Component;
import frontline.model.gfx.core.Sprite;
import frontline.model.util.Services;
import frontline.model.util.Vector2;
import java.awt.geom.AffineTransform;

/**
 * TODO: type comment.
 *
 * @version Aug 17, 2011, submitted by Nick Chavez
 */
public class UserInterface implements Component{

    public Sprite reticule;
    public Image footer;
    public WeaponBase weaponBase;
    public HealthMeter health;
    public EnemyMeter otherMeter;
    public Animation unshot; 
    public Animation shot;
    public Fighter player;
    public CircularLoad reloadIcon;
    public Blood blood;
    
    public UserInterface(Fighter player, Fighter enemy){
        this.player = player;
        loadResources(player, enemy);
    }
    
    @Override
    public void draw(Graphics2D g) {
        reticule.draw(g);
        g.drawImage(footer,
                0, 600 - Services.topInset - 29,
                800, 600 + Services.topInset,
                0, 0, 
                1, 1,
                null);
        otherMeter.draw(g);
        health.draw(g);
        weaponBase.draw(g);
        if(player.currentWeapon.reloadTimePassed >= 0){
            reloadIcon.draw(g);
        }
        blood.draw(g);
    }

    @Override
    public void update(long timePassed) {
        reticule.update(timePassed);
        weaponBase.update(timePassed);
        if(player.currentWeapon.reloadTimePassed >= 0){
            reloadIcon.position.X = player.position.X + 7;
            reloadIcon.position.Y = player.position.Y + 18;
            reloadIcon.update(timePassed);
        }
        blood.update(timePassed);
    }

    @Override
    public void loadResources(){}
    public void loadResources(Fighter player, Fighter enemy) {
        footer = ActionScene.depot.ui.get("footer");
        weaponBase = new WeaponBase(player.stock);
        otherMeter = new EnemyMeter(enemy);
        health = new HealthMeter();
        unshot = new Animation(ActionScene.depot.ui.get("unshot"), 2, 200);
        shot = new Animation(ActionScene.depot.ui.get("shot"), 2, 200);
        reticule = new Sprite(unshot);
        reloadIcon = new CircularLoad(ActionScene.depot.ui.get("reloadIcon"));
        blood = new Blood();
    }

}
