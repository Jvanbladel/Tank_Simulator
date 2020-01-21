package starter;

import java.awt.event.MouseEvent;

import acm.graphics.GImage;
import acm.graphics.GObject;

public class CreditsPane extends GraphicsPane {
	private MainApplication program; 
	
	private GImage Return, Ctrlz;
	private GImage credits;
	private GParagraph gameDescription;
	
	private TransparentImage creditsBG;
	
	public CreditsPane(MainApplication app) {
		super();
		program = app;
		Return = new GImage("Button/mainmenu0.png", 100, 450);
		Ctrlz = new GImage("Button/team0.png", 550, 450);

		credits = new GImage("../media/otherImages/TITLE2.png");
		credits.setLocation(200, 30);
		credits.setSize(400, 75);
		
		creditsBG = new TransparentImage("creditPhotos/CREDITS_WHITE.png");
		creditsBG.setLocation(50,110);
		creditsBG.changeTransparent(0.5);
		creditsBG.setSize(710,320);
		
		gameDescription = new GParagraph(""
				+ "      Ctrl-Z presents to you the game: Epic Tanks.\n"
				+ "The game is was made as a project for Comp 055,\n"
				+ "the class was taught by Professor Osvaldo at\n"
				+ "University of the Pacific. Epic tanks is a shooting\n"
				+ "simulator that was inspired by the IOS game\n"
				+ "'Pocket Tanks' and the game 'Shell Shock' found\n"
				+ "on steam. From all the members of Ctrl-Z team,\n"
				+ "we hope you enjoy the game.", 50, 160);
		gameDescription.setFont("Arial-Bold-30");
	}

	@Override
	public void showContents() 
	{
		program.add(creditsBG);
		program.add(Return);
		program.add(Ctrlz);
		program.add(credits);
		program.add(gameDescription);
	}

	@Override
	public void hideContents() 
	{
		program.remove(creditsBG);
		program.remove(Return);
		program.remove(Ctrlz);
		program.remove(credits);
		program.remove(gameDescription);
	}

	@Override
	public void mousePressed(MouseEvent e) 
	{
		GObject obj = program.getElementAt(e.getX(), e.getY());
		if (obj == Return) {
			program.switchToMenu();
		}
		else if (obj == Ctrlz) {
			program.switchToCtrlz();
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
		
		if (obj == Ctrlz) {
			Ctrlz.setImage("Button/team1.png");
		}
		else {
			Ctrlz.setImage("Button/team0.png");
		}
	}
}
