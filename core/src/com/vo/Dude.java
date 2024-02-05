package com.vo;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;

import java.util.ArrayList;

public class Dude {

    private Texture texture;
    private Location myLoc;
    private WumpusWorld world;

    public Dude(Location myLoc, WumpusWorld world) {
        this.myLoc = myLoc;
        this.world = world;
        world.setVisible(myLoc);
        texture = new Texture("guy.png");


    }

    public void draw(SpriteBatch batch) {
        batch.draw(texture, WumpusWorld.convertLocToX(myLoc), WumpusWorld.convertLocToY(myLoc));
    }


    public void moveRight() {
        Location loc = new Location(myLoc.getRow(), myLoc.getCol()+1);
        if(world.isValid(loc)) {
            myLoc.setCol(myLoc.getCol() + 1);
            world.setVisible(myLoc);
        }


    }

    public void moveLeft() {
        Location loc = new Location(myLoc.getRow(), myLoc.getCol()-1);
        if(world.isValid(loc)) {
            myLoc.setCol(myLoc.getCol() - 1);
            world.setVisible(myLoc);
        }

    }

    public void moveUp () {
        Location loc = new Location(myLoc.getRow()-1, myLoc.getCol());
        if(world.isValid(loc)) {
            myLoc.setRow(myLoc.getRow() - 1);
            world.setVisible(myLoc);
        }

    }

    public void moveDown () {
        Location loc = new Location(myLoc.getRow()+1, myLoc.getCol());
        if(world.isValid(loc)) {
            myLoc.setRow(myLoc.getRow() + 1);
            world.setVisible(myLoc);
        }

    }
    public ArrayList<Location> getPossibleMoves(){
        Location above = new Location(myLoc.getRow()-1, myLoc.getCol());
        Location below = new Location(myLoc.getRow()+1, myLoc.getCol());
        Location left = new Location(myLoc.getRow(), myLoc.getCol()-1);
        Location right = new Location(myLoc.getRow(), myLoc.getCol()+1);

        ArrayList<Location> locs = new ArrayList<>();
        if(world.isValid(above)){
            locs.add(above);
        }
        if(world.isValid(below)){
            locs.add(below);
        }
        if(world.isValid(left)){
            locs.add(left);
        }
        if(world.isValid(right)){
            locs.add(right);
        }
        return locs;
    }

    public void setMyLoc(Location myLoc) {
        this.myLoc = myLoc;
    }

    public Location getMyLoc() {
        return myLoc;
    }
}

