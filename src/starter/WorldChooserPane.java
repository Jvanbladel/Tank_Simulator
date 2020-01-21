package starter;

import java.awt.Color;
import java.awt.event.MouseEvent;

import acm.graphics.GImage;
import acm.graphics.GLabel;
import acm.graphics.GObject;
import acm.io.IODialog;

import java.util.ArrayList;

public class WorldChooserPane extends GraphicsPane{
	private static final int GRAV_X= 5;
	private static final int GRAV_Y=20;
	private MainApplication program; 
	
	private GImage background;
	
	
	private GImage map;
	
	private GImage tank1;
	private GImage tank2;
	
	private GameImage barrel1;
	private GameImage barrel2;
	
	private TANK_STYLE p1Color, p2Color;
	private WorldType world;
	
	private int tank1ColorIndex, tank2ColorIndex, worldIndex;
	private ArrayList<TANK_STYLE> tankColorList;
	private ArrayList<WorldType> worldTypeList;
	
	private GImage finished;
	private GImage title0;
	private GImage p1, p2;
	
	private GImage gravity;
	
	private int width;
	private int height;
	
	private int specialClicks;
	private IODialog cheaterCheater;
	
	private final static int TANK_HEIGHT = 50;

	public WorldChooserPane(MainApplication app, int width, int height, boolean is1Player) {
		super();
		program = app;
		
		
		this.width = width;
		this.height = height;
		
		this.p1Color = TANK_STYLE.GREEN;
		this.p2Color = TANK_STYLE.GREEN;
		tank1ColorIndex = 0;
		tank2ColorIndex = 0; 
		
		tank1 = new GImage(p1Color.getTankColor(true));
		tank1.setLocation(200-TANK_HEIGHT, height - 120);
		barrel1 = new GameImage(p1Color.getCannonColor());
		barrel1.setLocation(200-80, height - 120 + 12);
		barrel1.setDegrees(-20);
		
		tank2 = new GImage(p2Color.getTankColor(false));
		tank2.setLocation(width-(200 +TANK_HEIGHT), height - 120);
		barrel2 = new GameImage(p2Color.getCannonColor());
		barrel2.setLocation(width - (200 +60), height - 120 + 12);
		barrel2.setDegrees(200);
		
		this.world = WorldType.MEADOWS;
		background = new GImage(world.getFilePath());
		background.setSize(width, height);
		worldIndex = 0;
		
		map = new GImage("Button/map_meadow.png", 300,400);
		
		finished = new GImage("Button/begin0.png", 300, 250);
		
		title0 = new GImage("TitleImage/TITLE_WORLD0.png", 200,50);
		
		title0.setSize(400,50);
		
//		gravText= new GLabel("GRAVITY: " + this.world.getGravity(), GRAV_X, GRAV_Y);
//		gravText.setColor(Color.white);
//		gravText.setLocation(315,500);
//		gravText.setFont("Arial-BOLD-24");
		gravity = new GImage("Button/gravity_9.81.png", 325, 500);
		
		p1 = new GImage("TitleImage/TITLE_PLAYER1.png");
		p1.setLocation(tank1.getX(), tank1.getY()-50);
		p1.setSize(100,25);
		
		if(is1Player)
		{
			p2 = new GImage("TitleImage/TITLE_COMPUTER.png");
			p2.setSize(100,25);
			p2.setLocation(tank2.getX(), tank2.getY()-50);
		}
		else
		{
			p2 = new GImage("TitleImage/TITLE_PLAYER2.png");
			p2.setSize(100,25);
			p2.setLocation(tank2.getX()+15, tank2.getY()-50);
		}
		
		initializeTankAndWorldLists();
	}
	
	private void initializeTankAndWorldLists()
	{

		this.tankColorList = new ArrayList<TANK_STYLE>();
		tankColorList.add(TANK_STYLE.GREEN);
		tankColorList.add(TANK_STYLE.RED);
		tankColorList.add(TANK_STYLE.BLUE);
		tankColorList.add(TANK_STYLE.YELLOW);
		tankColorList.add(TANK_STYLE.PINK);
		
		this.worldTypeList = new ArrayList<WorldType>();
		worldTypeList.add(WorldType.MEADOWS);
		worldTypeList.add(WorldType.DESERT);
		worldTypeList.add(WorldType.MOUNTAINOUS);
		worldTypeList.add(WorldType.MOON);
		worldTypeList.add(WorldType.MARS);
	}

	@Override
	public void showContents() 
	{
		program.add(background);
		program.add(barrel1);
		program.add(barrel2);
		program.add(tank1);
		program.add(tank2);
		program.add(finished);
		program.add(title0);
		program.add(p1);
		program.add(p2);
		program.add(map);
		program.add(gravity);
	}

	@Override
	public void hideContents() 
	{
		program.add(background);
		program.remove(tank1);
		program.remove(tank2);
		program.remove(barrel1);
		program.remove(barrel2);
		program.remove(finished);
		program.remove(title0);
		program.remove(p1);
		program.remove(p2);
		program.remove(map);
	}

	private GObject obj;
	
