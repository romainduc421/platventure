package com.mygdx.platventure.model;

import com.badlogic.gdx.physics.box2d.*;
import com.mygdx.platventure.model.blocs.BlocSprite;
import com.mygdx.platventure.model.utils.Map;

public class Player {
    /*private PolygonShape shapeDiamond;
    private CircleShape shapeCircle;
    private final int hauteur = 500/4;
    private final int largeur = 290/4;
    private boolean jumping = false;
    private BodyType lastContact;
    private Body body;*/

    private int score;
    private final Map charMap;
    private BlocSprite playerbs;


    public Player(BlocSprite playerBlocS, Map map)
    {
        this.playerbs = playerBlocS;
        this.charMap = map;
    }


    /*@Override
    public void setBodyDef() {
        this.bodyDef = new BodyDef();
        this.bodyDef.type = BodyDef.BodyType.DynamicBody;
        this.bodyDef.position.set(this.getPosition());
    }

    @Override
    public void setFixture() {
        if ((this.bodyDef != null) && (this.body != null)) {
            FixtureDef fixtureDef = new FixtureDef();
            fixtureDef.shape = this.shapeDiamond;
            fixtureDef.density = 1;
            fixtureDef.restitution = 0.1f;
            fixtureDef.friction = 0.25f;
            getBody().createFixture(fixtureDef);

            FixtureDef fixtureDef2 = new FixtureDef();
            fixtureDef2.shape = this.shapeCircle;
            fixtureDef2.density = 1;
            fixtureDef2.restitution = 0.1f;
            fixtureDef2.friction = 0.25f;
            getBody().createFixture(fixtureDef2);
            getBody().setTransform(new Vector2(getPosition().x + 25, getPosition().y + 300 ), 0);
        }
        this.shapeDiamond.dispose();
        this.shapeCircle.dispose();
    }*/

    public boolean isOutOfMap(int tolerance) {
        boolean result;

        float x = getBody().getPosition().x;
        float y = getBody().getPosition().y;

        result = (x < -tolerance || y < -tolerance || x > charMap.getWidth() + tolerance || y > charMap.getHeight() + tolerance);
        return result;
    }
    public int getScore() {
        return score;
    }

    public Body getBody() {
        return playerbs.getBody();
    }

    public BlocSprite getPlayerbs() {
        return playerbs;
    }

    public void setPlayerbs(BlocSprite playerbs) {
        this.playerbs = playerbs;
    }

    public void updateScore(int i) {
        score += i;
    }

    public  void resetScore() {
        score = 0;
    }


}
