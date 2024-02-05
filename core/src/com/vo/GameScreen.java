package com.vo;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.Camera;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.utils.Array;
import com.badlogic.gdx.utils.viewport.FitViewport;
import com.badlogic.gdx.utils.viewport.Viewport;
import sun.tools.jstack.JStack;

import java.util.ArrayList;
import java.util.Stack;

public class GameScreen implements Screen {

        private static final float WORLD_WIDTH = 1024;
        private static final float WORLD_HEIGHT = 768;

        //Object that allows us to draw all our graphics
        private SpriteBatch spriteBatch;

        //Object that allwos us to draw shapes
        private ShapeRenderer shapeRenderer;

        //Camera to view our virtual world
        private Camera camera;

        //control how the camera views the world
        //zoom in/out? Keep everything scaled?
        private Viewport viewport;

        WumpusWorld wumpusWorld;

        BitmapFont defaultFont;

        Texture selected;
        Texture trophy;

        Dude dude;

        boolean aiRunning = false;
        int score = 1000;

        private Stack<Location> stack = new Stack<>();



        //timer

        private long delay = 100;
        private long nextMove = System.currentTimeMillis() + delay;

        //runs one time, at the very beginning
        //all setup should happen here
        @Override
        public void show() {
            camera = new OrthographicCamera(); //2D camera
            camera.position.set(WORLD_WIDTH/2, WORLD_HEIGHT/2,0);//move the camera
            camera.update();

            //freeze my view to 800x600, no matter the window size
            viewport = new FitViewport(WORLD_WIDTH, WORLD_HEIGHT, camera);

            spriteBatch = new SpriteBatch();

            shapeRenderer = new ShapeRenderer();
            shapeRenderer.setAutoShapeType(true); //???, I just know that this was the solution to an annoying problem

            wumpusWorld = new WumpusWorld();
            defaultFont = new BitmapFont();
            dude = new Dude(new Location(9,0),wumpusWorld);
            trophy = new Texture("trophy.png");
        }

        public void clearScreen() {
            Gdx.gl.glClearColor(0,0,0,1);
            Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
        }
        public void handleMouseClick() {
            int x = 650;
            int startY = 120;

            if (Gdx.input.isButtonJustPressed(Input.Buttons.LEFT)) {


                int mouse_x = Gdx.input.getX();
                int mouse_y = Gdx.input.getY();
                if (selected == null) {
                    if (mouse_x >= x && mouse_x <= x + 50 && mouse_y >= startY && mouse_y <= startY + 50) {
                        selected = WumpusWorld.spiderTile;
                    } else if (mouse_x >= x && mouse_x <= x + 50 && mouse_y >= startY + 60 && mouse_y <= startY + 110) {
                        selected = WumpusWorld.pitTile;
                    } else if (mouse_x >= x && mouse_x <= x + 50 && mouse_y >= startY + 120 && mouse_y <= startY + 170) {
                        selected = WumpusWorld.wumpusTile;
                    } else if (mouse_x >= x && mouse_x <= x + 50 && mouse_y >= startY + 180 && mouse_y <= startY + 230) {
                        selected = WumpusWorld.goldTile;
                    } else if (mouse_x >= x && mouse_x <= x + 50 && mouse_y >= startY + 240 && mouse_y <= startY + 290) {
                        selected = WumpusWorld.groundTile;
                    }
                    else if (mouse_x >= x && mouse_x <= x + 50 && mouse_y >= startY + 240 && mouse_y <= startY + 350) {
                        aiRunning = !aiRunning;
                    }
                } else {
                    wumpusWorld.placeTexture(selected, mouse_x, mouse_y);
                    selected = null;
                }
            }

        }
        public void AI1(){
            ArrayList<Location> validLocation = dude.getPossibleMoves();




            wumpusWorld.setVisible(dude.getMyLoc());
            stack.add(dude.getMyLoc());
            score--;
        }
        public void handleKeyPresses(){
            if(Gdx.input.isKeyJustPressed(Input.Keys.W)){
                dude.moveUp();
                score--;
            }
            if(Gdx.input.isKeyJustPressed(Input.Keys.S)){
                dude.moveDown();
                score--;
            }
            if(Gdx.input.isKeyJustPressed(Input.Keys.D)){
                dude.moveRight();
                score--;
            }
            if(Gdx.input.isKeyJustPressed(Input.Keys.A)){
                dude.moveLeft();
                score--;
            }
            if(Gdx.input.isKeyJustPressed(Input.Keys.SPACE)){
                wumpusWorld.setShowFog(!wumpusWorld.isShowFog());
            }

        }
        public void drawToolbar(){
            int startX=650;//+to the right
            int startY=120;//+50 to the bottom, +60 to next tile

            spriteBatch.draw(WumpusWorld.spiderTile,650,600);//650,120|700,170
            spriteBatch.draw(WumpusWorld.pitTile,650,540);
            spriteBatch.draw(WumpusWorld.wumpusTile,650,480);
            spriteBatch.draw(WumpusWorld.goldTile,650,420);
            spriteBatch.draw(WumpusWorld.groundTile,650,360);
            spriteBatch.draw(trophy,650,300);



            defaultFont.draw(spriteBatch, Gdx.input.getX() + ", " + Gdx.input.getY(),800,600);
            defaultFont.draw(spriteBatch, "Score: " + score,200,200);

            if(selected != null){
                spriteBatch.draw(selected, Gdx.input.getX()-25, WORLD_HEIGHT - 25 - Gdx.input.getY()  );
            }
        }

        //this method runs as fast as it can, repeatedly, constantly looped
        @Override
        public void render(float delta) {
            clearScreen();

            //User Input
            handleMouseClick();
            handleKeyPresses();



            //AI
            if(aiRunning && nextMove < System.currentTimeMillis()){
                AI1();
                nextMove = System.currentTimeMillis() + delay;
            }

            //all drawing of shapes MUST be in between begin/end
            shapeRenderer.begin();
            shapeRenderer.setColor(1,1,0,1);
            shapeRenderer.circle(30,30,30);
            shapeRenderer.end();

            //all drawing of graphics MUST be in between begin/end
            spriteBatch.begin();
            wumpusWorld.draw(spriteBatch);
            drawToolbar();
            dude.draw(spriteBatch);
            spriteBatch.end();


        }

        @Override
        public void resize(int width, int height) {
            viewport.update(width,height);
        }

        @Override
        public void pause() {

        }

        @Override
        public void resume() {

        }

        @Override
        public void hide() {

        }

        @Override
        public void dispose() {
            spriteBatch.dispose();
            shapeRenderer.dispose();
        }
    }
