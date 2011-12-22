/*
 * Copyright (c) 2011 BleepBloop Software, All Rights Reserved
 *
 * Unpublished copyright.  All rights reserved.
 */
package frontline.model.gfx.core.tiles;

import java.awt.Graphics2D;
import java.awt.Image;

import frontline.model.gfx.core.Component;
import frontline.model.gfx.core.tiles.Tile;
import frontline.model.util.Vector2;
import java.awt.Rectangle;
import java.util.ArrayList;

/**
 * TODO: type comment.
 *
 * @version Aug 17, 2011, submitted by Nick Chavez
 */
public class Map implements Component{

    public int tileSize;
    public Tile[][] matrix;
    public int width;
    public int height;
    protected Vector2 position;
    
    public Map(){
        this.tileSize = 32;
        width = 800/tileSize;
        height = 600/tileSize;
        matrix = new Tile[this.width][this.height];
        position = new Vector2(0,0);
    }
    
    public Map(int tileSize){
        this.tileSize = tileSize;
        width = 800/tileSize;
        height = 600/tileSize;
        matrix = new Tile[this.width][this.height];
        position = new Vector2(0,0);
    }
    
    public void draw(Graphics2D g){
        for(int x = 0; x < matrix.length; x++){
            for(int y = 0; y < matrix[0].length; y++){
                if(matrix[x][y] != null){
                matrix[x][y].draw(g);
                }
            }
        }
    }
    
    public void setChunk(Tile tile, int x1, int y1, int x2, int y2){
        for(int x = x1; x < x2; x ++){
            for(int y = y1; y < y2; y++){
                matrix[x][y] = new Tile(tile.singleImg, this, new Vector2(x, y));
                matrix[x][y].setGridPosition(new Vector2(x,y));
            }
        }
    }
    
    public void setChunk(Image image, int x1, int y1, int x2, int y2){
        for(int x = x1; x < x2; x ++){
            for(int y = y1; y < y2; y++){
                matrix[x][y] = new Tile(image, this, new Vector2(x, y));
                matrix[x][y].setGridPosition(new Vector2(x,y));
            }
        }
    }
    
    public void setChunk(Image image, int x1, int y1, int x2, int y2, boolean isBlocker){
        for(int x = x1; x < x2; x ++){
            for(int y = y1; y < y2; y++){
                matrix[x][y] = new Tile(image, this, new Vector2(x, y), isBlocker);
                matrix[x][y].setGridPosition(new Vector2(x,y));
            }
        }
    }
    
    public void update(long timePassed){
        for(int x = 0; x < matrix.length; x++){
            for(int y = 0; y < matrix[0].length; y++){
                if(matrix[x][y] != null){
                matrix[x][y].update(timePassed);
            
                }
            }
        }
        
    }
    
    public void setPosition(Vector2 position){
        for(int x = 0; x < matrix.length; x ++){
            for(int y = 0; y < matrix[0].length; y++){
                if(matrix[x][y] != null){
                matrix[x][y].position = new Vector2(matrix[x][y].position.X + (position.X - this.position.X),
                        matrix[x][y].position.Y + (position.Y - this.position.Y));
                }
            }
        }
        this.position = position;
    }
    
    public void setTile(Tile tile, int x, int y){
        tile.setGridPosition(new Vector2(x, y));
        tile.position.X += this.position.X;
        tile.position.Y += this.position.Y;
        matrix[x][y] = tile;
    }
    
    public void setTile(Image image, int x, int y){
        matrix[x][y] = new Tile(image, this, new Vector2(x, y));
        matrix[x][y].setGridPosition(new Vector2(x,y));
    }
    
    public void setTile(Image image, int x, int y, boolean isBlocker){
        matrix[x][y] = new Tile(image, this, new Vector2(x, y), isBlocker);
        matrix[x][y].setGridPosition(new Vector2(x,y));
    }
    
    public Tile getTile(int x, int y){
        if(x < matrix.length && y < matrix[0].length && x >= 0 && y >= 0){
            return matrix[x][y];
        }
        return null;
    }

    @Override
    public void loadResources() {
         
    }
    
    public boolean isBlocked(Vector2 position){
        boolean ans = false;
        int x = (int)(position.X/tileSize);
        int y = (int)(position.Y/tileSize);
        Tile t = getTile(x, y);
        if(t != null){
            ans = t.blocker;
        }
        
        return ans;
    }
    
    public Rectangle getRect(Vector2 position){
        Rectangle ans = new Rectangle();
        ans.x = ((int)position.X / tileSize) * tileSize;
        ans.y = ((int)position.Y / tileSize) * tileSize;
        ans.width = tileSize;
        ans.height = tileSize;
       
        return ans;
    }
    
    public Rectangle getRect(int x, int y){
        if(x >= 0 && y >= 0 && x < matrix.length && y < matrix[0].length){
            Rectangle ans = new Rectangle();
            ans.x = x * tileSize;
            ans.y = y * tileSize;
            ans.width = tileSize;
            ans.height = tileSize;
            return ans;
        }
        return null;
    }

    public Tile getTileAt(Vector2 position){
        int x = (int)(position.X/tileSize);
        int y = (int)(position.Y/tileSize);
        Tile t = getTile(x,y);
        return t;
        
    }
    
    public ArrayList<Tile> getBlockedTiles(){
        ArrayList<Tile> ans = new ArrayList<Tile>();
        
        for(int x = 0; x < matrix.length; x++){
            for(int y = 0; y < matrix[0].length; y++){
                if(matrix[x][y].blocker){
                    ans.add(matrix[x][y]);
                }
            }
        }
        
        return ans;
    }
}

