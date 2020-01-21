//This includes all the intro animation and menu animations

package starter;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.MouseEvent;
import javax.swing.Timer;

import acm.graphics.GImage;
import acm.graphics.GObject;

public class MenuPane extends GraphicsPane {
	private MainApplication program; 
	
	private MenuAnimation menuAnimation;
	
	private GImage Play;
	private GImage Settings;
	private GImage Credits;
	private GImage Exit;
	private GameImage explosion;
	
	public static final int DELAY_MS = 10;
	
	private Timer t1;
	private Timer t2;
	Timer bg;
	
	
	private int shootCounter = 0;
	private int delayCounter = 0;
	
	public AudioPlayer music = AudioPlayer.getInstance();
	
	private GImage tank;
	private GameImage barrel;
	private GImage shot;

	private double title_alpha = 0;
	
	private TransparentImage background;
	private int background_move_counter;
	
	private TransparentImage title;
	
	public MenuPane(MainApplication app) {
		super();
		program = app;
		
		//music.playSound("sounds", "FrigidTriumph.mp3", true);
		program.music.play(WorldType.MEADOWS);
		Play = new GImage("Button/Play0.png", 600, 25);
		Settings = new GImage("Button/Settings0.png", 600, 175); 
		Credits = new GImage("Button/credits0.png", 600, 325);
		Exit = new GImage("Button/exit0.png", 600,475);
		
		explosion = new GameImage(("otherImages/explosion.png"));
		explosion.setSize(100,100);
		
		tank = new GImage("../media/TanksPNGS/LeftSide_GreenTank.png", 100, 450);
		tank.setSize(250, 125);
		
		barrel = new GameImage("../media/TanksPNGS/large_greenTank_cannon.png", 15, 475);
		
		shot = new GImage("../media/Ammo/basic.png");
		shot.setSize(30, 30);
		
		background = new TransparentImage("otherImages/background_gray.png");
		background.setLocation(-50,0);
		background.changeTransparent(0);
		background.setSize(900,620);
		program.add(background);
		
		title = new TransparentImage("otherImages/TITLE2.png");
		title.setLocation(100,150);
		title.changeTransparent(0);
		
		t1 = new Timer(DELAY_MS, null);

		background_move_counter = 1;
		menuAnimation = new MenuAnimation(background, program, title, this, t1, title_alpha);
		
		bg = new Timer(150,null);
		t1.addActionListener(menuAnimation);
		bg.start();
		
		
		//TIMER bg actionPreformed()
		/*
		 * This method is the timer to move the background image left and right
		 */
		
		bg.addActionListener(new ActionListener() 
		{
			public void actionPerformed(ActionEvent e) 
			{
				if (menuAnimation.title_showing()) {
					if (background.getX() == -20) 
					{
						background_move_counter = -1;
					}
					else if (background.getX() == -50)
					{
						background_move_counter = 1;
					}
					background.move(background_move_counter, 0);
				}
			}
		});
		
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
								program.startGame();
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
								program.switchToSettings();
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
								program.switchtoCredits();
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
							explosion = new GameImage(("../media/otherImages/explosion.png"));
							explosion.setSize(100,100);
							explosion.setLocation(shot.getX()-25, shot.getY()-25);
							program.add(explosion);
							program.remove(shot);
						}
						else if(shootCounter > 30)
						{
							t2.stop();
							System.exit(0);
						}
							
						shootCounter++;
					}
				}
				delayCounter++;
			}
		});

	}
	
	//OVERRIDE showContents()
	/*
	 * Displays images in homescreen
	 * 
	 */

	@Override
	public void showContents() {
		t1.start();
		if (menuAnimation.title_showing())
		{
			program.add(Play);
			program.add(Settings);
			program.add(Credits);
			program.add(Exit);
			program.add(barrel);
			program.add(tank);
			title.setLocation(30,80);
			title.changeTransparent(1);
			program.add(title);
			background.setImage("otherImages/background.png");
			background.setSize(900,620);
			background.setLocation(-50,0);
			bg.start();
			background.sendToBack();
			program.add(background);
		}
	}

	//OVERRIDE showContents()
	/*
	 * Hides objects on homescreen
	 * 
	 */
	
	@Override
	public void hideContents() {
		program.remove(Play);
		program.remove(Settings);
		program.remove(Credits);
		program.remove(Exit);
		program.remove(tank);
		program.remove(barrel);
		program.remove(explosion);
		program.remove(title);
		shootCounter = 0;
		delayCounter = 0;
		barrel = new GameImage("../media/TanksPNGS/large_greenTank_cannon.png");
		barrel.setLocation(15, 475);
		p = false;
		s= false;
		c = false;
		q = false;
	}
	public void stopAnimation() {
		t1.stop();
		t2.stop();
		title_alpha=1;
		//bg.stop();
		
		
	}
	
	private GObject obj;
	
	private boolean p = false;
	private boolean s = false;
	private boolean c = false;
	private boolean q = false;
	
	//OVERRIDE mousePressed()
	/*
	 * Selects the animation for pressed buttons
	 * 
	 */
	
	
	@Override
	public void mousePressed(MouseEvent e) 
	{
		
		obj = program.getElementAt(e.getX(), e.getY());
		
		if(!p && !s && !c && ! q)
		{
			if (obj == Play) 
			{
				shootCounter = 0;
				delayCounter = 0;
				p = true;
				t2.start();
			}
			else if(obj == Settings) 
			{
				shootCounter = 0;
				delayCounter = 0;
				s = true;
				t2.start();
			}
			else if (obj == Credits)
			{
				shootCounter = 0;
				delayCounter = 0;
				c= true;
				t2.start();
			}
			else if (obj == Exit) 
			{
				shootCounter = 0;
				delayCounter = 0;
				q = true;
				t2.start();
			}
		}
	}
	
	
	@Override
	public void mouseReleased(MouseEvent e) 
	{
		program.remove(explosion);
	}
	
	//OVERRIDE mouseMoved()
	/*
	 * Allows mouse hovering over buttons to change color
	 */
	
	@Override
	public void mouseMoved(MouseEvent e) 
	{
		obj = program.getElementAt(e.getX(), e.getY());
		if (obj == Play) 
		{
			Play.setImage("Button/Play1.png");
		}
		else if (obj == Settings) 
		{
			Settings.setImage("Button/Settings1.png");
		}
		else if (obj == Credits) 
		{
			Credits.setImage("Button/credits1.png");
		}
		else if (obj == Exit) 
		{
			Exit.setImage("Button/Exit1.png");
		}
		else 
		{
			Play.setImage("Button/Play0.png");
			Settings.setImage("Button/Settings0.png");
			Credits.setImage("Button/credits0.png");
			Exit.setImage("Button/exit0.png");
		}
	}
	
	public void addUI() {
		program.add(title);
		program.add(Play);
		program.add(Settings);
		program.add(Credits);
		program.add(Exit);
		program.add(barrel);
		program.add(tank);
	}
}
