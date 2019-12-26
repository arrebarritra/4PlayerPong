package io.github.arrebarritra._4pp.tools;

import java.io.File;
import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;

/**
 * Plays sounds
 */
public class Sound {

    /**
     * Plays specified sound from folder containing WAV sounds
     *
     * @param sound Name of sound file
     */
    public static void playSound(String sound) {
        if (Boolean.parseBoolean(Settings.getProp("sound"))) {
            try {
                AudioInputStream stream = AudioSystem.getAudioInputStream(new File("res" + File.separator + "sound" + File.separator + sound + ".wav"));
                Clip clip = AudioSystem.getClip();
                clip.open(stream);
                clip.start();
            } catch (Exception ex) {
            }
        }
    }
}
