package com.mygdx.platventure.model.utils;

import com.badlogic.gdx.files.FileHandle;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.World;
import com.mygdx.platventure.model.Gem;
import com.mygdx.platventure.model.Player;
import com.mygdx.platventure.model.blocs.BlocSprite;
import com.mygdx.platventure.model.blocs.TypeDeBloc;
import com.mygdx.platventure.model.blocs.UIBloc;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

public class Map implements Iterable<BlocSprite> {

    private int width;
    private int height;
    private double time;
    private int timeByDefault;

    private String backgroundPath;

    private final List<BlocSprite> blocs = new ArrayList<>();
    private char[][] map;
    private final Player pl = new Player(null, this);
    private final Gem g1 = new Gem(null, this),
            g2 = new Gem(null, this);


    public Map( )
    {}

    public World getPlatVentureWorld(){
        this.setTime(this.timeByDefault);
        blocs.clear();
        getPl().resetScore();
        World w = new World(new Vector2(0,-10),false);

        for(int k=0; k<getWidth(); k++){
            for(int l=0; l<getHeight(); l++) {
                UIBloc blocToCreate = null;
                float posY = getHeight() - l - 1;
                char tmp = getElementAt(k, l);
                /*if (tmp == 'V') {
                    blocToCreate = UIBloc.AIR;
                } else*/ if (tmp == 'A'){
                    blocToCreate = UIBloc.BRICK_A;
                } else if (tmp == 'B'){
                    blocToCreate = UIBloc.BRICK_B;
                } else if (tmp == 'C'){
                    blocToCreate = UIBloc.BRICK_C;
                } else if (tmp == 'D'){
                    blocToCreate = UIBloc.BRICK_D;
                } else if (tmp == 'E'){
                    blocToCreate = UIBloc.BRICK_E;
                } else if (tmp == 'F'){
                    blocToCreate = UIBloc.BRICK_F;
                } else if (tmp == 'G'){
                    blocToCreate = UIBloc.BRICK_G;
                } else if (tmp == 'H'){
                    blocToCreate = UIBloc.BRICK_H;
                } else if (tmp == 'I'){
                    blocToCreate = UIBloc.BRICK_I;
                }
                else if(tmp == 'J') {
                    blocToCreate = UIBloc.PLATEFORME_GAUCHE;
                }
                else if(tmp == 'K') {
                    blocToCreate = UIBloc.PLATEFORME_MILIEU;
                }
                else if(tmp == 'L') {
                    blocToCreate = UIBloc.PLATEFORME_DROITE;
                }
                else if(tmp == 'P') {
                    blocToCreate = UIBloc.PLAYER;
                }
                else if(tmp == '1') {
                    blocToCreate = UIBloc.GEMME1;
                }
                else if(tmp == '2') {
                    blocToCreate = UIBloc.GEMME2;
                }
                else if(tmp == 'W') {
                    blocToCreate = UIBloc.EAU;
                }
                else if(tmp == 'Z') {
                    if(k-1<0){
                        blocToCreate = UIBloc.SORTIE_GAUCHE;
                    }
                    else{
                        blocToCreate = UIBloc.SORTIE_DROITE;
                    }
                    //blocToCreate = UIBloc.SORTIE;
                }

                if(blocToCreate != null) {
                    if (blocToCreate.getType().equals(TypeDeBloc.SORTIE) && (map[k][l] == 'I' || map[k][l] == 'J'
                            || map[k][l] == 'K' || map[k][l] == 'W')
                    )
                        posY = posY - 0.25f;
                    Body plBody = blocToCreate.ajouterAuMonde(w, k, posY);

                    BlocSprite bs = new BlocSprite(plBody, blocToCreate);
                    blocs.add(bs);
                    if (blocToCreate == UIBloc.PLAYER)
                        pl.setPlayerbs(bs);

                }

            }
        }
        return w;
    }


    public char getElementAt(int i, int j){
        return getMap()[i][j];
    }


    public void getMapFromFich(FileHandle fH){
        String[] lines, numbers;
        lines = fH.readString().split("\\r?\\n");
        numbers = lines[0].split(" ");
        int height = Integer.parseInt(numbers[1]),
        width = Integer.parseInt(numbers[0]),
        time = Integer.parseInt(numbers[2]);
        char[][] mapChar = new char[width][height];
        for(int j = 0; j < width; j++) {
            for(int i = 1; i <= height; i++)
                mapChar[j][i-1] = lines[i].charAt(j);
        }

        this.width = width;
        this.height = height;
        this.backgroundPath = lines[lines.length - 1];
        this.map = mapChar;
        this.timeByDefault = time;

    }

    @Override
    public String toString()
    {
        StringBuilder sb = new StringBuilder();

        sb.append("Map : \n")
        .append(width).append(" ")
        .append(height).append(" ")
        .append(time).append("\n");
        for(int i = 0; i < height; i++)
        {
            for(int j = 0; j < width; j++)
                sb.append(map[i][j]);
            sb.append("\n");
        }
        sb.append(backgroundPath);
        return sb.toString();
    }

    public int getHeight() {
        return height;
    }

    public int getWidth() {
        return width;
    }

    public void setWidth(int width) {
        this.width = width;
    }

    public void setHeight(int height) {
        this.height = height;
    }

    public double getTime() {
        return time;
    }

    public double updateTime(double timeToRemove){
        if(time - timeToRemove >= 0)
        {
            time -= timeToRemove;
        }
        return time - timeToRemove;
    }

    public void setTime(int time) {
        this.time = time;
    }

    public String getBackgroundPath() {
        return backgroundPath;
    }

    public void setBackgroundPath(String backgroundPath) {
        this.backgroundPath = backgroundPath;
    }

    public char[][] getMap() {
        return map;
    }

    public void setMap(char[][] map) {
        this.map = map;
    }

    public Player getPl() {
        return pl;
    }

    public Gem getG1() {
        return g1;
    }

    public Gem getG2() {
        return g2;
    }

    public void updatePlayerPosition(){
        pl.getPlayerbs().updatePosition();
    }

    public BlocSprite getBlocSFromBody(Body bs){
        for(BlocSprite b : this.blocs){
            if(b.getBody() == bs)
                return b;
        }
        return null;
    }

    public void removeBlocS(BlocSprite bs){
        blocs.remove(bs);
    }

    @Override
    public Iterator<BlocSprite> iterator(){
        return blocs.iterator();
    }

}
