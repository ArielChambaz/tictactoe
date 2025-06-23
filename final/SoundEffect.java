import javax.sound.sampled.*;
import java.net.URL;

public enum SoundEffect {
   EAT("audio/eatfood.wav"),
   DIE("audio/die.wav");

   public static enum Volume { MUTE, LOW, MEDIUM, HIGH }
   public static Volume volume = Volume.LOW;

   private Clip clip;

   SoundEffect(String soundFileName) {
      try {
         URL url = getClass().getClassLoader().getResource(soundFileName);
         AudioInputStream audio = AudioSystem.getAudioInputStream(url);
         clip = AudioSystem.getClip();
         clip.open(audio);
      } catch (Exception e) {
         e.printStackTrace();
      }
   }

   public void play() {
      if (volume != Volume.MUTE) {
         if (clip.isRunning()) clip.stop();
         clip.setFramePosition(0);
         clip.start();
      }
   }

   public static void init() {
      values(); // précharge tous les sons
   }
}
