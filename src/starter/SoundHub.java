package starter;

import javax.swing.Timer;

public class SoundHub {
	MainApplication program;
	AudioPlayer soundHub;
	String FILE_PATH = "sounds";
	boolean isPlaying;
	private WorldType lastWT;
	
	public SoundHub(MainApplication mainApp){
		this.lastWT=WorldType.MEADOWS;
		this.isPlaying=true;
		this.program = mainApp;
		this.soundHub = AudioPlayer.getInstance();
		
	}
	public void switchMusic(WorldType wt) {//changes music to correct world
		stop();
		lastWT=wt;
		if(isPlaying) {
			play(wt);
		}
			
	}
	public void play(WorldType wt) {
		if(isPlaying) {
			lastWT=wt;
		}
		
		soundHub.playSound(FILE_PATH, getWorldMusic(wt), true);
	}
	private void stop() {
		
		soundHub.stopSound(FILE_PATH, getWorldMusic(lastWT));
	}
	public void toggleSound() {// allows user to toggle audio 
		if(isPlaying) {
			stop();
		}else {
			play(lastWT);
		}
		isPlaying=!isPlaying;
	}
	public WorldType getLastWT() {//returns last world type visited by the player
		return lastWT;
	}
	public void playShot(projectileType type) {//plays correct ammunition FX for corresponding projectileType enum
		if(isPlaying) {
			soundHub.playSound(FILE_PATH, getShotSound(type));
			
		}
		
	}
	public void playInput(String str) {// special FX that aren't tied to enumerators that need to be called only once or twice
		if(isPlaying) {
			switch(str) {
			case"plane":
				soundHub.playSound(FILE_PATH, "planeSound.mp3");
				return;
			case"whistle":
				soundHub.playSound(FILE_PATH, "drop_shot.mp3");
				return;
			case"lose":
				soundHub.playSound(FILE_PATH, "lose.mp3");
				return;
			case"explosion":
				soundHub.playSound(FILE_PATH, "explosion.mp3");
				return;
			}
		}
	}
	private String getShotSound(projectileType type) {//gives audio file path for corresponding projectileType enum
		
		switch(type) {
		case MISSILE: return"missile.mp3";
		case DOUBLE: return"doubleSound.mp3";
		case AIR_STRIKE:
		case BOMBING_RUN:
		case SLUG:
		case NULLSHOT:
		case BASIC:
		default:
			return "cannonFire.mp3";
		}
	}
	private String getWorldMusic(WorldType wt) {//gives audio file path for corresponding WorldType enum
		switch(wt) {
		case MOON:return "Moon.mp3";
		case MARS: return "Desert.mp3";
		case MOUNTAINOUS: return "Mountains.mp3";
		case DESERT: return "mars.mp3";
		case MEADOWS:
		default:return "FrigidTriumph.mp3";
		}
	}
}
