package com.mygdx.game.outbreak;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.audio.Music;
import com.badlogic.gdx.audio.Sound;

/**
 * Created by Jay on 1/10/2016.
 */
public class Audio {
    public static final Music INTRO = Gdx.audio.newMusic(Gdx.files.internal("intro.ogg"));
    public static final Sound PADDLE = Gdx.audio.newSound(Gdx.files.internal("paddle.ogg"));
    public static final Sound SPLIT = Gdx.audio.newSound(Gdx.files.internal("split.ogg"));
    private static final Sound BLIP1 = Gdx.audio.newSound(Gdx.files.internal("blip1.ogg"));
    private static final Sound BLIP2 = Gdx.audio.newSound(Gdx.files.internal("blip2.ogg"));
    private static final Sound BLIP3 = Gdx.audio.newSound(Gdx.files.internal("blip3.ogg"));
    private static final Sound BLIP4 = Gdx.audio.newSound(Gdx.files.internal("blip4.ogg"));
    public static final Sound[] BLIP = {BLIP1, BLIP2, BLIP3, BLIP4};
}
