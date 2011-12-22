/*
 * CopYright (c) 2011 BleepBloop Software, All Rights Reserved
 *
 * Unpublished copYright.  All rights reserved.
 */
package frontline.model.gfx;

import frontline.model.ActionScene;
import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.Rectangle;
import java.util.HashMap;

import frontline.model.gfx.core.Animation;
import frontline.model.gfx.core.Component;
import frontline.model.gfx.core.Sprite;
import frontline.model.gfx.core.tiles.Tile;
import frontline.model.util.Directions;
import frontline.model.util.Services;
import frontline.model.util.Vector2;
import java.util.ArrayList;
import java.util.Iterator;

/**
 * TODO: tYpe comment.
 *
 * @version Aug 17, 2011, submitted by Nick Chavez
 */
public class Fighter implements Component {

    public final int boxSize;

    public enum SoldierClass {

        SOLDIER(0), MARKSMAN(1), ASSAULT(2), SPY(3), COMMANDO(4),
        TROOPER(5), SNIPER(6), HEAVY(7), RECON(8), OFFICER(9),
        TEMPLATE(10), ENEMY(11);
        public int num;

        SoldierClass(int num) {
            this.num = num;
        }
    }
    public int[][] stocks = {
        //should be rifle and pistol
        {Weapon.MACHINE_GUN, Weapon.RIFLE, Weapon.SEMI_AUTOMATIC, Weapon.PISTOL, Weapon.MUSKET}, {Weapon.ROCKET_LAUNCHER, Weapon.BAZOOKA}, {Weapon.SNIPER_RIFLE, Weapon.SEMI_AUTOMATIC}, {Weapon.SILENCER, Weapon.MACHINE_GUN}, {Weapon.GATLING_GUN, Weapon.RIFLE},
        {Weapon.AK_47, Weapon.PISTOL}, {Weapon.BAZOOKA, Weapon.RIFLE}, {Weapon.SNIPER_RIFLE, Weapon.PISTOL}, {Weapon.SILENCER, Weapon.SHOTGUN}, {Weapon.GATLING_GUN, Weapon.ROCKET_LAUNCHER},
        {Weapon.MACHINE_GUN, Weapon.RIFLE, Weapon.SEMI_AUTOMATIC, Weapon.PISTOL, Weapon.MUSKET}, {Weapon.INFINITE}
    };
    public MouseFollower upperBody;
    public Legs legs;
    public MouseFollower hand;
    public Weapon[] stock;
    public Weapon currentWeapon;
    public Image belt;
    public boolean moving;
    public float speed;
    public Vector2 position;
    public Vector2 velocity;
    public Rectangle boundingBox;
    public SoldierClass type;

    public Fighter() {
        type = SoldierClass.TEMPLATE;
        loadResources();
        moving = false;
        speed = .15f;
        position = new Vector2((800 - getWidth()) / 2, (600 - getHeight()) / 2);
        velocity = new Vector2(0f, 0f);
        boxSize = 30;
        boundingBox = new Rectangle((int) position.X + 8, (int) position.Y + getHeight() - boxSize, 20, boxSize);
    }

    public Fighter(SoldierClass type) {
        this.type = type;
        loadResources();
        moving = false;
        speed = .15f;
        position = new Vector2((800 - getWidth()) / 2, (600 - getHeight()) / 2);
        velocity = new Vector2(0f, 0f);
        boxSize = 30;
        boundingBox = new Rectangle((int) position.X + 8, (int) position.Y + getHeight() - boxSize, 20, boxSize);
    }

