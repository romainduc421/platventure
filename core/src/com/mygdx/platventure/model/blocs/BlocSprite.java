package com.mygdx.platventure.model.blocs;


import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.physics.box2d.Body;

public class BlocSprite {
    private final UIBloc uiBloc;
    private final Body body;
    private final Sprite sprite;
    private float elapsedTime = 0f;
    private Animation<TextureRegion> regionAnimation= null;

    public BlocSprite(Body body, UIBloc typeDeBloc){
        this.body = body;
        this.uiBloc = typeDeBloc;
        this.sprite = new Sprite(typeDeBloc.getTextures()[0]);
        this.sprite.setSize(typeDeBloc.getWidth(), typeDeBloc.getHeight());
        this.sprite.setCenter(body.getPosition().x,body.getPosition().y - ((typeDeBloc.getHeight()==0.75f)? 0.125f : 0f));
        if(typeDeBloc.equals(UIBloc.SORTIE_GAUCHE))
            this.sprite.setFlip(true,false);
        if(typeDeBloc.isHasAnimation()){
            TextureRegion[] textures;
            if(uiBloc.getTextures().length == 1){
                TextureRegion[][] regions = TextureRegion.split(typeDeBloc.getTextures()[0],56,56);
                textures = new TextureRegion[regions.length];
                int idx = 0;
                for (TextureRegion[] region : regions) {
                    textures[idx++] = region[0];
                }
            }
            else{
                textures = new TextureRegion[typeDeBloc.getTextures().length];
                for(int k = 0; k < typeDeBloc.getTextures().length; k++)
                    textures[k] = new TextureRegion(typeDeBloc.getTextures()[k]);

            }
            regionAnimation = new Animation<>(3f/4.2f, textures);
            regionAnimation.setPlayMode(Animation.PlayMode.LOOP);
        }
    }

    public void updatePosition(){
        sprite.setCenter(body.getPosition().x, body.getPosition().y);
    }

    public Body getBody() {
        return body;
    }

    public Sprite getSprite() {
        return sprite;
    }

    public void updateAnimation( float delta )
    {
        if(regionAnimation != null)
        {
            elapsedTime = elapsedTime +delta;
            getSprite().setRegion(regionAnimation.getKeyFrame(elapsedTime,true));
        }
    }

    public void flip(boolean bo)
    {
        getSprite().setFlip(bo,false);
    }
}
