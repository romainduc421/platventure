package com.mygdx.platventure.model.utils;
import com.badlogic.gdx.Gdx;

public enum Constants {


    CAMERA_SIZE(16f),

    MOVE_X(1f),
    MOVE_Y(50f),

    OBJECT_DENSITY(1f),
    OBJECT_RESTITUTION(0.1f),
    OBJECT_FRICTION(0.25f),

    PLAYER_DENSITY(0.5f),
    PLAYER_RESTITUTION(0.1f),
    PLAYER_FRICTION(0.5f);

    //Donne la taille d'une unité en mètre
    //public static float TAILLE_UNITE = 2048/(float) Gdx.graphics.getWidth();
    private final float instance;
    Constants(float cst){
        this.instance = cst;
    }

    public float getInstance() {
        return instance;
    }

}
