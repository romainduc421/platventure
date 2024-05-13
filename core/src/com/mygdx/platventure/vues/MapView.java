package com.mygdx.platventure.vues;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.GlyphLayout;
import com.badlogic.gdx.graphics.g2d.freetype.FreeTypeFontGenerator;
import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.Box2DDebugRenderer;
import com.badlogic.gdx.physics.box2d.World;
import com.badlogic.gdx.utils.ScreenUtils;
import com.mygdx.platventure.PlatVentureScreen;
import com.mygdx.platventure.controllers.PlayerController;
import com.mygdx.platventure.listeners.CollisionEcouteur;
import com.mygdx.platventure.model.LevelMap;
import com.mygdx.platventure.model.Player;
import com.mygdx.platventure.model.blocs.BlocSprite;
import com.mygdx.platventure.model.utils.*;
import com.mygdx.platventure.sons.Sounds;

import java.util.HashSet;
import java.util.Set;

public class MapView implements Screen {

    private Texture backGround;
    private Map map;
    private World w;
    private Box2DDebugRenderer box2DDebugRenderer;
    private OrthographicCamera orthographicCamera, sceneCamera;
    private final LevelMap levelMap = new LevelMap();
    private CollisionEcouteur collisionEcouteur;
    private BitmapFont font;
    private final PlayerController playerController = new PlayerController();
    private final Set<Body> bodytoDesactivate = new HashSet<>(4);
    private int loadedLevel = 0;
    private float tempEtatHandler=0f;
    private EtatPartie gamestate = EtatPartie.PLAYING;
    private boolean debugMode = false;
    private PlayerView playerView;

    @Override
    public void show() {

        FreeTypeFontGenerator freeTypeFontGenerator = new FreeTypeFontGenerator(Gdx.files.internal("resources/fonts/Comic_Sans_MS_Bold.ttf"));
        FreeTypeFontGenerator.FreeTypeFontParameter param = new FreeTypeFontGenerator.FreeTypeFontParameter();
        param.borderWidth = 3f/1024f*Gdx.graphics.getWidth();
        param.size = (int)(60f*Gdx.graphics.getWidth()/1024f);
        param.borderColor = Color.BLACK;
        param.color = Color.YELLOW;
        this.font = freeTypeFontGenerator.generateFont(param);
        sceneCamera = new OrthographicCamera();
        levelMap.init((new LevelChoice(++loadedLevel)).creerNiveau());
        map = levelMap.getMap();
        w = map.getPlatVentureWorld();
        playerView = new PlayerView(map.getPl());
        backGround = new Texture(Gdx.files.internal("resources/images/"+map.getBackgroundPath()));
        orthographicCamera = PlatVentureScreen.getInstance().getCam();
        box2DDebugRenderer = new Box2DDebugRenderer(true,true,false,true,true,true);
        //spriteBatch = new SpriteBatch();
        Gdx.input.setInputProcessor(playerController);
        collisionEcouteur = new CollisionEcouteur();
        sceneCamera.setToOrtho(false, Gdx.graphics.getWidth(), Gdx.graphics.getHeight());
        w.setContactListener(collisionEcouteur);
        //this.orthographicCamera.position.set(new Vector2(16/2f, orthographicCamera.viewportHeight/2f),0);
    }