    public void loadUpperBody() {
        Image sheet = ActionScene.depot.characters.get(type.toString()).get("upperBody");
        Sprite[] sprites = new Sprite[16];
        for (int i = 0; i < 16; i++) {
            Image singleSheet = Services.crop(Services.toBufferedImage(sheet),
                    new Rectangle(
                    0, (sheet.getHeight(null) / 16) * i, sheet.getWidth(null), sheet.getHeight(null) / 16));
            sprites[i] = new Sprite(Services.makeColorTransparent(singleSheet, Color.BLACK));
        }

        HashMap<Integer, Sprite> spriteset = new HashMap<Integer, Sprite>();
        for (int i = 0; i < 16; i++) {
            spriteset.put(i, sprites[i]);
        }

        upperBody = new MouseFollower(spriteset);
        upperBody.sheet = sheet;
    }

    public void loadLegs() {
        int mspf = 100;

        Animation[] anims = new Animation[8];
        Image sheet = ActionScene.depot.characters.get(type.toString()).get("legs");

        for (int i = 0; i < 8; i++) {
            Image singleSheet = Services.crop(Services.toBufferedImage(sheet),
                    new Rectangle(
                    0, (sheet.getHeight(null) / 8) * i, sheet.getWidth(null), sheet.getHeight(null) / 8));
            anims[i] = new Animation(Services.makeColorTransparent(singleSheet, Color.BLACK), 4, mspf);
        }

        HashMap<Integer, Animation> animations = new HashMap<Integer, Animation>();
        for (int i = 0; i < 8; i++) {
            animations.put((2 * i), anims[i]);
        }

        legs = new Legs(animations);
        legs.sheet = sheet;
    }

    public void loadHand() {
        Image sheet = ActionScene.depot.characters.get(type.toString()).get("hand");
        Sprite[] sprites = new Sprite[16];
        for (int i = 0; i < 16; i++) {
            Image singleSheet = Services.crop(Services.toBufferedImage(sheet),
                    new Rectangle(
                    0, (sheet.getHeight(null) / 16) * i, sheet.getWidth(null), sheet.getHeight(null) / 16));
            sprites[i] = new Sprite(Services.makeColorTransparent(singleSheet, Color.BLACK));
        }

        HashMap<Integer, Sprite> spriteset = new HashMap<Integer, Sprite>();
        for (int i = 0; i < 16; i++) {
            spriteset.put(i, sprites[i]);
        }

        hand = new MouseFollower(spriteset);
        hand.sheet = sheet;
    }

    public void loadWeapon() {
        stock = new Weapon[stocks[type.num].length];
        for (int i = 0; i < stocks[type.num].length; i++) {
            stock[i] = new Weapon(stocks[type.num][i]);
        }
        currentWeapon = stock[0];
    }

    public void update(long timePassed) {
        updateComponents(timePassed);
        checkCollision();
    }

    public void draw(Graphics2D g) {
        g.drawImage(legs.animation.sheet,
                (int) position.X, (int) position.Y,
                (int) position.X + legs.animation.frameWidth, (int) position.Y + getHeight(),
                legs.animation.getRectangle().x, legs.animation.getRectangle().y,
                legs.animation.getRectangle().x + legs.animation.frameWidth, legs.animation.getRectangle().y + getHeight(),
                null);

        if (currentWeapon.direction == Directions.NORTHEAST || currentWeapon.direction == Directions.NORTHWEST
                || currentWeapon.direction == Directions.NORTHNORTHWEST || currentWeapon.direction == Directions.NORTHNORTHEAST) {
            currentWeapon.draw(g, position);
        }
        upperBody.draw(g, position);
        if (currentWeapon.direction != Directions.NORTHEAST && currentWeapon.direction != Directions.NORTHWEST
                && currentWeapon.direction != Directions.NORTHNORTHWEST && currentWeapon.direction != Directions.NORTHNORTHEAST) {
            currentWeapon.draw(g, position);
        }
        hand.draw(g, position);

    }

    public void updateComponents(long timePassed) {
        legs.ubDirection = upperBody.direction;
        if (legs.animation != null && moving) {
            legs.update(timePassed);
        }
        if (!moving) {
            legs.animation.sceneIndex = 1;
        }
        currentWeapon.update(timePassed);
        position.X += velocity.X * timePassed;
        position.Y += velocity.Y * timePassed;
        boundingBox.x = (int) position.X + 5;
        boundingBox.y = (int) (position.Y + upperBody.getHeight() - boundingBox.height);

    }

