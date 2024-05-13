package com.mygdx.platventure.vues;

import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.utils.ScreenUtils;
import com.badlogic.gdx.utils.Timer;
import com.mygdx.platventure.PlatVentureScreen;
import com.mygdx.platventure.sons.Sounds;

public class StartScreen implements Screen {

    private final PlatVentureScreen platVentureScreen;
    private final Texture img = new Texture("resources/images/Intro.png");
    private final SpriteBatch sb;

    public StartScreen(PlatVentureScreen platVentureGame) {
        this.platVentureScreen = platVentureGame;
        this.sb = PlatVentureScreen.getInstance().getBatch();
    }
    @Override
    public void show() {
        Timer.schedule(new Timer.Task() {
            @Override
            public void run() {
                platVentureScreen.setPlatVentureScreen();
            }
        }, 4f);
        Sounds.WIN.play();

    }

    @Override
    public void render(float delta) {
        ScreenUtils.clear(1, 0, 0, 1);
        sb.begin();
        sb.draw(img, 0, 0);
        sb.end();

    }

    @Override
    public void resize(int width, int height) {

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
        sb.dispose();
        img.dispose();
    }
}