    @Override
    public void render(float delta) {
        ScreenUtils.clear(-1,0,0,1);
        float deltaTime = Gdx.graphics.getDeltaTime();
        Batch spriteBatch = PlatVentureScreen.getInstance().getBatch();


        if(debugMode)
            box2DDebugRenderer.render(w, orthographicCamera.combined);
        else
            spriteBatch.setProjectionMatrix(orthographicCamera.combined);
        playerView.updateEtat();
        w.step(deltaTime, 6,2);
        Body playerBody = map.getPl().getBody();

        boolean left, right, up, down;
        left=right=up=down=false;

        if((playerController.getKeyHold() == Input.Keys.A) || (playerController.getKeyHold() == Input.Keys.LEFT) || (playerController.getKeyHold() == Input.Keys.DPAD_LEFT))
        {
            left = true;
        }
        else if((playerController.getKeyHold() == Input.Keys.D) || (playerController.getKeyHold() == Input.Keys.RIGHT) || (playerController.getKeyHold() == Input.Keys.DPAD_RIGHT))
        {
            right = true;
        }
        else if((playerController.getKeyHold() == Input.Keys.W) || (playerController.getKeyHold() == Input.Keys.UP) || (playerController.getKeyHold() == Input.Keys.DPAD_UP))
        {
            up = true;
        }
        else if((playerController.getKeyHold() == Input.Keys.S) || (playerController.getKeyHold() == Input.Keys.DOWN) || (playerController.getKeyHold() == Input.Keys.DPAD_DOWN))
        {
            down = true;
        }
        else if(playerController.getKeyHold() == Input.Keys.F1)
            debugMode = true;
        else if(playerController.getKeyHold() == Input.Keys.F2)
            debugMode = false;

        if(Gdx.input.isTouched()) {
            if (Gdx.input.isTouched(1)||Gdx.input.getX() > Gdx.graphics.getWidth() * 0.3f && Gdx.input.getX() < Gdx.graphics.getWidth() * 0.7f && Gdx.input.getY() <= Gdx.graphics.getHeight() * 0.5f) {
                up = true;
            } else if (Gdx.input.getX() > Gdx.graphics.getWidth() * 0.02f && Gdx.input.getX() < Gdx.graphics.getWidth() * 0.3f) {
                left = true;
            } else if (Gdx.input.getX() > Gdx.graphics.getWidth() * 0.7f && Gdx.input.getX() < Gdx.graphics.getWidth() * 1.0f) {
                right = true;
            } else if (Gdx.input.getX() > Gdx.graphics.getWidth() * 0.3f && Gdx.input.getX() < Gdx.graphics.getWidth() * 0.7f && Gdx.input.getY() >= Gdx.graphics.getHeight() * 0.5f){
                down = true;
            }
        }
        if(playerView.getEtatJoueur()!= EtatJoueur.JUMPING)
        {
            if(right) {
                playerBody.applyForceToCenter(1.0f, 0f, false);
                map.getPl().getPlayerbs().flip(false);
            }
            if(left) {
                playerBody.applyForceToCenter(-1.0f, 0f, false);
                map.getPl().getPlayerbs().flip(true);
            }
            if(down)
                playerBody.applyForceToCenter(0f,0.3f,false);

            if(up)
                playerBody.applyForceToCenter(0f, 29.5f, false);
        }
        resetCamera();
        orthographicCamera.update();
        sceneCamera.update();

        map.updatePlayerPosition();
        double timeLeft = map.updateTime(deltaTime);
        if(!debugMode){
            spriteBatch.begin();
            spriteBatch.draw(backGround,0,0,map.getWidth(),map.getHeight());
            for(BlocSprite bs : map)
            {
                if(bs.getBody().isActive())
                {
                    bs.getSprite().draw(spriteBatch);
                    bs.updateAnimation(deltaTime);
                }
            }
            playerView.updateAnimation(deltaTime);
            spriteBatch.setProjectionMatrix(sceneCamera.projection);
            GlyphLayout glyphLayout = new GlyphLayout(font, "Score : " + map.getPl().getScore());
            if(map.getWidth()>=16) {
                font.draw(spriteBatch, glyphLayout, sceneCamera.viewportWidth / 2f - glyphLayout.width, (sceneCamera.viewportHeight - glyphLayout.height) / 2f);
                glyphLayout.setText(font, String.valueOf(((int) (timeLeft * 10)) / 10f));
                font.draw(spriteBatch, glyphLayout, -glyphLayout.width / 2f, (sceneCamera.viewportHeight - glyphLayout.height) / 2f);
            }
            else{
                font.draw(spriteBatch, glyphLayout, sceneCamera.viewportWidth / 5f - glyphLayout.width, (sceneCamera.viewportHeight - glyphLayout.height) / 5f);
                glyphLayout.setText(font, String.valueOf(((int) (timeLeft * 10)) / 10f));
                font.draw(spriteBatch, glyphLayout, -glyphLayout.width / 2f, (sceneCamera.viewportHeight - glyphLayout.height)/8.2f );
            }
            if(gamestate == EtatPartie.WIN){
                glyphLayout.setText(font,"Bravo ! :-)");
                font.draw(spriteBatch, glyphLayout, -glyphLayout.width/2f, -glyphLayout.height/2f);
            }
            if(gamestate == EtatPartie.LOOSE){
                glyphLayout.setText(font,"Dommage  :'/");
                font.draw(spriteBatch,glyphLayout,-glyphLayout.width/2f, -glyphLayout.height/2f);
            }

            spriteBatch.end();
        }
        if(timeLeft<=7)
            Sounds.ALERT.play();
        if(getPlayer().isOutOfMap(1) || timeLeft <= 0) {
            if(gamestate != EtatPartie.WIN)
                initEtat(EtatPartie.LOOSE);
        }
        if(tempEtatHandler > 0){
            tempEtatHandler = tempEtatHandler - deltaTime;
            if(tempEtatHandler <= 0){
                gamestate.executeAfterEtat();
                initEtat(EtatPartie.PLAYING);
            }
        }
        for(Body b : bodytoDesactivate){
            b.setActive(false);
            bodytoDesactivate.remove(b);
        }
    }

    @Override
    public void resize(int width, int height) {
        if (map.getWidth()<16)
            orthographicCamera.setToOrtho(false, 6.5f, 8f*(float) height/(float)height);
        else{
            orthographicCamera.setToOrtho(false, Constants.CAMERA_SIZE.getInstance(), Constants.CAMERA_SIZE.getInstance() * (float) height / (float) width);
        }
        resetCamera();

    }

    @Override
    public void pause() {

    }

    @Override
    public void resume() {

    }

    @Override
    public void hide() {

    }

