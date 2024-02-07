package com.vo;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;

import java.util.ArrayList;


public class WumpusWorld {
    //Anything less than 10 is black tile - fog of war
    public static int  Ground = 0, Spider = -1 , Pit = -2, Wumpus = -3, Gold = 14;
    public static int               Web = -11, Wind = -12, Stink = -13, Glitter = 4;
    public static boolean texturePlace = false;

    boolean visible[][] = new boolean[10][10];
    int world [][] = {
            {0,0,0,0,0,0,0,0,0,0},
            {0,0,0,0,0,0,0,0,0,0},
            {0,0,0,0,0,0,0,0,0,0},
            {0,0,0,0,0,0,0,0,0,0},
            {0,0,0,0,0,0,0,0,0,0},
            {0,0,0,0,0,0,0,0,0,0},
            {0,0,0,0,0,0,0,0,0,0},
            {0,0,0,0,0,0,0,0,0,0},
            {0,0,0,0,0,0,0,0,0,0},
            {0,0,0,0,0,0,0,0,0,0}
    };

    private boolean showFog = true;

    public boolean isShowFog() {
        return showFog;
    }

    public void setShowFog(boolean showFog) {
        this.showFog = showFog;
    }

    public static Texture emptyChest, glitterTile, goldTile, pitTile, spiderTile, stinkTile, webTile, windTile, wumpusTile, groundTile;

    public WumpusWorld(){
        emptyChest = new Texture("emptyChest.png");
        glitterTile= new Texture("glitterTile.png");
        goldTile = new Texture("goldTile.png");
        pitTile = new Texture("pitTile.png");
        spiderTile = new Texture("spiderTile.png");
        stinkTile = new Texture("stinkTile.png");
        webTile = new Texture("webTile.png");
        windTile = new Texture("windTile.png");
        wumpusTile = new Texture("wumpusTile.png");
        groundTile = new Texture("groundTile.png");

    }

    public ArrayList<Location> getValidNeighbors(Location loc){
        Location above = new Location(loc.getRow()-1, loc.getCol());
        Location below = new Location(loc.getRow()+1, loc.getCol());
        Location left = new Location(loc.getRow(), loc.getCol()-1);
        Location right = new Location(loc.getRow(), loc.getCol()+1);

        ArrayList<Location> locs = new ArrayList<>();
        if(isValid(above)){
            locs.add(above);
        }
        if(isValid(below)){
            locs.add(below);
        }
        if(isValid(left)){
            locs.add(left);
        }
        if(isValid(right)){
            locs.add(right);
        }
        return locs;
    }


    public void addHints(Location loc){
        ArrayList<Location> list = getValidNeighbors(loc);
        System.out.println(list.size());
        for(Location t: list){
            if(world[loc.getRow()][loc.getCol()] == Ground){
                world[t.getRow()][t.getCol()] = Ground;
            }
            else
            world[t.getRow()][t.getCol()] = world[loc.getRow()][loc.getCol()]- 10;
            }
    }
    public static int convertLocToX(Location loc){
       return (loc.getCol()*50)+20;
    }
    public static int convertLocToY(Location loc){
        return 718-(loc.getRow()*50)+18;
    }

    //return a Location (row)(col) that corresponds the {x,y} coordinate
    public static Location convertCoordsToLoc(int x, int y){
        int col;
        int row;

        col = (x-20)/50;
        row = (y-18)/50;


        return new Location(row, col);

    }

    public void placeTexture(Texture t, int x, int y){
        Location loc = convertCoordsToLoc(x,y);
        if(isValid(loc)){
            if(t.toString().equals("spiderTile.png")){
                world[loc.getRow()][loc.getCol()] = Spider;
            }
            else if(t.toString().equals("pitTile.png")){
                world[loc.getRow()][loc.getCol()] = Pit;
            }
            else if(t.toString().equals("wumpusTile.png")){
                world[loc.getRow()][loc.getCol()] = Wumpus;
            }
            else if(t.toString().equals("goldTile.png")){
                world[loc.getRow()][loc.getCol()] = Gold;
            }
            else if(t.toString().equals("groundTile.png")){
                world[loc.getRow()][loc.getCol()] = Ground;
            }
        }
        addHints(loc);
    }
    public void setVisible(Location loc){
        if(isValid(loc)){
            visible[loc.getRow()][loc.getCol()] = true;
        }
    }
    public int getValue(Location loc){
        return world[loc.getRow()][loc.getCol()];
    }
    public boolean isVisible(Location loc) {
        if (isValid(loc)) {
            return (visible[loc.getRow()][loc.getCol()]);
        } else {
            return false;
        }
    }

    public void draw(SpriteBatch spriteBatch){
        int xOffset = 20;
        for(int row=0; row < world.length; row++){
            for(int col=0; col <world[row].length; col++){
                if(visible[row][col]||showFog == false) {
                    //row = y, col = x
                    if (world[row][col] == Ground) {
                        spriteBatch.draw(groundTile, xOffset + col * 50, 700 - row * 50);
                    } else if (world[row][col] == Spider) {
                        spriteBatch.draw(spiderTile, xOffset + col * 50, 700 - row * 50);
                    } else if (world[row][col] == Glitter) {
                        spriteBatch.draw(glitterTile, xOffset + col * 50, 700 - row * 50);
                    } else if (world[row][col] == Gold) {
                        spriteBatch.draw(goldTile, xOffset + col * 50, 700 - row * 50);
                    } else if (world[row][col] == Pit) {
                        spriteBatch.draw(pitTile, xOffset + col * 50, 700 - row * 50);
                    } else if (world[row][col] == Web) {
                        spriteBatch.draw(webTile, xOffset + col * 50, 700 - row * 50);
                    } else if (world[row][col] == Wind) {
                        spriteBatch.draw(windTile, xOffset + col * 50, 700 - row * 50);
                    } else if (world[row][col] == Wumpus) {
                        spriteBatch.draw(wumpusTile, xOffset + col * 50, 700 - row * 50);
                    } else if (world[row][col] == Stink) {
                        spriteBatch.draw(stinkTile, xOffset + col * 50, 700 - row * 50);
                    }
                }


            }
        }
    }
    public boolean isValid(Location loc) {
        return loc.getRow() >= 0 && loc.getRow() < world.length && loc.getCol() >= 0 && loc.getCol() < world[(loc.getRow())].length;
    }
}
