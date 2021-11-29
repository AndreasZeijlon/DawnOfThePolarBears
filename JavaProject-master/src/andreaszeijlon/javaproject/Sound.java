package andreaszeijlon.javaproject;

import javax.sound.sampled.*;
import java.io.File;
import java.io.IOException;




final public class Sound
{
    	private Clip clip = null;
	private static boolean noMusic = false;
    private static boolean noGameAudio = false;
    	private static boolean clipPlaying = false;
    private static final int MAXVALUE = Integer.MAX_VALUE;



	public  Sound(String s){


	    try{
		AudioInputStream inputStream = AudioSystem.getAudioInputStream(new File(s));
		clip = AudioSystem.getClip();
		clip.open(inputStream);
	    } catch (UnsupportedAudioFileException | IOException | LineUnavailableException e) {
			e.printStackTrace();
		}
	}
    public void play(){
	if(clip == null) return;clip.setFramePosition(0);clip.start();

    }
    public void stop(){
	if(clip.isRunning())
	    clip.stop();
    }
    public void loop(){
	if(!noMusic){
	clip.loop(MAXVALUE);}
    }


    public static void setClipPlaying(final boolean clipPlaying) {
	Sound.clipPlaying = clipPlaying;
    }

    public static boolean isNoMusic() {
    		return noMusic;
    	}

    	public static void setNoMusic(boolean noMusic) {
    		Sound.noMusic = noMusic;
    	}

    	public static boolean isNoGameAudio() {
    		return noGameAudio;
    	}

    	public static void setNoGameAudio(boolean noGameAudio) {
    		Sound.noGameAudio = noGameAudio;
    	}

	public static boolean getNoGameAudio(){
		return noGameAudio;
	}

	public static boolean getClipPlaying(){
		return clipPlaying;
	}
}
