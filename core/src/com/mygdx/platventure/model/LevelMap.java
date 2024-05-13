package com.mygdx.platventure.model;

import com.badlogic.gdx.Gdx;
import com.mygdx.platventure.model.utils.Map;

public class LevelMap {


    private final Map map = new Map();

    public LevelMap() {
    }

    public void init(String levelPath) {
        map.getMapFromFich(Gdx.files.internal(levelPath));
    }

    public Map getMap() {
        return map;
    }
}