    public int getWidth() {
        return upperBody.getWidth();
    }

    public int getHeight() {
        return upperBody.getHeight();
    }

    @Override
    public void loadResources() {
        loadUpperBody();
        loadLegs();
        loadHand();
        loadWeapon();
    }

    public void setVelocity(int direction) {
        int offset = 90;
        velocity.X = (float) ((speed) * Math.cos(Math.toRadians(legs.direction * 22.5 - offset)));
        velocity.Y = (float) ((speed) * Math.sin(Math.toRadians(legs.direction * 22.5 - offset)));
    }

    public void setDirection(double angle) {
        upperBody.setDirection(angle);
        hand.setDirection(angle);
        currentWeapon.setDirection(angle);
    }

    public void checkCollision() {
        if (toHitBounds(legs.direction)) {
            velocity = new Vector2();
            moving = false;
        }
    }

    public boolean toHitBounds(int direction) {
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
        if (toHitBlocked(direction)) {
            ans = true;
        }


        return ans;
    }

    public void move(int direction) {
        if (toHitBounds(direction) || toHitBlocked(direction)) {
            stop();
            legs.setDirection(direction);
        } else {
            legs.setDirection(direction);
            moving = true;
            setVelocity(direction);
        }
    }

    public void stop() {
        velocity.X = 0f;
        velocity.Y = 0f;
        moving = false;
    }

    public boolean toHitBlocked(int direction) {
        ArrayList<Tile> set;

        if (Services.grid != null) {
            set = Services.grid.getBlockedTiles();
            for (Tile t : set) {
                int y2 = (int)t.position.Y + t.getHeight();
                int y1 = (int)t.position.Y;
                int x2 = (int)t.position.X + t.getWidth();
                int x1 = (int)t.position.X;
                int buffer = 5;
                
                if (  
                          (((direction >= 13 || direction <= 3) && position.Y <= y2 && (Math.abs(position.Y - y2)) < buffer)
                        && (( position.X == x1 && position.X + getWidth() == x2 ) || (position.X > x1 && position.X < x2) ||
                        (position.X + getWidth()  > x1 && position.X + getWidth() < x2) || 
                        (position.X < x1 && position.X + getWidth()> x2)))
                        
                         
                        || ((direction >= 5 && direction <= 11 && position.Y + getHeight() >= y1 && (Math.abs(position.Y + getHeight() - y1)) < buffer) 
                          && (( position.X == x1 && position.X + getWidth() == x2 ) || (position.X > x1 && position.X < x2) ||
                        (position.X + getWidth()  > x1 && position.X + getWidth() < x2) || 
                        (position.X < x1 && position.X + getWidth()> x2)))
                                        
                        || ((direction >= 1 && direction <= 7 && position.X + getWidth() >= x1 && (Math.abs(position.X + getWidth() - x1)) < buffer) 
                        && (( position.Y == y1 && position.Y + getHeight() == y2 ) || (position.Y > y1 && position.Y < y2) ||
                        (position.Y + getHeight()  > y1 && position.Y + getHeight() < y2) || 
                        (position.Y < y1 && position.Y + getHeight()> y2)))
                        
                        || ((direction >= 9 && direction <= 15 && position.X <= x2 && (Math.abs(position.X - x2)) < buffer) 
                        && (( position.Y == y1 && position.Y + getHeight() == y2 ) || (position.Y > y1 && position.Y < y2) ||
                        (position.Y + getHeight()  > y1 && position.Y + getHeight() < y2) || 
                        (position.Y < y1 && position.Y + getHeight()> y2)))
                        
                        ){
                    return true;
                }
            }
        }

        return false;
    }
}