    @Override
    public void dispose() {

        box2DDebugRenderer.dispose();
        //spriteBatch.dispose();
        font.dispose();
        w.dispose();
    }


    public void resetCamera()
    {
        // Si le joueur est hors de la map on ne reset pas la orthographicCamera
        if(getMap().getPl().isOutOfMap(0))
            return;
        Body playerBody = map.getPl().getBody();
        float playerX = playerBody.getPosition().x;
        float playerY = playerBody.getPosition().y;

        orthographicCamera.position.set(playerX, playerY, 0);
        boolean collisionRight = playerX + orthographicCamera.viewportWidth / 2f > map.getWidth();
        boolean collisionLeft = playerX - orthographicCamera.viewportWidth / 2f < 0;
        boolean collisionTop = playerY + orthographicCamera.viewportHeight / 2f > map.getHeight();
        boolean collisionBottom = playerY - orthographicCamera.viewportHeight / 2f < 0;

        // Si il y a une collision (la caméra montre une partie extérieure de la map) on repositionne
        if(collisionLeft && collisionRight)
        {
            orthographicCamera.position.set(map.getWidth()/2f, orthographicCamera.position.y, 0);
        }
        else if(collisionRight) {
            orthographicCamera.position.set(map.getWidth()-orthographicCamera.viewportWidth/2f, orthographicCamera.position.y, 0);
        }
        else if(collisionLeft) {
            orthographicCamera.position.set(orthographicCamera.viewportWidth/2f, orthographicCamera.position.y, 0);
        }

        if(map.getHeight() < orthographicCamera.viewportHeight || collisionBottom) {
            orthographicCamera.position.set(orthographicCamera.position.x, orthographicCamera.viewportHeight/2f, 0);
        }
        else if(collisionTop) {
            orthographicCamera.position.set(orthographicCamera.position.x, map.getHeight()-orthographicCamera.viewportHeight/2f, 0);
        }


        orthographicCamera.update();

        /*positionCameraHeight(playerY, worldHeight);
        positionCameraWidth(playerX, worldWidth);

        if (playerY > orthographicCamera.position.y + 1 && (orthographicCamera.position.y + orthographicCamera.viewportHeight/2) < worldHeight ) {
            orthographicCamera.position.set(new Vector2(orthographicCamera.position.x, orthographicCamera.position.y+0.05f), 0);
        } else if (playerY < orthographicCamera.position.y - 1 && orthographicCamera.position.y > orthographicCamera.viewportHeight/2f) {
            orthographicCamera.position.set(new Vector2(orthographicCamera.position.x, orthographicCamera.position.y-0.05f), 0);
        }

        if (playerX > orthographicCamera.position.x + 3 && (orthographicCamera.position.x +orthographicCamera.viewportWidth/2) < worldWidth) {
            orthographicCamera.position.set(new Vector2(orthographicCamera.position.x+0.05f, orthographicCamera.position.y), 0);
        } else if (playerX < orthographicCamera.position.x - 3 && orthographicCamera.position.x > 16/2f) {
            orthographicCamera.position.set(new Vector2(orthographicCamera.position.x-0.05f, orthographicCamera.position.y), 0);
        }
        orthographicCamera.update();
        Array<Vector2> focalPoints = new Array<>();
        for(int k=0; k<Gdx.graphics.getHeight(); k++){
            focalPoints.add(new Vector2(1022f,(float)k));
        }
        for(int l=0; l<Gdx.graphics.getWidth(); l++){
            focalPoints.add(new Vector2((float)l,766f));
        }
        boolean found = MapView.searchFocalPoints(orthographicCamera,focalPoints, playerBody.getPosition().scl(32f), 180);
        if(!found)
        {
            MapView.lerpToTarget(orthographicCamera, new Vector2(playerX, playerY));
        }
        orthographicCamera.update();*/
    }


    public void resetWorld()
    {
        loadedLevel = 0;
        map.getPl().resetScore();
        loadLevelUp();
    }

    public Player getPlayer(){
        return map.getPl();
    }

    public void loadLevelUp(){
        int previousScore = this.map.getPl().getScore();
        if(loadedLevel >= 3)
            this.loadedLevel = 0;
        this.levelMap.init((new LevelChoice(++this.loadedLevel)).creerNiveau());
        if(this.w != null)
            this.w.dispose();
        map = levelMap.getMap();
        this.w = map.getPlatVentureWorld();
        this.w.setContactListener(this.collisionEcouteur);
        this.map.getPl().updateScore(previousScore);
        this.backGround = new Texture(Gdx.files.internal("resources/images/"+this.map.getBackgroundPath()));
    }

    public Map getMap() {
        return map;
    }

    public void initEtat(EtatPartie etat)
    {
        if(this.gamestate != etat){
            etat.executeBeforeEtat();
            this.gamestate = etat;
            this.tempEtatHandler = etat.getSecondesDsLetat();
        }
    }

    public void desactivateBody(Body body)
    {
        bodytoDesactivate.add(body);
    }
}
