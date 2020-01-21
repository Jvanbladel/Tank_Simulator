package starter;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;

import javax.swing.Timer;

import acm.graphics.GImage;
import acm.graphics.GObject;

public class PauseGamePane extends GraphicsPane {
	private MainApplication program; 
	
	private GImage MainMenu;
	private GImage ReturnToGame;
	private GImage Sound;
	private GImage restartButton;
	private GImage title;
	private TransparentImage  background;
	
	private GImage tank, shot, explosion;
	private GameImage barrel;
	
	private int shootCounter;
	private int delayCounter;
	
	private boolean p;
	private boolean s;
	private boolean c;
	private boolean q;
	
	private Timer t2;

	public PauseGamePane(MainApplication app) {
		super();
		program = app;
		
		MainMenu = new GImage("Button/mainmenu0.png", 600, 475);
		Sound = new GImage("Button/sound0.png", 600, 325);
		restartButton = new GImage("Button/restart0.png", 600, 175); 
		ReturnToGame = new GImage("Button/back0.png", 600, 25);
		
		title = new GImage("TitleImage/TITLE_SETTINGS.png",100,100);
		
		background = new TransparentImage("otherImages/background.png");
		background.setSize(900,620);
		background.setLocation(-50,0);
		
		tank = new GImage("../media/TanksPNGS/LeftSide_GreenTank.png");
		tank.setSize(250, 125);
		tank.setLocation(100, 450);
		
		barrel = new GameImage("../media/TanksPNGS/large_greenTank_cannon.png");
		barrel.setLocation(15, 475);
		
		shot = new GImage("../media/Ammo/basic.png");
		shot.setSize(30, 30);
		
		explosion = new GameImage(("../media/otherImages/explosion.png"));
		
		shootCounter = 0;
		delayCounter = 0;
		p = false;
		s = false;
		c = false;
		q = false;
		
		
		t2 = new Timer(20, null);
		
		//TIMER t2 actionPreformed()
		/*
		 * This method is the timer to have the tank shoot a selected button
		 */
		
		t2.addActionListener(new ActionListener() 
		{
			public void actionPerformed(ActionEvent evt)
			{				
				if(delayCounter  > 5)
				{
					if(p)
					{
						if(barrel.getRotation() == 0 || barrel.getRotation() > 315)
						{
							barrel.rotate(-1);
						}
						else
						{
							if(shootCounter == 0)
							{
								shot = new GImage("../media/Ammo/basic.png");
								shot.setSize(30, 30);
								shot.setLocation(365, 320);
								program.add(shot);
							}
							else if (shootCounter < 25)
							{
								shot.move(10, -10);
							}
							else if (shootCounter == 25)
							{
								explosion = new GameImage(("../media/otherImages/explosion.png"));
								explosion.setSize(100,100);
								explosion.setLocation(shot.getX()-25, shot.getY()-25);
								program.add(explosion);
								program.remove(shot);
							}
							else if(shootCounter > 30)
							{
								t2.stop();
								program.loadGame();
							}
								
							shootCounter++;
						}
					}
					else if(s) 
					{
						
						if(barrel.getRotation() == 0 || barrel.getRotation() > 327)
						{
							barrel.rotate(-1);
						}
						else
						{
							if(shootCounter == 0)
							{
								shot = new GImage("../media/Ammo/basic.png");
								shot.setSize(30, 30);
								shot.setLocation(390, 355);
								program.add(shot);
							}
							else if (shootCounter < 20)
							{
								shot.move(10, -7);
							}
							else if (shootCounter == 20)
							{
								explosion = new GameImage(("../media/otherImages/explosion.png"));
								explosion.setSize(100,100);
								explosion.setLocation(shot.getX()-25, shot.getY()-25);
								program.add(explosion);
								program.remove(shot);
							}
							else if(shootCounter > 30)
							{
								t2.stop();
								program.restartLevel();;
							}
							shootCounter++;
						}
					}
					else if (c) 
					{
						if(barrel.getRotation() == 0 || barrel.getRotation() > 345)
						{
							barrel.rotate(-1);
						}
						else
						{
							if(shootCounter == 0)
							{
								shot = new GImage("../media/Ammo/basic.png");
								shot.setSize(30, 30);
								shot.setLocation(410, 430);
								program.add(shot);
							}
							else if (shootCounter < 16)
							{
								shot.move(10, -5);
							}
							else if (shootCounter == 16)
							{
								explosion = new GameImage(("../media/otherImages/explosion.png"));
								explosion.setSize(100,100);
								explosion.setLocation(shot.getX()-25, shot.getY()-25);
								program.add(explosion);
								program.remove(shot);
							}
							else if(shootCounter > 30)
							{
								t2.stop();
								program.toggleSound();
								program.remove(explosion);
								barrel.setDegrees(0);
								c = false;
							}
							
							shootCounter++;
						}
					}
					else if (q) 
					{
						if(shootCounter == 0)
						{
							shot = new GImage("../media/Ammo/basic.png");
							shot.setSize(30, 30);
							shot.setLocation(425, 475);
							program.add(shot);
						}
						else if (shootCounter < 16)
						{
							shot.move(10, 1);
						}
						else if (shootCounter == 16)
						{
							
							explosion.setSize(100,100);
							explosion.setLocation(shot.getX()-25, shot.getY()-25);
							program.add(explosion);
							program.remove(shot);
						}
						else if(shootCounter > 30)
						{
							t2.stop();
							program.switchToMenu();
						}
							
						shootCounter++;
					}
				}
				delayCounter++;
			}
		});
	}

	@Override
	public void showContents() 
	{
		program.add(background);
		program.add(Sound);
		program.add(MainMenu);
		program.add(ReturnToGame);
		program.add(restartButton);
		program.add(title);
		program.add(barrel);
		program.add(tank);
	}

	@Override
	public void hideContents() 
	{
		program.remove(background);
		program.remove(Sound);
		program.remove(MainMenu);
		program.remove(ReturnToGame);
		program.remove(restartButton);
		program.remove(title);
		program.remove(tank);
		program.remove(barrel);
		program.remove(explosion);
	}
	
	@Override
	public void mousePressed(MouseEvent e) 
	{	
		GObject obj = program.getElementAt(e.getX(), e.getY());
		
		if(!p && !s && !c && ! q)
		{
			if (obj == Sound) 
			{
				program.toggleFX();
				shootCounter = 0;
				delayCounter = 0;
				c = true;
				t2.start();
			}
			else if(obj == MainMenu) 
			{
				shootCounter = 0;
				delayCounter = 0;
				q = true;
				t2.start();
			}
			else if (obj == ReturnToGame)
			{
				shootCounter = 0;
				delayCounter = 0;
				p = true;
				t2.start();
			}
			else if (obj == restartButton) 
			{
				shootCounter = 0;
				delayCounter = 0;
				s = true;
				t2.start();
			}
		}
	}
	
	@Override
	public void mouseMoved(MouseEvent e) {
		GObject obj = program.getElementAt(e.getX(), e.getY());
		if (obj == MainMenu) {
			MainMenu.setImage("Button/mainmenu1.png");
		}
		else if(obj == ReturnToGame) {
			ReturnToGame.setImage("Button/back1.png");
		}
		else if (obj == restartButton) {
			restartButton.setImage("Button/restart1.png");
		}
		else {
			MainMenu.setImage("Button/mainmenu0.png");
			ReturnToGame.setImage("Button/back0.png");
			restartButton.setImage("Button/restart0.png");
		}
	}
}
