package com.mygdx.platventure.model.utils;


public class LevelChoice {
    private final int niveau;

    public LevelChoice(int lvl) {
        niveau = lvl;
    }

    /**
     * Fonction qui va remplir les attributs de la classe
     * à l'aide d'un fichier fourni ou sont stockées les informations
     * sur le niveau.
     */
    public String creerNiveau() {
        String lvl = "resources/levels/level_";

        if (this.niveau < 10)
            lvl = lvl+"00"+this.niveau+".txt";
        else if (this.niveau < 100)
            lvl = lvl+"0"+this.niveau+".txt";
        else
            lvl = lvl+this.niveau+".txt";

        return lvl;
    }

}
