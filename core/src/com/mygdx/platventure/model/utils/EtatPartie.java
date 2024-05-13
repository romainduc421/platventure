package com.mygdx.platventure.model.utils;

import com.mygdx.platventure.PlatVentureScreen;
import com.mygdx.platventure.sons.Sounds;
import com.mygdx.platventure.vues.MapView;

public enum EtatPartie {
    PLAYING(0,-1),
    WIN(1,2),
    LOOSE(2,2);

    private final float secondesDsLetat;
    private final int etat;

    EtatPartie(int etat, float sec){
        this.etat = etat;
        this.secondesDsLetat = sec;
    }

    public float getSecondesDsLetat() {
        return secondesDsLetat;
    }

    public int getEtat() {
        return etat;
    }

    public void executeAfterEtat(){
        MapView mView = PlatVentureScreen.getInstance().getMapView();
        if(this == WIN)
            mView.loadLevelUp();
        else if(this == LOOSE)
            mView.resetWorld();
    }

    public void executeBeforeEtat(){
        if(this == WIN)
            Sounds.WIN.play();
        else if(this == LOOSE)
            Sounds.LOOSE.play();
    }
}
