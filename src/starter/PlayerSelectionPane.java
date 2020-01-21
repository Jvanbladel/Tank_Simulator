package starter;

import java.awt.Color;
import java.awt.event.MouseEvent;


import acm.graphics.GImage;
import acm.graphics.GObject;

public class PlayerSelectionPane extends GraphicsPane{
	private MainApplication program; 
	
	
	private GImage onePlayer;
	private GImage twoPlayer;
	
	private GButton X;
	private GImage numbPlayers;
	
	private GImage tank1;
	private GImage tank2;
	private GImage tank3;
	
	private GameImage barrel1;
	private GameImage barrel2;
	private GameImage barrel3;

	public PlayerSelectionPane(MainApplication app) {
		super();
		program = app;
		
		
		onePlayer = new GImage("Button/playerselect0.png", 50, 125);
		twoPlayer = new GImage("Button/playerselect0.png", 425, 125);
		
		X = new GButton("X", 750, 0, 50, 50);
		X.setFillColor(Color.RED);
		
		numbPlayers = new GImage("TitleImage/TITLE_SELECT.png", 50, 25);
		numbPlayers.setSize(650,50);
		
		tank1 = new GImage("../media/TanksPNGS/LeftSide_GreenTank.png");
		tank1.setLocation(160, 265);
		tank2 = new GImage("../media/TanksPNGS/LeftSide_GreenTank.png");
		tank2.setLocation(475, 265);
		tank3 = new GImage("../media/TanksPNGS/LeftSide_GreenTank.png");
		tank3.setLocation(585, 265);
		
		barrel1 = new GameImage("../media/TanksPNGS/greenTank_cannon.png");
		barrel1.setLocation(130, 275);
		barrel1.rotate(-20);
		
		barrel2 = new GameImage("../media/TanksPNGS/greenTank_cannon.png");
		barrel2.setLocation(445, 275);
		barrel2.rotate(-20);
		
		barrel3 = new GameImage("../media/TanksPNGS/greenTank_cannon.png");
		barrel3.setLocation(555, 275);
		barrel3.rotate(-20);	
	}

	@Override
	public void showContents() 
	{
		program.add(onePlayer);
		program.add(twoPlayer);
		program.add(X);
		program.add(numbPlayers);
		program.add(barrel1);
		program.add(barrel2);
		program.add(barrel3);
		program.add(tank1);
		program.add(tank2);
		program.add(tank3);
		
	}

	@Override
	public void hideContents() 
	{
		program.remove(onePlayer);
		program.remove(twoPlayer);
		program.remove(X);
		program.remove(numbPlayers);
		program.remove(tank1);
		program.remove(tank2);
		program.remove(tank3);
		program.remove(barrel1);
		program.remove(barrel2);
		program.remove(barrel3);
	}

	private GObject obj;
	
	@Override
	public void mousePressed(MouseEvent e) 
	{
		obj = program.getElementAt(e.getX(), e.getY());
		
		if(obj == onePlayer || obj == tank1 || obj == barrel1)
		{
			program.switchToDif();
		}
		else if(obj == twoPlayer || obj == tank2 || obj == barrel2 || obj == tank3 || obj == barrel3)
		{
			program.switchToDraft(false, 0);
		}
		else if(obj == X)
		{
			program.switchToMenu();
		}
		
	}
	
	@Override
	public void mouseMoved(MouseEvent e) {
		obj = program.getElementAt(e.getX(), e.getY());
		
		if (obj == onePlayer || obj == tank1 || obj == barrel1) {
			onePlayer.setImage("Button/playerselect1.png");
		}
		else if (obj == twoPlayer || obj == tank2 || obj == barrel2 || obj == tank3 || obj == barrel3) {
			twoPlayer.setImage("Button/playerselect1.png");
		}
		else {
			onePlayer.setImage("Button/playerselect0.png");
			twoPlayer.setImage("Button/playerselect0.png");
		}
	}
}