	@Override
	public void mousePressed(MouseEvent e) 
	{
		obj = program.getElementAt(e.getX(), e.getY());
		
		if(obj == tank1 || obj == barrel1 || obj == p1)
		{
			changeTankColor(true);
		}
		else if(obj == tank2 || obj == barrel2 || obj == p2)
		{
			changeTankColor(false);
		}
		else if(obj == map)
		{
			changeWorld();
		}
		else if (obj == finished)
		{
			program.startLevel(p1Color, p2Color, world);
		}
		else if(obj == title0) {
			if(specialClicks>=5) {
				cheater();
			}
			else {
				specialClicks++;
			}
		}
	}
	
	@Override
	public void mouseMoved(MouseEvent e) {
		GObject obj = program.getElementAt(e.getX(), e.getY());
			if (obj == finished) {
				finished.setImage("Button/begin1.png");
			}
			else {
				finished.setImage("Button/begin0.png");
			}
			
	}
	
	private void changeTankColor(boolean isTank1)
	{
		if(isTank1)
		{
			if(tank1ColorIndex == tankColorList.size()-1)
			{
				tank1ColorIndex = 0;
			}
			else
			{
				tank1ColorIndex++;
			}
			p1Color = tankColorList.get(tank1ColorIndex);
			program.remove(barrel1);
			barrel1 = new GameImage(p1Color.getCannonColor());
			barrel1.setLocation(200-80, height - 120 + 12);
			barrel1.setDegrees(-20);
			program.add(barrel1);
			tank1.setImage(p1Color.getTankColor(true));
			tank1.sendToFront();
		}
		else
		{
			if(tank2ColorIndex == tankColorList.size()-1)
			{
				tank2ColorIndex = 0;
			}
			else
			{
				tank2ColorIndex++;
			}
			
			p2Color = tankColorList.get(tank2ColorIndex);
			program.remove(barrel2);
			barrel2 = new GameImage(p2Color.getCannonColor());
			barrel2.setLocation(width- (200 + 60), height - 120 + 12);
			barrel2.setDegrees(200);
			program.add(barrel2);
			tank2.setImage(p2Color.getTankColor(false));
			tank2.sendToFront();
			
		}
	}
	
	private void changeWorld()
	{
		if(worldIndex == worldTypeList.size()-1)
		{
			worldIndex = 0;
		}
		else
		{
			worldIndex++;
		}
		world = worldTypeList.get(worldIndex);
		background.setImage(world.getFilePath());
		background.setSize(width, height);
		map.setImage(world.getButtonpath());
		gravity.setImage(world.getGravityFP());
//		gravText.setLabel("GRAVITY: " + this.world.getGravity());
//		program.remove(gravText);
//		gravText= new GLabel("GRAVITY: " + this.world.getGravity(), GRAV_X, GRAV_Y);
//		program.add(gravText);
		program.music.switchMusic(world);
	}
	private void cheater() {
		
		cheaterCheater=new IODialog();
		String userIn;
			userIn=cheaterCheater.readLine("Enter Cheat code?");
			switch(userIn) {
			case "Gin&Juice": //plays Gin and Juice by Snoop Dog
				program.toggleSound();
				program.toggleSnoop();
				System.out.println("entering snoopMode");
				program.requestFocus();
				return;
			case "p1 ghost":	//turns player 1 into a ghost tank
				p1Color = TANK_STYLE.GHOST;
				program.remove(barrel1);
				barrel1 = new GameImage("TanksPNGS/GhostCannon.png");
				barrel1.setLocation(200-80, height - 120 + 12);
				barrel1.setDegrees(-20);
				program.add(barrel1);
				tank1.setImage("TanksPNGS/LeftSide_GhostTank.png");
				tank1.sendToFront();
				program.requestFocus();
				return;
			case "p2 ghost":	//turns player 2 into a ghost tank
				p2Color = TANK_STYLE.GHOST;
				program.remove(barrel2);
				barrel2 = new GameImage("TanksPNGS/GhostCannon.png");
				barrel2.setLocation(width- (200 + 60), height - 120 + 12);
				barrel2.setDegrees(200);
				program.add(barrel2);
				tank2.setImage("TanksPNGS/RightSide_GhostTank.png");
				tank2.sendToFront();
				program.requestFocus();
				return;
			case "p1 black":	//turns player 1 to a black color scheme
				p1Color = TANK_STYLE.BLACK;
				program.remove(barrel1);
				barrel1 = new GameImage("TanksPNGS/BlackCannon.png");
				barrel1.setLocation(200-80, height - 120 + 12);
				barrel1.setDegrees(-30);
				program.add(barrel1);
				tank1.setImage("TanksPNGS/LeftSide_BlackTank.png");
				tank1.sendToFront();
				program.requestFocus();
				return;
			case "p2 black":	//turns player 2 to a black color scheme
				p2Color = TANK_STYLE.BLACK;
				program.remove(barrel2);
				barrel2 = new GameImage("TanksPNGS/BlackCannon.png");
				barrel2.setLocation(width- (200 + 60), height - 120 + 12);
				barrel2.setDegrees(200);
				program.add(barrel2);
				tank2.setImage("TanksPNGS/RightSide_BlackTank.png");
				tank2.sendToFront();
				program.requestFocus();
				return;
			case "X":
				specialClicks=0;
				program.requestFocus();
				return;
			}
	
		
		
	}
}
