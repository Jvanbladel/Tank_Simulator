package starter;

import java.awt.event.MouseEvent;

import acm.graphics.GImage;
import acm.graphics.GObject;

public class SettingsPane extends GraphicsPane {
	private MainApplication program; 
	
	private GImage Sound;
	private GImage Return;

	private boolean isSoundOn = true;

	public SettingsPane(MainApplication app) {
		super();
		program = app;
		Sound = new GImage("Button/sound0.png");
		Sound.setLocation(300,100);
		Return = new GImage("Button/mainmenu0.png");
		Return.setLocation(300,400);
		
	}

	@Override
	public void showContents() 
	{
		program.add(Sound);
		program.add(Return);
	}

	@Override
	public void hideContents() 
	{
		program.remove(Sound);
		program.remove(Return);
	}

	@Override
	public void mousePressed(MouseEvent e) 
	{
		GObject obj = program.getElementAt(e.getX(), e.getY());
		if (obj == Sound) 
		{
			program.toggleSound();
		}		
		else if (obj == Return) 
		{
			program.switchToMenu();
		}
	}
	
	@Override
	public void mouseMoved(MouseEvent e) {
		GObject obj = program.getElementAt(e.getX(), e.getY());
		if (obj == Return) {
			Return.setImage("Button/mainmenu1.png");
		}
		else {
			Return.setImage("Button/mainmenu0.png");
		}
	}
}