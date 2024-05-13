package com.mygdx.platventure.vues;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.physics.box2d.Body;
import com.mygdx.platventure.model.Player;
import com.mygdx.platventure.model.utils.EtatJoueur;

public class PlayerView {

    private final Player player;
    private float animationTime = 0.0f;
    private final Animation<Texture> motionLessAnimation,jumpingAnimation,runningAnimation;
    private EtatJoueur etatJoueur;

    public PlayerView(Player p)
    {
        int k=0;
        this.etatJoueur = EtatJoueur.MOTIONLESS;
        this.player = p;
        Texture[] notmovingTextures = new Texture[10];
        for(; k < 10; k++)
            notmovingTextures[k] = new Texture(Gdx.files.internal("resources/images/Idle__00"+k+".png"));
        motionLessAnimation = new Animation<>(0.1f, notmovingTextures);
        motionLessAnimation.setPlayMode(Animation.PlayMode.LOOP_PINGPONG);
        Texture[]  runTextures = new Texture[10];
        for(k=0; k<10; k++){
            runTextures[k] = new Texture(Gdx.files.internal("resources/images/Run__00"+k+".png"));
        }
        runningAnimation = new Animation<>(0.1f, runTextures);
        runningAnimation.setPlayMode(Animation.PlayMode.LOOP);

        Texture[] jumpingTextures = new Texture[10];
        for(k=0; k<10; k++){
            jumpingTextures[k] = new Texture(Gdx.files.internal("resources/images/Jump__00"+k+".png"));
        }
        jumpingAnimation = new Animation<>(0.1f, jumpingTextures);
        jumpingAnimation.setPlayMode(Animation.PlayMode.LOOP_PINGPONG);
    }


    public EtatJoueur getEtatJoueur() {
        return etatJoueur;
    }

    public Animation<Texture> getMotionLessAnimation() {
        return motionLessAnimation;
    }

    public Animation<Texture> getJumpingAnimation() {
        return jumpingAnimation;
    }

    public Animation<Texture> getRunningAnimation() {
        return runningAnimation;
    }



    public void updateAnimation(float fl){
        animationTime = animationTime+fl;
        Sprite spriteJoueur = player.getPlayerbs().getSprite();
        spriteJoueur.setTexture(getMotionLessAnimation().getKeyFrame(animationTime));
        if(etatJoueur == EtatJoueur.RUN)
            spriteJoueur.setTexture(getRunningAnimation().getKeyFrame(animationTime));
        else if(etatJoueur == EtatJoueur.JUMPING)
            spriteJoueur.setTexture(getJumpingAnimation().getKeyFrame(animationTime));
    }

    public void updateEtat()
    {
        Body bodyPlayer = player.getBody();
        EtatJoueur nextState ;
        nextState = EtatJoueur.MOTIONLESS;
        if( bodyPlayer.getLinearVelocity().x <= -0.000001f || bodyPlayer.getLinearVelocity().x >= 0.000001f )
            nextState = EtatJoueur.RUN;
        if( bodyPlayer.getLinearVelocity().y <= -0.000001f || bodyPlayer.getLinearVelocity().y >= 0.000001f )
            nextState = EtatJoueur.JUMPING;
        if(etatJoueur != nextState){
            animationTime = 0.0f;
            etatJoueur = nextState;
        }
    }
}
