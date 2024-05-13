package com.mygdx.platventure.sons;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.audio.Music;
import com.badlogic.gdx.audio.Sound;

public enum Sounds {

    ALERT(Gdx.audio.newMusic(Gdx.files.internal("resources/sounds/alert.ogg"))),
    COLLECT_GEM(Gdx.audio.newSound(Gdx.files.internal("resources/sounds/gem.ogg"))),
    COLLISION(Gdx.audio.newSound(Gdx.files.internal("resources/sounds/collision.ogg"))),
    WATER(Gdx.audio.newMusic(Gdx.files.internal("resources/sounds/plouf.ogg"))),
    LOOSE(Gdx.audio.newMusic(Gdx.files.internal("resources/sounds/loose.ogg"))),
    WIN(Gdx.audio.newSound(Gdx.files.internal("resources/sounds/win.ogg")));

    private Music loadedMusic = null;
    private Sound loadedSound = null;
    private boolean canPlay = true;

    Sounds(Music music)
    {
        loadedMusic = music;
        loadedMusic.setOnCompletionListener(new Music.OnCompletionListener() {
            @Override
            public void onCompletion(Music listener) {
                canPlay = true;
            }
        });
    }

    Sounds(Sound sound)
    {
        loadedSound = sound;
    }

    public void play()
    {
        if(canPlay && loadedMusic != null) {
            canPlay = false;
            loadedMusic.play();
        }

        // If we play a sound that can be played multiple times
        if(loadedSound != null)
            loadedSound.play();
    }

    public static void dispose()
    {
        for(Sounds s : Sounds.values())
        {
            if(s.loadedSound != null)
                s.loadedSound.dispose();
            if(s.loadedMusic != null)
                s.loadedMusic.dispose();
        }
    }

}
