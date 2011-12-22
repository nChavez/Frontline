/*
 * Copyright (c) 2011 BleepBloop Software, All Rights Reserved
 *
 * Unpublished copyright.  All rights reserved.
 */
package frontline.model.gfx.core.tiles;

import java.awt.Color;
import java.awt.Image;
import java.awt.Rectangle;
import java.awt.image.BufferedImage;

import frontline.model.util.Services;

/**
 * TODO: type comment.
 *
 * @version Aug 17, 2011, submitted by Nick Chavez
 */
public class TileSet {
    
    public Image sheet;
    public int size;
    public Image[][]tiles;
    
    public TileSet(Image sheet){
        this.sheet = sheet;
        size = 32;
        tiles = new Image[sheet.getWidth(null)/size][sheet.getHeight(null)/size];
        loadSet();
    }
    
    public void loadSet(){
        BufferedImage bsheet = Services.toBufferedImage(sheet);
        for(int x = 0; x < tiles.length; x++){
            for(int y = 0; y < tiles[0].length; y++){
                tiles[x][y] = Services.makeColorTransparent(
                        Services.crop(bsheet, new Rectangle(x * size, y * size, size, size)), Color.BLACK);
            }
        }
    }

}

