package com.mygdx.platventure.model;

import com.badlogic.gdx.physics.box2d.*;
import com.mygdx.platventure.model.blocs.BlocSprite;
import com.mygdx.platventure.model.utils.Map;

public class Gem {

    private Map charMap;
    private BlocSprite Gembs;


    public Gem(BlocSprite gemBlocS, Map map)
    {
        this.Gembs= gemBlocS;
        this.charMap = map;
    }


    public Body getBody() {
        return Gembs.getBody();
    }

    public BlocSprite getGembs() {
        return Gembs;
    }

    public void setGembs(BlocSprite Gembs) {
        this.Gembs = Gembs;
    }



}
