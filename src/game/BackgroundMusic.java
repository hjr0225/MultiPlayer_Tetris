package game;

import java.io.IOException;
import java.net.URL;

import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;
import javax.sound.sampled.LineUnavailableException;
import javax.sound.sampled.UnsupportedAudioFileException;

public class BackgroundMusic{
	private Clip audioClip;
	public void playMusic() {
		try {
			audioClip = AudioSystem.getClip();
			URL url = getClass().getResource("tetris2.wav");
			audioClip.open(AudioSystem.getAudioInputStream(url));
			audioClip.start();
			audioClip.loop(Clip.LOOP_CONTINUOUSLY);
		} catch (LineUnavailableException e) {
			e.printStackTrace();
		} 
		catch (IOException e) {e.printStackTrace();}
		catch (UnsupportedAudioFileException e) {e.printStackTrace();} 

	}
	
	
	public void stopPlaying() {
		audioClip.stop();
		audioClip.close();
	}
}
