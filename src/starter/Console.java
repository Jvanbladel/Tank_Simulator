package starter;

import acm.graphics.GImage;
import acm.io.IODialog;

public class Console {

	MainApplication program;
	public IODialog consoleControls;
	private GImage background;
	public Console(MainApplication program) {
		this.program=program;
		background = new GImage("../media/BackGroundImages/meadowsBackGroundImage.png");
		background.setSize(MainApplication.WINDOW_WIDTH, MainApplication.WINDOW_HEIGHT);
	}
	
	public void run() {
		consoleControls=new IODialog();
		String userIn = new String();
		userIn=consoleControls.readLine("Console Commands");
		String[] grouping =sepSpace(userIn);
		switch(grouping[0]) 
		{
			case"meadows":
				program.testLevel(WorldType.MEADOWS, grouping);
				return;
			case"desert":
				program.testLevel(WorldType.DESERT, grouping);
				return;
			case"mountains":
				program.testLevel(WorldType.MOUNTAINOUS, grouping);
				return;
			case"moon":
				program.testLevel(WorldType.MOON, grouping);
				return;
			case"mars":
				program.testLevel(WorldType.MARS, grouping);
				return;
			case"p1_win":
				cleanUp();
				program.skipToWinScreen(true, false);
				return;
			case"p2_win":
				cleanUp();
				program.skipToWinScreen(false, false);
				return;
			case"win":
				cleanUp();
				program.skipToWinScreen(true, true);
				return;
			case"lost":
				cleanUp();
				program.skipToWinScreen(false, true);
				return;
			case"gj": //"I've got my mind on my money and my money on my mind"
				program.toggleSnoop();
				return;
			case "menu":
				cleanUp();
				program.remove(background);
				program.switchToMenu();
				return;
			case "cred":
				cleanUp();
				program.switchtoCredits();
				return;
			case "bio": //goes to team bios...Glitch in background menu animations when run
				cleanUp();
				program.switchToCtrlz();
				return;
			case"sound":
				program.toggleSound();
				return;
			case"easy":
				cleanUp();
				program.switchToDraft(true , 1);
				return;
			case"medium":
				cleanUp();
				program.switchToDraft(true, 2);
				return;
			case"hard":
				cleanUp();
				program.switchToDraft(true, 3);
				return;
		}
	}
	private void cleanUp() { //cancels and removes all animations going on in MainApplication or other panes
		program.getMenu().stopAnimation();
		program.removeAll();
		program.add(background);
	}
	public String[] sepSpace(String str) {//separates the string entered into the console on spaces for sequenced commands
		return str.split("\\s+");
	}
	
}
