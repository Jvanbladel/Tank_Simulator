package starter;

import java.awt.Color;
import java.awt.event.ActionEvent;

import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;

import javax.swing.Timer;

import acm.graphics.GImage;
import acm.graphics.GObject;
import acm.graphics.GRect;

public class WinPane extends GraphicsPane implements ActionListener {
	private MainApplication program; 
									
	private GRect black;
	private GImage background;
	
	private GImage Mainmenu;
	private GImage restartButton;
	
	private GImage message;
	
	private GImage finalTank;
	private GameImage finalTankCannon;
	
	private String bgname;
	private int counter;
	Timer winTimer;
	
	private boolean isPlayer1Win;
	private boolean isSingleP;

	public WinPane(MainApplication app, int w, int h, boolean isWinnerPlayer1, boolean isSinglePlayer) {
		super();
		program = app;
		
		
		black = new GRect(0,0, 800, 600);
		black.setFilled(true);
		black.setFillColor(Color.black);
		
		isPlayer1Win = isWinnerPlayer1;
		isSingleP = isSinglePlayer;
		Mainmenu = new GImage("Button/mainmenu0.png", 125, 450);
		restartButton = new GImage("Button/restart0.png", w-125 - 150, 450);
		
		background = new GImage("otherImages/WIN_BACKGROUND0.png");
		background.sendToBack();

		bgname = "otherImages/WIN_BACKGROUND";
		counter = 0;
		

		finalTank = new GImage(program.getFinalTank(isSinglePlayer, isWinnerPlayer1));
		finalTankCannon = new GameImage(program.getFinalCannon(program.getTank(isSinglePlayer,isWinnerPlayer1)));
		
		finalTank.setLocation(350,275);
		finalTankCannon.setLocation(320, 287);
		
		if(isSinglePlayer)
		{
			if(isWinnerPlayer1)
			{
				message = new GImage("TitleImage/TITLE_WIN.png", 200,50);
				
				
			}
			else
			{
				message = new GImage("TitleImage/TITLE_YOULOST.png", 180,300);
				background.setImage("otherImages/LOSE_BACKGROUND.png");
				background.setLocation(0,600);
				program.music.playInput("lose");
				finalTank.setLocation(350,450);
				finalTankCannon.setLocation(320, 462);
				
				Mainmenu.setLocation(100, 450);
				restartButton.setLocation(w-100 - restartButton.getWidth(), 450);
			}
		}
		else
		{
			if(isWinnerPlayer1)
			{
				message = new GImage("TitleImage/TITLE_PLAYER1WINS.png", 100,50);
			}
			else
			{
				message = new GImage("TitleImage/TITLE_PLAYER2WINS.png", 100,50);
				finalTankCannon.rotate(180);
				finalTankCannon.move(10, 0);
			}
		}
		
		
		winTimer = new Timer(500, this);
		
	}

	@Override
	public void showContents() 
	{
		
		winTimer.start();
	}

	@Override
	public void hideContents() 
	{
		winTimer.stop();
		program.remove(black);
		program.remove(background);
		program.remove(Mainmenu);
		program.remove(restartButton);
		program.remove(message);
		program.remove(finalTankCannon);
		program.remove(finalTank);
	}

	@Override
	public void mousePressed(MouseEvent e) 
	{
		GObject obj = program.getElementAt(e.getX(), e.getY());
		if (obj == Mainmenu) {
			program.switchToMenu();
		}
		else if(obj == restartButton)
		{
			program.restartLevel();
		}
	}
	
	@Override
	public void mouseMoved(MouseEvent e) {
		GObject obj = program.getElementAt(e.getX(), e.getY());
		
		if (obj == Mainmenu) {
			Mainmenu.setImage("Button/mainmenu1.png");
		}
		else if (obj == restartButton) {
			restartButton.setImage("Button/restart1.png");
		}
		else {
			Mainmenu.setImage("Button/mainmenu0.png");
			restartButton.setImage("Button/restart0.png");
		}
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		program.add(black);
		program.add(background);
		if (!isSingleP || isPlayer1Win) {
			program.add(Mainmenu);
			program.add(restartButton);
			program.add(message);
			program.add(finalTankCannon);
			program.add(finalTank);
			if (counter == 0) {
				counter++;
			}
			else {
				counter--;
			}
			background.setImage(bgname + counter + ".png");
		}
		else {
			winTimer.setDelay(5);
			background.move(0,-5);
			if (background.getY() == 0) {
				program.add(Mainmenu);
				program.add(restartButton);
				program.add(message);
				program.add(finalTankCannon);
				program.add(finalTank);
				winTimer.stop();
			}
		}
	}
}
