/*
 * Copyright (c) 2011 BleepBloop Software, All Rights Reserved
 *
 * Unpublished copyright.  All rights reserved.
 */
package frontline.model.gfx.core.tiles;

import java.awt.Image;


import frontline.model.gfx.core.Sprite;
import frontline.model.gfx.core.tiles.Map;
import frontline.model.util.Vector2;

/**
 * TODO: type comment.
 *
 * @version Aug 17, 2011, submitted by Nick Chavez
 */
public class Tile extends Sprite{
    
    public Map grid;
    public Vector2 gridPosition;
    public int priority;
    public boolean blocker;
   
    public Tile(Image singleImg){
        super(singleImg);
        gridPosition = new Vector2(0, 0);
        priority = 1;
        blocker = false;
    }
    
    public Tile (Image singleImg, Map grid, Vector2 gridPosition){
        super(singleImg);
        this.grid = grid;
        this.gridPosition = gridPosition;
        position = new Vector2 ((gridPosition.X * grid.tileSize), 
                (gridPosition.Y * grid.tileSize)); 
        priority = 1;
        blocker = false;
    }
    
    public Tile (Image singleImg, Map grid, Vector2 gridPosition, boolean isBlocker){
        super(singleImg);
        this.grid = grid;
        this.gridPosition = gridPosition;
        position = new Vector2 ((gridPosition.X * grid.tileSize), 
                (gridPosition.Y * grid.tileSize)); 
        priority = 1;
        blocker = isBlocker;
    }
 
    public void setGridPosition(Vector2 newGridPosition){
        gridPosition = newGridPosition;
        position = new Vector2 (gridPosition.X * grid.tileSize, gridPosition.Y * grid.tileSize);
    }
    
    public String toString(){return gridPosition.toString();}
    
    public int getWidth(){
        return grid.tileSize;
    }
    public int getHeight(){
        return grid.tileSize;
    }
}

