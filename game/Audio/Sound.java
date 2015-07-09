package game.Audio;

import java.applet.Applet;
import java.applet.AudioClip;

public class Sound {
    public static AudioClip END;
    public static AudioClip WIN;
    public static AudioClip LASER;
    public static AudioClip SPECIAL_LASER;
    public static AudioClip BACKGROUND;
    public static AudioClip POWERUP_SPECIAL;
    public static AudioClip POWERUP;
    public static AudioClip START;
    public static AudioClip OPTION;
    public static AudioClip BOSS_INTRO;
    public static AudioClip BOSS_MIDDLE;
    public static AudioClip BOSS_END;
    
    public Sound(){
        END = Applet.newAudioClip(Sound.class.getResource("Mufasa.wav"));
        WIN = Applet.newAudioClip(Sound.class.getResource("Clapping.wav"));
        LASER = Applet.newAudioClip(Sound.class.getResource("laser.wav"));
        SPECIAL_LASER = Applet.newAudioClip(Sound.class.getResource("fire.wav"));
        BACKGROUND = Applet.newAudioClip(Sound.class.getResource("DarkVader.wav"));
        POWERUP_SPECIAL = Applet.newAudioClip(Sound.class.getResource("Yoshi_Mount.wav"));
        POWERUP = Applet.newAudioClip(Sound.class.getResource("POWERUP_MUSHROOM.wav"));
        START = Applet.newAudioClip(Sound.class.getResource("Mario.wav"));
        OPTION = Applet.newAudioClip(Sound.class.getResource("Coin.wav"));
        BOSS_INTRO = Applet.newAudioClip(Sound.class.getResource("nyancat_intro.wav"));
        BOSS_MIDDLE = Applet.newAudioClip(Sound.class.getResource("nyancat_loop.wav"));
        BOSS_END = Applet.newAudioClip(Sound.class.getResource("nyancat_exit.wav"));
    }
}
