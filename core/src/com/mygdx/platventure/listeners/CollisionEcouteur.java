package com.mygdx.platventure.listeners;

import com.badlogic.gdx.physics.box2d.*;
import com.mygdx.platventure.PlatVentureScreen;
import com.mygdx.platventure.model.Player;
import com.mygdx.platventure.model.blocs.UIBloc;
import com.mygdx.platventure.model.blocs.TypeDeBloc;
import com.mygdx.platventure.model.utils.EtatPartie;
import com.mygdx.platventure.sons.Sounds;
import com.mygdx.platventure.vues.MapView;

public class CollisionEcouteur implements ContactListener {
    @Override
    public void beginContact(Contact contact) {
        UIBloc blockA = (UIBloc) contact.getFixtureA().getBody().getUserData();
        UIBloc blockB = (UIBloc) contact.getFixtureB().getBody().getUserData();
        Body player = null;


        if(blockA.equals(UIBloc.PLAYER)){
            player = contact.getFixtureA().getBody();
        }

        if(blockB.equals(UIBloc.PLAYER)) {
            player = contact.getFixtureB().getBody();
        }

        if(player == null) return;
        PlatVentureScreen platVentureScreen = PlatVentureScreen.getInstance();
        MapView mView = platVentureScreen.getMapView();

        if(blockB.getType().equals(TypeDeBloc.GEMME) || blockA.getType().equals(TypeDeBloc.GEMME))
        {
            mView.desactivateBody((blockA.equals(UIBloc.PLAYER)) ? contact.getFixtureB().getBody():contact.getFixtureA().getBody());
            Sounds.COLLECT_GEM.play();
            Player player1 = mView.getPlayer();
            if(!(!(blockA.equals(UIBloc.GEMME1)) && !(blockB.equals(UIBloc.GEMME1)))){
                player1.updateScore(1);
            }
            if(!(!(blockA.equals(UIBloc.GEMME2)) && !(blockB.equals(UIBloc.GEMME2)))){
                player1.updateScore(2);
            }
        }

        if(blockB.equals(UIBloc.EAU) || blockA.equals(UIBloc.EAU))
        {
            Sounds.WATER.play();
        }

        /*if(blockA.getType().equals(TypeDeBloc.SORTIE) || blockB.getType().equals(TypeDeBloc.SORTIE)){
            Sounds.WIN.play();
            mView.loadLevelUp();
        }*/

        if(blockB.getType().equals(TypeDeBloc.SOL) || blockA.getType().equals(TypeDeBloc.SOL))
        {
            if(player.getLinearVelocity().len2() >= 3)
                Sounds.COLLISION.play();
        }

    }

    @Override
    public void endContact(Contact contact){
        Body pl = null;
        UIBloc blockA = (UIBloc) contact.getFixtureA().getBody().getUserData();
        UIBloc blockB = (UIBloc) contact.getFixtureB().getBody().getUserData();
        if(blockA.equals(UIBloc.PLAYER))
            pl = contact.getFixtureA().getBody();
        if(blockB.equals(UIBloc.PLAYER))
            pl = contact.getFixtureB().getBody();
        if(pl==null) return;
        PlatVentureScreen platVentureScreen = PlatVentureScreen.getInstance();
        MapView mView  = platVentureScreen.getMapView();
        Player player = mView.getPlayer();
        if((blockB.getType().equals(TypeDeBloc.SORTIE) || blockA.getType().equals(TypeDeBloc.SORTIE))&& player.isOutOfMap(0)){
            mView.initEtat(EtatPartie.WIN);
        }
    }

    @Override
    public void preSolve(Contact contact, Manifold oldManifold) {

    }

    @Override
    public void postSolve(Contact contact, ContactImpulse impulse) {

    }
}
