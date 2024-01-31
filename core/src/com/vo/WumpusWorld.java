package com.vo;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;

public class WumpusWorld {
    //Anything less than 10 is black tile - fog of war
    public static int  Ground = 0, Spider = 1 , Pit = 2, Wumpus = 3, Gold = 4;
    public static int               Web = 21, Wind =22, Stink = 23, Glitter = 24;

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
            {1,0,0,0,0,0,0,0,0,0}
    };

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

    //return a Location (row)(col) that corresponds the {x,y} coordinate
    public Location convertCoordsToLoc(int x, int y){
        int col;
        int row;

        col = (x-20)/50;
        row = (y-18)/50;

        if (isValid(new Location(row, col)))
            return new Location(row, col);
        else
            return null;
    }

    public void placeTexture(Texture t, int x, int y){
        Location loc = convertCoordsToLoc(x,y);
        if(isValid(loc)){
            if(t.toString().equals("spiderTile.png")){
                world[loc.getRow()][loc.getCol()] = Spider;
            }
            if(t.toString().equals("pitTile.png")){
                world[loc.getRow()][loc.getCol()] = Pit;
            }
            if(t.toString().equals("wumpusTile.png")){
                world[loc.getRow()][loc.getCol()] = Wumpus;
            }
            if(t.toString().equals("goldTile.png")){
                world[loc.getRow()][loc.getCol()] = Gold;
            }
            if(t.toString().equals("groundTile.png")){
                world[loc.getRow()][loc.getCol()] = Ground;
            }
        }
    }
    public void draw(SpriteBatch spriteBatch){
        int xOffset = 20;
        for(int row=0; row < world.length; row++){
            for(int col=0; col <world[row].length; col++){
                //row = y, col = x
                if(world[row][col] == Ground) {
                    spriteBatch.draw(groundTile, xOffset + col * 50, 700-row * 50);
                }else if(world[row][col] == Spider){
                    spriteBatch.draw(spiderTile, xOffset+ col * 50,700- row*50);
                }else if(world[row][col] == Glitter){
                    spriteBatch.draw(glitterTile, xOffset+ col * 50,700- row*50);
                }
                else if(world[row][col] == Gold){
                    spriteBatch.draw( goldTile, xOffset+ col * 50,700- row*50);
                }
                else if(world[row][col] == Pit){
                    spriteBatch.draw(pitTile, xOffset+ col * 50,700- row*50);
                }
                else if(world[row][col] == Web){
                    spriteBatch.draw(webTile, xOffset+ col * 50,700- row*50);
                }
                else if(world[row][col] == Wind){
                    spriteBatch.draw(windTile, xOffset+ col * 50,700- row*50);
                }
                else if(world[row][col] == Wumpus){
                    spriteBatch.draw(wumpusTile, xOffset+ col * 50,700- row*50);
                }
                else if(world[row][col] == Stink){
                    spriteBatch.draw(stinkTile, xOffset+ col * 50,700- row*50);
                }


            }
        }
    }
    public boolean isValid(Location loc) {
        return loc.getRow() >= 0 && loc.getRow() < world.length && loc.getCol() >= 0 && loc.getCol() < world[(loc.getRow())].length;
    }
}
