package starter;

import java.awt.event.MouseEvent;

import acm.graphics.GImage;
import acm.graphics.GObject;

public class CtrlzPane extends GraphicsPane {
	private MainApplication program; 
	
	private GImage Return;
	private GImage ctrlz;
	private GImage tae, jason, sean; 
	private GImage taeName, jasonName, seanName;
	private GParagraph t, j, s;
	private boolean showingTae, showingJason, showingSean;
	
	public CtrlzPane(MainApplication app) {
		super();
		program = app;
		Return = new GImage("Button/mainmenu0.png", 325, 430);
		ctrlz = new GImage("TitleImage/TITLE_CREDITS.png");
		ctrlz.setLocation(200, 30);
		ctrlz.setSize(400, 75);
		
		tae = new GImage("creditPhotos/tae.png");
		tae.setSize(150, 200);
		tae.setLocation(75, 245);
		
		t = new GParagraph(""
				+ "was resonsible for\n"
				+ "the all the animation\n"
				+ "work in Epic Tanks.\n"
				+ "Tae also made large \n"
				+ "contrabutions to the\n"
				+ "UI by making it easy \n"
				+ "to navigate.\n"
				+ "\n"
				+ "Words of Widsom:\n"
				+ "Always do what \n"
				+ "you are afraid\n"
				+ "to do.\n", 80, 255);

		t.setFont("Arial-Bold-14");
		
		sean = new GImage("../media/creditPhotos/sean.jpg");
		sean.setSize(150, 200);
		sean.setLocation(575, 245);
		
		s = new GParagraph(""
				+ "was resonsible for\n"
				+ "the all the art \n"
				+ "and sound in Epic\n"
				+ "Tanks. Sean made\n "
				+ "the game console\n"
				+ "important for bug\n"
				+ "testing. He added\n"
				+ "cheat codes if \n" 
				+ "you can find them \n"
				+"\n"
				+ "Words of Wisdom:\n"
				+ "Think critically\n"
				+ "of all your\n"
				+ "aphorisms.\n", 580, 255);
		s.setFont("Arial-Bold-10");
		
		jason = new GImage("../media/creditPhotos/jason.png");
		jason.setSize(150, 200);
		jason.setLocation(325, 185);
		
		j = new GParagraph(""
				+ "was resposible for\n"
				+ "the physics and the\n"
				+ "interface of the\n"
				+ "game. Jason helped\n"
				+ "make Epic Tanks the\n"
				+ "game it is today.\n"
				+ "\n"
				+ "Words of Wisdom:\n"
				+ "Anything worth\n"
				+ "doing is worth\n"
				+ "doing well.", 330, 195);
		j.setFont("Arial-Bold-14");
		
		taeName = new GImage("creditPhotos/CREDITS_TAE.png");
		seanName = new GImage("creditPhotos/CREDITS_SEAN.png");
		jasonName = new GImage("creditPhotos/CREDITS_JASON.png");
		
		taeName.setLocation(35, 195);
		seanName.setLocation(542, 195);
		jasonName.setLocation(240, 135);
		
		showingTae = true;
		showingJason = true;
		showingSean = true;
	}

	@Override
	public void showContents() 
	{
		program.add(Return);
		program.add(ctrlz);
		program.add(t);
		program.add(s);
		program.add(j);
		program.add(tae);
		program.add(jason);
		program.add(sean);
		program.add(seanName);
		program.add(taeName);
		program.add(jasonName);
		showingTae = true;
		showingJason = true;
		showingSean = true;
	}

	@Override
	public void hideContents() 
	{
		program.remove(Return);
		program.remove(ctrlz);
		program.remove(tae);
		program.remove(jason);
		program.remove(sean);
		program.remove(seanName);
		program.remove(taeName);
		program.remove(jasonName);
		program.remove(t);
		program.remove(s);
		program.remove(j);
	}

	@Override
	public void mousePressed(MouseEvent e) 
	{
		GObject obj = program.getElementAt(e.getX(), e.getY());
		if (obj == Return) {
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
		if(e.getX() > 575 && e.getX() < 725 && e.getY() > 235 && e.getY() < 435)
		{
			program.remove(sean);
			showingSean = false;
		}
		else if(!showingSean)
		{
			program.add(sean);
			showingSean = true;
		}
		
		if(e.getX() > 325 && e.getX() < 475 && e.getY() > 185 && e.getY() < 385)
		{
			program.remove(jason);
			showingJason = false;
		}
		else if(!showingJason)
		{
			program.add(jason);
			showingJason = true;
		}
		
		if(e.getX() > 75 && e.getX() < 225 && e.getY() > 235 && e.getY() < 435)
		{
			program.remove(tae);
			showingTae = false;
		}
		else if(!showingTae)
		{
			program.add(tae);
			showingTae = true;
		}
	}
}
