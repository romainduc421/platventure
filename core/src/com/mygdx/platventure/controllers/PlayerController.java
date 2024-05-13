package com.mygdx.platventure.controllers;

import com.badlogic.gdx.InputProcessor;

public class PlayerController implements InputProcessor {

    private char keyHold = '/';
    //private final Vector2 moveCommands = new Vector2(0f, 0f);

    @Override
    public boolean keyDown(int keycode) {
        /*if(keycode == Input.Keys.UP) {
            moveCommands.y = Constants.MOVE_Y.getInstance();
            return true;
        }
        if(keycode == Input.Keys.LEFT) {
            moveCommands.x = -Constants.MOVE_X.getInstance();
            return true;
        }
        if(keycode == Input.Keys.RIGHT) {
            moveCommands.x = Constants.MOVE_X.getInstance();
            return true;
        }*/
        if(keyHold != '/')
        {
            return false;
        }
        keyHold = (char)keycode;
        return true;

    }

    @Override
    public boolean keyUp(int keycode) {
        /*if(keycode == Input.Keys.LEFT || keycode == Input.Keys.RIGHT)
            moveCommands.x = 0f;
        if(keycode == Input.Keys.UP)
            moveCommands.y = 0f;*/
        keyHold = '/';
        return true;
    }

    @Override
    public boolean keyTyped(char character) {
        return false;
    }

    @Override
    public boolean touchDown(int screenX, int screenY, int pointer, int button) {
        /*if(Gdx.graphics.getWidth()>=Gdx.input.getX() && Gdx.input.isTouched()){
            keyHold = Input.Keys.LEFT;
        }
        if(screenX > Gdx.graphics.getWidth()/2){
            keyHold = Input.Keys.RIGHT;
        }*/
        return false;
    }

    @Override
    public boolean touchUp(int screenX, int screenY, int pointer, int button) {
        return false;
    }

    @Override
    public boolean touchDragged(int screenX, int screenY, int pointer) {
        return false;
    }

    @Override
    public boolean mouseMoved(int screenX, int screenY) {
        return false;
    }

    @Override
    public boolean scrolled(float amountX, float amountY) {
        return false;
    }

    public char getKeyHold(){
        return keyHold;
    }

}
