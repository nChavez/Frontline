/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package Resources;

import frontline.model.gfx.core.tiles.TileSet;
import frontline.model.util.Services;
import java.awt.Image;
import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.HashMap;
import frontline.model.gfx.core.tiles.Map;
import java.awt.Color;

/**
 *
 * @author Consuelo
 */
public class ResourceLoader {
    
    public HashMap<String, HashMap<String, Image>> characters; 
    public HashMap<String, Image> etc;
    public HashMap<String, Image> ui;
    public HashMap<String, Image> tilesets;
    public HashMap<String, Image> weapons;
    public HashMap<String, ArrayList<String>> mapInputs;
    public long startTime;
    
    public static String [] etcFiles = {
        "ammoicon", "blood1", "blood2", "defeat", "emptyammoicon", "paused", "victory"
    };
    public static String [] uiFiles = {
        "ammoicon", "emptyammoicon", "footer", "healthbase", "healthfill", "healthmask", "otherBar", "otherFill", 
            "shot", "unshot", "weaponbase", "reloadIcon", "backColor", "frontColor", "bloodMask"
    };
    public static String [] tilesetsFiles = {
        "overworld"
    };
    public static String [] weaponsFiles = {
        "MACHINE_GUN", "MUSKET", "PISTOL", "RIFLE", "SEMI-AUTOMATIC", "INFINITE"
    };
    public static String [] mapFiles = {
        "MapOne.txt"
    };

    public ResourceLoader(){
        startTime = System.currentTimeMillis();
        init();
        load();
    }
    private void init(){
        characters = new HashMap<String, HashMap<String, Image>>();
        etc = new HashMap<String, Image>();
        ui = new HashMap<String, Image>();
        tilesets = new HashMap<String, Image>();
        weapons = new HashMap<String, Image>();
        mapInputs = new HashMap<String, ArrayList<String>>();
    }
    public void load(){
        loadCharacters();
        loadFolder(etc, etcFiles, "Etc");
        loadFolder(ui, uiFiles, "Interface");
        loadFolder(tilesets, tilesetsFiles, "Tilesets");
        loadFolder(weapons, weaponsFiles, "Weapons");
        loadMaps();
        
    }
    
    public void loadCharacters(){
        String [] fightersAvailable = {
            "SOLDIER", "TEMPLATE", "ENEMY"
        };
        for(int i = 0; i < fightersAvailable.length; i++){
            String type = fightersAvailable[i];
            HashMap<String, Image> images = new HashMap<String, Image>();
            images.put("hand", Services.loadImage(getClass().getResource("Images/Characters/" + type + "/hand.png")));
            images.put("legs", Services.loadImage(getClass().getResource("Images/Characters/" + type + "/legs.png")));
            images.put("upperBody", Services.loadImage(getClass().getResource("Images/Characters/" + type + "/upperBody.png")));
            characters.put(type, images);
        }
        HashMap<String, Image> e = new HashMap<String, Image>();
        e.put("bulletalt", Services.loadImage(getClass().getResource("Images/Characters/bulletalt.png")));
        e.put("bulletdark", Services.loadImage(getClass().getResource("Images/Characters/bulletdark.png")));
        characters.put("etc", e);
    }
    
    public void loadFolder(HashMap <String, Image> folder, String [] names, String srcHead){
        for(int i = 0; i  < names.length; i++){
            folder.put(names[i], Services.loadImage(getClass().getResource("Images/" + srcHead + "/" + names[i] + ".png")));
        }
    }
    
    public void loadMaps(){

        for(int i = 0; i  < mapFiles.length; i++){
            mapInputs.put(mapFiles[i].substring(0, mapFiles[i].lastIndexOf(".")), loadOneMap(mapFiles[i]));
        }
    }
    
    public ArrayList<String> loadOneMap(String fileName){
        ArrayList<String> input = new ArrayList<String>();
        String strLine = "";
        InputStream is = getClass().getResourceAsStream("Maps/" + fileName);
        InputStreamReader isr = new InputStreamReader(is);
        BufferedReader br = new BufferedReader(isr);
        try{
            while ((strLine = br.readLine()) != null){
             if (!strLine.equals("") && !strLine.substring(0, 1).equals("#")) {
                    input.add(strLine);
                }
          }
           br.close();
           isr.close();
           is.close();
        }catch(Exception e){}
        return input;
    }
    
    public Map process(ArrayList<String> input, Image tileset) {
        Map ans = new Map(Integer.parseInt(input.get(2)));
        TileSet set;
        Image sheet = tileset;
        set = new TileSet(sheet);
        ans.setChunk(set.tiles[Integer.parseInt(input.get(3).substring(0, 2))][Integer.parseInt(input.get(3).substring(3, 5))], 0, 0, ans.width, ans.height);
        for (int i = 4; i < input.size(); i++) {
            
            if ((input.get(i).substring(0, 1)).equals("@")) {//If @ is first, this is a passable chunk.
                int dx1 = Integer.parseInt(input.get(i).substring(1, 3));
                int dy1 = Integer.parseInt(input.get(i).substring(4, 6));
                int dx2 = Integer.parseInt(input.get(i).substring(7, 9));
                int dy2 = Integer.parseInt(input.get(i).substring(10, 12));
                int sx = Integer.parseInt(input.get(i).substring(13, 15));
                int sy = Integer.parseInt(input.get(i).substring(16, 18));
                ans.setChunk(set.tiles[sx][sy], dx1, dy1, dx2, dy2);

            }  else if ( ((input.get(i).substring(0, 1)).equals("!")) && ((input.get(i).substring(1, 2)).equals("@")) ) {//Blocked Chunk
                int dx1 = Integer.parseInt(input.get(i).substring(2, 4));
                int dy1 = Integer.parseInt(input.get(i).substring(5, 7));
                int dx2 = Integer.parseInt(input.get(i).substring(8, 10));
                int dy2 = Integer.parseInt(input.get(i).substring(11, 13));
                int sx = Integer.parseInt(input.get(i).substring(14, 16));
                int sy = Integer.parseInt(input.get(i).substring(17, 19));
                ans.setChunk(set.tiles[sx][sy], dx1, dy1, dx2, dy2, true);

            }else if ( (input.get(i).substring(0, 1)).equals("!") ){//&& !((input.get(i).substring(1, 2)).equals("@")) ) {//Blocked Point
                int dx = Integer.parseInt(input.get(i).substring(1, 3));
                int dy = Integer.parseInt(input.get(i).substring(4, 6));
                int sx = Integer.parseInt(input.get(i).substring(7, 9));
                int sy = Integer.parseInt(input.get(i).substring(10, 12));
                ans.setTile(set.tiles[sx][sy], dx, dy, true);

            } else {//Passable point
                int dx = Integer.parseInt(input.get(i).substring(0, 2));
                int dy = Integer.parseInt(input.get(i).substring(3, 5));
                int sx = Integer.parseInt(input.get(i).substring(6, 8));
                int sy = Integer.parseInt(input.get(i).substring(9, 11));
                ans.setTile(set.tiles[sx][sy], dx, dy);
            }
        }
        return ans;
    }
}
