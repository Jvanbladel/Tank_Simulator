package starter;
import java.awt.Color;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.awt.event.KeyEvent;

import acm.graphics.GImage;
import acm.graphics.GLabel;
import acm.graphics.GObject;
import acm.graphics.GOval;
import acm.graphics.GRect;
import acm.util.RandomGenerator;
import javafx.geometry.Rectangle2D;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;

import java.util.ArrayList;

import javax.swing.Timer;

public class GamePane extends GraphicsPane {
	private MainApplication program; 
										
	public static final float DELAY_MS = 10;
	public static final int MOVE_DELAY = 100;
	
	private int width;
	private int height;
	private WorldType world;
	
	private Level level;
	private Decorations decor;
	private InGameTutorial tutorialObj;
	
	Tank [] t;
	
	ArrayList<projectileType> p1;
	ArrayList<projectileType> p2;
	
	private TANK_STYLE p1Color, p2Color;
	private ArrayList<GRect> terrainList;
	private GImage tank1, tank2;
	private GameImage cannon1, cannon2;
	private GRect hOutline1, hOutline2, h1, h2;
	
	private Timer timer, moveTimer, AITimer, moveTankTimer, airStrikeTimer, bomberTimer;
	
	private Cordinate hitPos;
	private int  airStrikeCounter,  bombingCounter, missilePos;
	GameImage missile;
	private boolean dropedLoad;
	
	private boolean isSinglePlayer;
	private boolean isGamePaused;
	
	private int difficulty;
	public AudioPlayer effectSounds = AudioPlayer.getInstance();
	
	public GamePane(MainApplication app, int programWidth, int programHeight, 
			ArrayList<projectileType> p1,  ArrayList<projectileType> p2, 
			boolean isSinglePlayer, int diff,
			TANK_STYLE p1Color, TANK_STYLE p2Color, WorldType worldSelected)
	{
		super();
		program = app;
		
		this.width = programWidth;
		this.height = programHeight;
		this.world = worldSelected;
		this.p1 = p1;
		this.p2 = p2;
		this.p1Color = p1Color;
		this.p2Color = p2Color;
		this.isSinglePlayer = isSinglePlayer;
		this.difficulty = diff;
		
		isGameOver = false; //********************
		isWinnerPlayer1 = true;
		hideUI = false;
		hiding = false;
		hidingShot = true;
		moveScreenRight = false;
		movingTank = false;
		fireScreenUp = false;
		isGamePaused = false;
		
		power1 = 25;
		power2 = 25;
		angle1 = 0;
		angle2 = 180;
		moveTankToRight = false;
		moveTankCount = 0;
		isCurrentlyMoving = false;
		
		AICount = 0;
		AIAngle = 0;
		lastHit = false;

		moveAI = false;
		moveAIRight = true;
		
		timesP1MovedRight = 0;
		timesP1MovedLeft = 0;
		timesAIMovedRight = 0;
		timesAIMovedLeft = 0;
		timesPlayerWasHit = 0;
		previousTimesPlayerWasHit = 0;
		timesAIWasHit = 0;
		previousTimesAIWasHit = 0;
		PreviousAIAngle = 0;
		PreviousAIPower = 0;
		
		firing = false;
		increment = 100;
		count = 100;
		explosionTiming = 100;
		
		level = new Level(programWidth*3, programHeight, p1, p2, world);
		t = level.getTanks();
		level.changeWind();
		drawLevel();
		
		timer = new Timer((int) DELAY_MS, null);
		moveTimer = new Timer((int) DELAY_MS, null);
		AITimer = new Timer((int) DELAY_MS, null);
		moveTankTimer = new Timer(MOVE_DELAY, null);
		
		program.music.switchMusic(world);
		
		//TIMER timer actionListener
		/* has a short delay then fires the tank with an animation
		 * this timer stops when the projectile hits a tank or the terrain
		 * it then adds an explosion effect to the screen 
		 * 
		 * This method was very hard to write please do not modify without everyone being present
		 */
		
		timer.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent evt)
			{
				if(projectileObject != null && count == 99)
				{
					
					
					program.music.playShot(currentShotType);
					program.add(projectileObject);
				}
				
				if(count >100)
				{
					if(count==90 && currentShotType== projectileType.DOUBLE) {
						program.music.playShot(currentShotType);
					}
					if(flightPath != null && increment < flightPath.size()-1 && projectileObject != null)
					 {
						 if(moveTerrain(flightPath.get(increment).getX() - flightPath.get(increment-1).getX(), true))
							 projectileObject.setLocation(projectileObject.getX(), flightPath.get(increment).getY());
						 else
							 projectileObject.setLocation(projectileObject.getX()+ flightPath.get(increment).getX() - flightPath.get(increment-1).getX(), flightPath.get(increment).getY());
						 
						 rotateProjectileImage(increment);
					 }
					
					 increment++;
					
					 if(flightPath == null || increment == flightPath.size() || level.HitObject(flightPath.get(increment)))
					 {
						 if(explosionTiming == 0)
						 {
							 if(currentShotType == projectileType.AIR_STRIKE)
							 {
								 hitPos = new Cordinate(flightPath.get(increment-1).getX(),flightPath.get(increment-1).getY());
								 program.remove(projectileObject);
								 projectileObject = null;
								 flightPath = null;
								 timer.stop();
								 missilePos = -50;
								 airStrikeCounter = 0;
								 airStrikeTimer.start();
							 }
							 else if(currentShotType == projectileType.BOMBING_RUN)
							 {
								 hitPos = new Cordinate(flightPath.get(increment-1).getX(),flightPath.get(increment-1).getY());
								 program.remove(projectileObject);
								 projectileObject = null;
								 flightPath = null;
								 timer.stop();
								 dropedLoad = false;
								 bombingCounter = 0;
								 bomberTimer.start();
							 }
							 else
							 {
								 explosion = new GImage("../media/otherImages/explosion.png");
								 explosion.setSize(EXPLOSION_HEIGHT, EXPLOSION_HEIGHT);
								 explosion.setLocation(projectileObject.getX()-(EXPLOSION_HEIGHT/2), flightPath.get(increment-1).getY()-(EXPLOSION_HEIGHT/2));
								 program.add(explosion);
								 program.music.playInput("explosion");
								 program.remove(projectileObject);
								 projectileObject = null;
								 flightPath = null;
								 updatePlayerHealth();
							 }
						 }
						 else if(explosionTiming > 20)
						 {
							 centerOnFiringTank(level.isPlayer1Turn());
							 level.changeWind();
							 showUI(level.isPlayer1Turn());
							 program.remove(explosion);
							 explosion = null;
							 firing = false;
							 timer.stop();
						 }
						explosionTiming++;
					 }
				}
				count++;
			}
		});
			
		//TIMER moveTimer actionListener
		/* 
		 * Moves the screen right or left countinously when called
		 */
		
		moveTimer.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent evt)
			{
				if(moveScreenRight)
				{
					 moveTerrain(-5, true);
				}
				else
				{
					 moveTerrain(5, true);
				}
			}
		});
		
		//TIMER AITimer actionListener
				/* 
				 * Animations and delays for AI moving cannon and AI Tank left/right
				 */
		
		AITimer.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent evt)
			{
				if(moveAI)
				{
					moveTankTimer.start();
				}
				else
				{
					if(AICount <= AIAngle)
					{
						cannon2.rotate(1);
						angle2++;
						
					}
					else
					{
						fireTank();
						AITimer.stop();
					}
					
					AICount++;
				}
				
			}
		});
		
		//TIMER moveTankTimer actionListener
		/* 
		 * moves the current players tank left or right 50 pixels in an animation.
		 * 
		 */
		
		moveTankTimer.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent evt)
			{
				if(moveTankCount <50)
				{
					if(moveTankToRight)
					{
						moveTerrain(1, false);
						recompileTankAndArrows(level.isPlayer1Turn());
					}
					else
					{
						moveTerrain(-1, false);
						recompileTankAndArrows(level.isPlayer1Turn());
					}
				}
				else
				{
					recompileFuel(level.isPlayer1Turn());
					isCurrentlyMoving = false;
					moveAI = false;
					moveTankTimer.stop();
					
				}
				moveTankCount++;
			}
		});
		
		    airStrikeTimer = new Timer(15, null);
		    
		    airStrikeTimer.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent evt)
			{
				if(airStrikeCounter == 0)
				{
					missile = new GameImage("../media/Ammo/drop_Shot.png");
					missile.setSize(25,40);
					missile.setLocation(hitPos.getX()- currentTerrainPos, missilePos);
					program.add(missile);
					program.music.playInput("whistle");
					airStrikeCounter++;
				}
				else if(missile != null && missile.getY() < hitPos.getY())
				{
					missile.move(0, 5);
				}
				else if (missile != null && explosion == null)
				{
					explosion = new GImage("../media/otherImages/explosion.png");
					explosion.setSize(EXPLOSION_HEIGHT, EXPLOSION_HEIGHT);
					explosion.setLocation(missile.getX()-(missile.getWidth()/2)-(EXPLOSION_HEIGHT/2),
							missile.getY()-(missile.getHeight()/2)-(EXPLOSION_HEIGHT/2));
					program.remove(missile);
					program.add(explosion);
					program.music.playInput("explosion");
					updatePlayerHealth();
				}
				else if(airStrikeCounter > 20)
				{
					 centerOnFiringTank(level.isPlayer1Turn());
					 level.changeWind();
					 showUI(level.isPlayer1Turn());
					 program.remove(explosion);
					 explosion = null;
					 firing = false;
					 airStrikeTimer.stop();
				}
				else
				{
					airStrikeCounter++;
				}
			}
		});
		    
		bomberTimer = new Timer(15, null);
		    
		bomberTimer.addActionListener(new ActionListener() {
			private GImage bomber;
		public void actionPerformed(ActionEvent evt)
		{
			if(bombingCounter == 0)
			{
				if(bomber != null)
					program.remove(bomber);
				centerOnFiringTank(!level.isPlayer1Turn());
				if(level.isPlayer1Turn())
				{
					bomber = new GImage("../media/otherImages/"+world.getPlane()+"L.png");
					bomber.setLocation(width + 100, 80);
				}
				else
				{
					bomber = new GImage("../media/otherImages/"+world.getPlane()+"R.png");
					bomber.setLocation(-100, 80);
				}
				bomber.setSize(100, 70);
				program.add(bomber);
			}
			else if (bombingCounter < 100)
			{
				if(level.isPlayer1Turn())
				{
					bomber.move(-5, 0);
				}
				else
				{
					bomber.move(5, 0);
				}
			}
			else if (!level.isPlayer1Turn())
			{
				if(bomber.getX() <= hitPos.getX()-currentTerrainPos)
				{
					if(!moveTerrain(5, true))
					{
						bomber.move(5, 0);
					}
				}
				else if (bomber.getX() > hitPos.getX()-currentTerrainPos)
				{
					if(!dropedLoad)
					{
						airStrikeCounter = 0;
						missilePos = (int)(bomber.getY()-bomber.getHeight());
						airStrikeTimer.start();
						dropedLoad = true;
					}
					bomber.move(5, 0);
					if(bomber.getX() > width + 100)
					{
						bomberTimer.stop();
						program.remove(bomber);
					}
				}
			}
			else 
			{
				if(bomber.getX() >= hitPos.getX()-currentTerrainPos)
				{
					if(!moveTerrain(-5, true))
					{
						bomber.move(-5, 0);
					}
				}
				else if (bomber.getX() < hitPos.getX()-currentTerrainPos)
				{
					if(!dropedLoad)
					{
						airStrikeCounter = 0;
						missilePos = (int)(bomber.getY()-bomber.getHeight());
						airStrikeTimer.start();
						dropedLoad = true;
					}
					bomber.move(-5, 0);
					if(bomber.getX() < -100)
					{
						bomberTimer.stop();
						program.remove(bomber);
					}
				}
			}
			bombingCounter++;
		}	
		});
	}
	
	//METHOD drawLevel()
	/* 
	 * Draws all the elements on the screen.
	 * Called by constructor
	 */
	
	private void drawLevel()
	{
		drawBackGround();
		drawTerrain(width*3, height);
		decor = new Decorations(world,terrainList, program);
		drawTanks();
		drawUI();
		tutorialObj = new InGameTutorial(program);
	}
	
	private GButton back;
	private GButton showFireScreen;
	private GOval angleBackGround;
	private GRect fuelBackGround;
	private GameImage arrow;
	private GRect fuelOutline;
	private GRect fuel;
	private GLabel fuelText;
	private GImage moveRight;
	private GImage moveLeft;
	private GRect fireScreenBackGround;
	private GButton BACK;
	private GButton FIRE;
	private GImage powerDown;
	private GImage powerUp;
	private GLabel powerLevel;
	private GRect AngleBackGround;
	private GRect AmmoTypeBackGround;
	private GLabel angleLabel;
	private GImage currentShot;
	private GRect hoverShot;
	private GImage hoverShotImage;
	private GLabel hoverShotName;
	private GParagraph hoverShotDescription;
	private GameImage windImage;
	private GLabel windText;
	private GImage tutorial;
	
	private final static int ROTATE180 = 180;
	private final static int SMALL_BUTTON_SIZE = 50;
	private final static int ANGLE_BACKGROUND_SIZE = 350;
	private final static int ANGLE_ARROW_DISPLACEMENT = 175;
	private final static int ANGLE_ARROW_HEIGHT_DISPLACEMENT = 30;
	private final static int FIRE_BUTTON_SIZE = 100;
	private final static int FUEL_BACKGROUND_WIDTH = 125;
	private final static int FUEL_BACKGROUND_HEIGHT = 175;
	private final static int FUEL_HEIGHT = 145;
	private final static int FUEL_WIDTH = 85;
	private final static int FUEL_DISPLACEMENT = 105;
	private final static int FUEL_TEXT_WIDTH_DISPLACEMENT = 112;
	private final static int FUEL_TEXT_HEIGTH_DISPLACEMENT = 150;
	private final static int MEDIUM_BUTTON_SIZE = 75;
	private final static int FIRE_SCREEN_BC_WIDTH = 400;
	private final static int FIRE_SCREEN_BC_HEIGHT = 300;
	private final static int FIRE_SCREEN_BC_DISPLACEMENT = 50;
	private final static int ARROW_HEIGHT = 60;
	private final static int ANGLE_BG_WIDTH = 130;
	private final static int AMMO_TYPE_BG_WIDTH = 230;
	private final static int PROJECTILE_SIZE = 30;
	private final static int POWER_DOWN_DISPLACEMENT = 65;
	private final static int POWER_LEVEL_DISPLACEMENT = 80;
	private final static int POWER_DOWN_HEIGHT_DISPLACEMENT = 170;
	private final static int HOVER_SHOT_WIDTH_DISPLACEMENT = 190;
	private final static int HOVER_SHOT_WIDTH = 300;
	private final static int SHOT_DISPLACEMENT_WIDTH = 10;
	private final static int SHOT_DISPLACEMENT_HEIGHT = 220;
	private final static int ANGLE_LABEL_PLACEMENT_X = 188;
	private final static int ANGLE_LABEL_PLACEMENT_Y = 13;
	private final static int HOVERSHOT_Y = 260;
	private final static int HOVERSHOT_X = 85;
	private final static int HOVERSHOT_LABEL_X = 125;
	private final static int HOVERSHOT_LABEL_Y = 285;
	private final static int HOVERSHOT_DESCRIPTION_X = 105;
	private final static int HOVERSHOT_DESCRIPTION_Y = 320;
	private static final int EXPLOSION_HEIGHT = 100;
	private static final int WINDX_LOCATION = 600;
	private static final int WINDY_LOCATION = 200;
	private static final int WINDX_LOCATION_P2 = 100;
	private static final int WIND_TEXT_LOCATION_X = 611;
	private static final int WIND_TEXT_LOCATION_Y = 220;
	
	//METHOD drawUI()
	/* Sets the UI on the screen for player 1
	 * The values are constant for the size and position, 
	 * so be aware that they will change the configuration on the screen.
	 */
	
	private void drawUI()
	{
		back = new GButton("X",width-SMALL_BUTTON_SIZE, 0,SMALL_BUTTON_SIZE,SMALL_BUTTON_SIZE, Color.red);
		tutorial = new GImage("../media/Tutorial/tutorial_button.png");
		tutorial.setSize(SMALL_BUTTON_SIZE, SMALL_BUTTON_SIZE);
		
		angleBackGround = new GOval(-1* (ANGLE_BACKGROUND_SIZE/2), height - (ANGLE_BACKGROUND_SIZE/2), ANGLE_BACKGROUND_SIZE, ANGLE_BACKGROUND_SIZE);
		angleBackGround.setFillColor(Color.gray);
		angleBackGround.setFilled(true);
		
		arrow = new GameImage("../media/otherImages/arrow.png", - ANGLE_ARROW_DISPLACEMENT, height - ANGLE_ARROW_HEIGHT_DISPLACEMENT);
		arrow.rotate(ROTATE180);
		
		showFireScreen = new GButton("Fire", -1* FIRE_BUTTON_SIZE, height - FIRE_BUTTON_SIZE, FIRE_BUTTON_SIZE*2, Color.red, true);
		
		fuelBackGround = new GRect(width- FUEL_BACKGROUND_WIDTH, height - FUEL_BACKGROUND_HEIGHT, FUEL_BACKGROUND_WIDTH, FUEL_BACKGROUND_HEIGHT);
		fuelBackGround.setFillColor(Color.gray);
		fuelBackGround.setFilled(true);
		
		fuelOutline = new GRect(width- FUEL_DISPLACEMENT, height - FUEL_HEIGHT, FUEL_WIDTH, FUEL_HEIGHT);
		
		fuel = new GRect(width- FUEL_DISPLACEMENT, height - FUEL_HEIGHT, FUEL_WIDTH, FUEL_HEIGHT);
		fuel.setFillColor(Color.yellow);
		fuel.setFilled(true);
		fuelText = new GLabel("Fuel");
		fuelText.setFont("Arial-Bold-24");
		fuelText.setLocation(width - FUEL_TEXT_WIDTH_DISPLACEMENT + fuelText.getWidth()/2, height - FUEL_TEXT_HEIGTH_DISPLACEMENT);
		
		moveRight = new GImage("../media/otherImages/greenArrowRight.png");
		moveRight.setSize(MEDIUM_BUTTON_SIZE, MEDIUM_BUTTON_SIZE);
		moveLeft = new GImage("../media/otherImages/greenArrowLeft.png");
		moveLeft.setSize(MEDIUM_BUTTON_SIZE, MEDIUM_BUTTON_SIZE);
		
		fireScreenBackGround = new GRect(width- (FIRE_SCREEN_BC_HEIGHT*2), height - (FIRE_SCREEN_BC_WIDTH + FIRE_SCREEN_BC_DISPLACEMENT), FIRE_SCREEN_BC_WIDTH, FIRE_SCREEN_BC_HEIGHT);
		fireScreenBackGround.setFillColor(Color.gray);
		fireScreenBackGround.setFilled(true);
		BACK = new GButton("X",width-(FIRE_SCREEN_BC_HEIGHT - FIRE_SCREEN_BC_DISPLACEMENT), height - (FIRE_SCREEN_BC_WIDTH + FIRE_SCREEN_BC_DISPLACEMENT),SMALL_BUTTON_SIZE,SMALL_BUTTON_SIZE, Color.red);
		FIRE = new GButton("FIRE!", width-(FIRE_SCREEN_BC_WIDTH + (FIRE_SCREEN_BC_DISPLACEMENT * 2)), height-FIRE_SCREEN_BC_WIDTH, (FIRE_SCREEN_BC_WIDTH/2), Color.RED);
		
		powerDown = new GImage("../media/otherImages/powerDown.png");
		powerDown.setSize(MEDIUM_BUTTON_SIZE, ARROW_HEIGHT);
		powerDown.setLocation(width - (SMALL_BUTTON_SIZE*2), height - POWER_DOWN_DISPLACEMENT);
		
		powerUp = new GImage("../media/otherImages/powerUp.png");
		powerUp.setSize(MEDIUM_BUTTON_SIZE, ARROW_HEIGHT);
		powerUp.setLocation(width - (SMALL_BUTTON_SIZE*2), height - POWER_DOWN_HEIGHT_DISPLACEMENT);
		
		powerLevel = new GLabel("45");
		powerLevel.setFont("Arial-Bold-24");
		powerLevel.setLocation(width - (SMALL_BUTTON_SIZE*2) + fuelText.getWidth()/2, height - POWER_LEVEL_DISPLACEMENT);
		
		AmmoTypeBackGround = new GRect(0, height-AMMO_TYPE_BG_WIDTH, SMALL_BUTTON_SIZE, (SMALL_BUTTON_SIZE*2));
		AmmoTypeBackGround.setFillColor(Color.gray);
		AmmoTypeBackGround.setFilled(true);
		
		AngleBackGround = new GRect(ANGLE_BG_WIDTH, height - SMALL_BUTTON_SIZE, (SMALL_BUTTON_SIZE*2), SMALL_BUTTON_SIZE);
		AngleBackGround.setFillColor(Color.gray);
		AngleBackGround.setFilled(true);
		
		angleLabel = new GLabel("0°");
		angleLabel.setFont("Arial-Bold-24");
		angleLabel.setLocation(ANGLE_LABEL_PLACEMENT_X,height  - ANGLE_LABEL_PLACEMENT_Y);
		
		currentShot = new GImage("../media/Ammo/" + level.getNextShot().toString() + ".png");
		currentShot.setSize(PROJECTILE_SIZE, PROJECTILE_SIZE);
		currentShot.setLocation(SHOT_DISPLACEMENT_WIDTH, height-SHOT_DISPLACEMENT_HEIGHT);
		
		hoverShot = new GRect(MEDIUM_BUTTON_SIZE, HOVER_SHOT_WIDTH - SMALL_BUTTON_SIZE, HOVER_SHOT_WIDTH, HOVER_SHOT_WIDTH_DISPLACEMENT);
		hoverShot.setFillColor(Color.gray);
		hoverShot.setFilled(true);
		
		hoverShotImage = new GImage("../media/Ammo/" + level.getNextShot().toString() + ".png");
		hoverShotImage.setSize(PROJECTILE_SIZE, PROJECTILE_SIZE);
		hoverShotImage.setLocation(HOVERSHOT_X, HOVERSHOT_Y);
		
		hoverShotName = new GLabel(level.getNextShot().getDraftName());
		hoverShotName.setFont("Arial-Bold-24");
		hoverShotName.setLocation(HOVERSHOT_LABEL_X, HOVERSHOT_LABEL_Y);
		
		hoverShotDescription = new GParagraph(level.getNextShot().descriptionOfAmmo(),HOVERSHOT_DESCRIPTION_X, HOVERSHOT_DESCRIPTION_Y);
		hoverShotDescription.setFont("Arial-18");
		
		windImage = new GameImage("../media/otherImages/windImage.png");
		windImage.setLocation(WINDX_LOCATION, WINDY_LOCATION);
		windText = new GLabel(""+Math.abs(level.getWind()) + " Mph");
		windText.setFont("Arial-Bold-14");
		windText.setLocation(WIND_TEXT_LOCATION_X, WIND_TEXT_LOCATION_Y);

	}
	
	//METHOD decorationList()
	/*
	 * Draws the decorations for the type of world
	 */
	
	
	
	
	
	//METHOD drawClouds()
	/*Method populates creates an arrayList of clouds 
	 * with random heights and random sizes 
	 * 
	 */
	
	
	public static final int WIND_TEXT_LOCATION_X_LEFT = 663;
	public static final int WIND_TEXT_LOCATION_Y_LEFT = 278;
	public static final int WIND_TEXT_LOCATION_X_LEFT_P2 = 165;
	public static final int WIND_TEXT_LOCATION_Y_LEFT_P2 = 278;	
	public static final int WIND_TEXT_LOCATION_X_RIGHT_P2 = 111;
	public static final int WIND_TEXT_LOCATION_Y_RIGHT_P2 = 220;
	
	//METHOD drawWind()
	/*
	 * This adds the wind on the screen based on the wind 
	 * conditions in the board class
	 */
	
	public void drawWind()
	{
		program.remove(windImage);
		program.remove(windText);
		if(level.isPlayer1Turn())
		{
			windImage.setLocation(WINDX_LOCATION, WINDY_LOCATION);
			if(level.getWind() > 0)
			{
				windImage.setDegrees(0);
				program.add(windImage);
				windText.setLabel(""+Math.abs(level.getWind()) + " Mph");
				windText.setLocation(WIND_TEXT_LOCATION_X, WIND_TEXT_LOCATION_Y);
				program.add(windText);
			}
			else if (level.getWind() < 0)
			{
				windImage.setDegrees(ROTATE180);
				program.add(windImage);
				windText.setLabel(""+Math.abs(level.getWind()) + " Mph");
				windText.setLocation(WIND_TEXT_LOCATION_X_LEFT, WIND_TEXT_LOCATION_Y_LEFT);
				program.add(windText);
			}
		}
		else
		{
			windImage.setLocation(WINDX_LOCATION_P2, WINDY_LOCATION);
			if(level.getWind() > 0)
			{
				windImage.setDegrees(ROTATE180);
				program.add(windImage);
				windText.setLabel(""+Math.abs(level.getWind()) + " Mph");
				windText.setLocation(WIND_TEXT_LOCATION_X_LEFT_P2, WIND_TEXT_LOCATION_Y_LEFT_P2);
				program.add(windText);
			}
			else if (level.getWind() < 0)
			{
				windImage.setDegrees(0);
				program.add(windImage);
				windText.setLabel(""+Math.abs(level.getWind()) + " Mph");
				windText.setLocation(WIND_TEXT_LOCATION_X_RIGHT_P2, WIND_TEXT_LOCATION_Y_RIGHT_P2);
				program.add(windText);
			}
		}
	}
	
	private boolean hideUI;
	
	//METHOD hideUI()
	/* Method hides all the UI  
	 * Called when tank shoots
	 */
	
	public void hideUI()
	{
		hideUI = true;

	    toggleShotDescription(false);
		
		program.remove(back);
		program.remove(tutorial);
		program.remove(angleBackGround);
		program.remove(arrow);
		program.remove(showFireScreen);
		program.remove(fuelBackGround);
		program.remove(powerUp);
		program.remove(powerDown);
		program.remove(powerLevel);
		program.remove(moveRight);
		program.remove(moveLeft);
		program.remove(fuel);
		program.remove(fuelText);
		program.remove(fuelOutline);
		program.remove(AngleBackGround);
		program.remove(AmmoTypeBackGround);
		program.remove(angleLabel);
		program.remove(currentShot);
	}
	
	private final static int POWER_DISPLACEMENT_P1 = 100;
	private final static int POWER_DISPLACEMENT_P2 = 20;
	private final static int HOVER_SHOT_DISPLACEMENT = 375;
	private final static int HOVER_SHOT_TEXT_DISPLACEMENT = 325;
	private final static int ANGLE_TEXT_DISPLACEMENT = 215;
	private final static int FUEL_DISPLACEMENT_P2 = 15;
	private final static int ARROW_DISPLACEMENT = 160;
	
	//METHOD showUI()
		/*Method puts all the UI on the screen
		 * Displays all the UI for the current player.
		 */
	
	public void showUI(boolean player)
	{
		hideUI = false;
		if(isGameOver)
		{
//			timer.stop();
//			moveTimer.stop();
//			AITimer.stop();
//			moveTankTimer.stop();
			
			program.switchToWinScreen(isWinnerPlayer1, isSinglePlayer);
		}
		else if(player)
		{
			
			angleBackGround.setLocation(-ANGLE_ARROW_DISPLACEMENT, height - ANGLE_ARROW_DISPLACEMENT);
			if(!hiding && !isGamePaused)
			{
				recompileArrowAngle(player);
				arrow.setLocation(-1 * ARROW_DISPLACEMENT, height - PROJECTILE_SIZE);
				setArrowCorrectAngle(player);
			}
			
			showFireScreen.setLocation(-1 * FIRE_BUTTON_SIZE, height - FIRE_BUTTON_SIZE);
			showFireScreen = new GButton("Fire", -1 * FIRE_BUTTON_SIZE, height - FIRE_BUTTON_SIZE, FIRE_BUTTON_SIZE*2, Color.red, true);
			fuelBackGround.setLocation(width - FUEL_BACKGROUND_WIDTH, height - FUEL_BACKGROUND_HEIGHT);
			powerDown.setLocation(width - POWER_DISPLACEMENT_P1, height - POWER_DOWN_DISPLACEMENT);
			powerUp.setLocation(width - POWER_DISPLACEMENT_P1, height - POWER_DOWN_HEIGHT_DISPLACEMENT);
			powerLevel.setLocation(width - POWER_DISPLACEMENT_P1 + fuelText.getWidth()/2, height - POWER_LEVEL_DISPLACEMENT);
			
			AmmoTypeBackGround.setLocation(0, height-(SHOT_DISPLACEMENT_HEIGHT+SHOT_DISPLACEMENT_WIDTH));
			AngleBackGround.setLocation(ANGLE_BG_WIDTH, height - SMALL_BUTTON_SIZE);
			
			angleLabel.setLabel((int)t[0].getAngle()+ "°");
			angleLabel.setLocation(ANGLE_LABEL_PLACEMENT_X,height  - ANGLE_LABEL_PLACEMENT_Y);
			
			fuelOutline.setLocation(width- FUEL_DISPLACEMENT, height - FUEL_HEIGHT);
			recompileFuel(player);
			fuelText.setLocation(width - FUEL_TEXT_WIDTH_DISPLACEMENT + fuelText.getWidth()/2, height - FUEL_TEXT_HEIGTH_DISPLACEMENT);
			
			moveRight.setLocation(tank1.getX() + ANGLE_BG_WIDTH, tank1.getY());
			moveLeft.setLocation(tank1.getX() - FIRE_BUTTON_SIZE, tank1.getY());
			
			powerLevel.setLabel("" + power1);
			setPowerTextColor();
			
			currentShot.setImage("../media/Ammo/" + level.getNextShot().toString() + ".png");
			currentShot.setSize(PROJECTILE_SIZE, PROJECTILE_SIZE);
			currentShot.setLocation(SHOT_DISPLACEMENT_WIDTH, height-SHOT_DISPLACEMENT_HEIGHT);
			
			hoverShot.setLocation(MEDIUM_BUTTON_SIZE, (HOVERSHOT_Y -SHOT_DISPLACEMENT_WIDTH));
			
			hoverShotImage.setImage("../media/Ammo/" + level.getNextShot().toString() + ".png");
			hoverShotImage.setSize(PROJECTILE_SIZE, PROJECTILE_SIZE);
			hoverShotImage.setLocation(HOVERSHOT_X, HOVERSHOT_Y);
			
			hoverShotName.setLabel(level.getNextShot().getDraftName());
			hoverShotName.setLocation(HOVERSHOT_LABEL_X, HOVERSHOT_LABEL_Y);
			
			hoverShotDescription = new GParagraph(level.getNextShot().descriptionOfAmmo(),HOVERSHOT_DESCRIPTION_X, HOVERSHOT_DESCRIPTION_Y);
			hoverShotDescription.setFont("Arial-18");
			
			program.add(back);
			program.add(tutorial);
			program.add(AngleBackGround);
			program.add(AmmoTypeBackGround);
			program.add(angleBackGround);
			program.add(arrow);
			program.add(showFireScreen);
			program.add(fuelBackGround);
			program.add(powerUp);
			program.add(powerDown);
			program.add(powerLevel);
			program.add(angleLabel);
			program.add(currentShot);
			drawWind();
		}
		else if(isSinglePlayer)
		{
			recompileArrowAngle(player);
			arrow.setLocation(width- HOVER_SHOT_WIDTH_DISPLACEMENT, height - PROJECTILE_SIZE);
			setArrowCorrectAngle(player);
			haveAIShoot();
		}
		else 
		{		
			angleBackGround.setLocation(width - ANGLE_ARROW_DISPLACEMENT, height - ANGLE_ARROW_DISPLACEMENT);
			
			if(!hiding && !isGamePaused)
			{
				recompileArrowAngle(player);
				arrow.setLocation(width- HOVER_SHOT_WIDTH_DISPLACEMENT, height - PROJECTILE_SIZE);
				setArrowCorrectAngle(player);
			}
			
			showFireScreen = new GButton("Fire", -1 * FIRE_BUTTON_SIZE, height -FIRE_BUTTON_SIZE, FIRE_BUTTON_SIZE*2, Color.red, false);
			showFireScreen.setLocation(width - FIRE_BUTTON_SIZE, height -FIRE_BUTTON_SIZE);
			fuelBackGround.setLocation(0, height - FUEL_BACKGROUND_HEIGHT);
			powerDown.setLocation(POWER_DISPLACEMENT_P2, height - POWER_DOWN_DISPLACEMENT);
			powerUp.setLocation(POWER_DISPLACEMENT_P2, height - POWER_DOWN_HEIGHT_DISPLACEMENT);
			powerLevel.setLocation(fuelText.getWidth()/2 + POWER_DISPLACEMENT_P2, height - POWER_LEVEL_DISPLACEMENT);
			
			AmmoTypeBackGround.setLocation(width-SMALL_BUTTON_SIZE, height - AMMO_TYPE_BG_WIDTH);
			AngleBackGround.setLocation(width - AMMO_TYPE_BG_WIDTH, height - SMALL_BUTTON_SIZE);
			
			
			fuelOutline.setLocation(FUEL_DISPLACEMENT_P2, height - FUEL_HEIGHT);
			recompileFuel(player);
			fuelText.setLocation(SHOT_DISPLACEMENT_WIDTH + fuelText.getWidth()/2, height - FUEL_TEXT_HEIGTH_DISPLACEMENT);
			
			angleLabel.setLabel((int)t[1].getAngle() +"°");
			angleLabel.setLocation(width-ANGLE_TEXT_DISPLACEMENT,height  - ANGLE_LABEL_PLACEMENT_Y);
			
			moveRight.setLocation(tank2.getX() + ANGLE_BG_WIDTH, tank2.getY());
			moveLeft.setLocation(tank2.getX() - FIRE_BUTTON_SIZE, tank2.getY());
			
			powerLevel.setLabel("" + power2);
			setPowerTextColor();
			
			currentShot.setImage("../media/Ammo/" + level.getNextShot().toString() + ".png");
			currentShot.setSize(PROJECTILE_SIZE, PROJECTILE_SIZE);
			currentShot.setLocation(width - (SHOT_DISPLACEMENT_WIDTH + PROJECTILE_SIZE), height-SHOT_DISPLACEMENT_HEIGHT);
			
			hoverShot.setLocation(width - HOVER_SHOT_DISPLACEMENT, (HOVERSHOT_Y -SHOT_DISPLACEMENT_WIDTH));
			
			hoverShotImage.setImage("../media/Ammo/" + level.getNextShot().toString() + ".png");
			hoverShotImage.setSize(PROJECTILE_SIZE, PROJECTILE_SIZE);
			hoverShotImage.setLocation(width - (HOVER_SHOT_DISPLACEMENT - SHOT_DISPLACEMENT_WIDTH), HOVERSHOT_Y);
			
			hoverShotName.setLabel(level.getNextShot().getDraftName());
			hoverShotName.setLocation(width - HOVER_SHOT_TEXT_DISPLACEMENT, HOVERSHOT_LABEL_Y); 
			
			hoverShotDescription = new GParagraph(level.getNextShot().descriptionOfAmmo(), width - (HOVER_SHOT_DISPLACEMENT-PROJECTILE_SIZE), HOVERSHOT_DESCRIPTION_Y);
			hoverShotDescription.setFont("Arial-18");
			
			
			program.add(back);
			program.add(tutorial);
			program.add(AngleBackGround);
			program.add(AmmoTypeBackGround);
			program.add(angleBackGround);
			program.add(arrow);
			program.add(showFireScreen);
			program.add(fuelBackGround);
			program.add(powerUp);
			program.add(powerDown);
			program.add(powerLevel);
			program.add(angleLabel);
			program.add(currentShot);
			drawWind();
		}
	}

	//OVERRIDE showContents()
	 /* This method displays all the contents onto the screen when 
	  * entering into gamePane
	  */	
	
	@Override
	public void showContents() {
		
		program.add(backGround);
		
		for(int i = 0; i< terrainList.size(); i++)
		{
			program.add(terrainList.get(i));
		}
		
		decor.add();
				
		program.add(cannon1);
		program.add(cannon2);
		program.add(tank1);
		program.add(tank2);
		program.add(hOutline1);
		program.add(h1);
		program.add(hOutline2);
		program.add(h2);
		
		
		//int setBack = currentTerrainPos;
		//currentTerrainPos = 0;
		//moveTerrain(setBack, false);
		
		hideUI = false;
		hidingShot = true;
		isCurrentlyMoving = false;
		movingTank = false;
		fireScreenUp = false;
		hiding = false;
		showUI(level.isPlayer1Turn());	
		centerOnFiringTank(level.isPlayer1Turn());
		isGamePaused = false;
	}
	
	private boolean hiding;
	
	//OVERRIDE hideContents()
	/*
	 * Removes everything from the screen.
	 */	

	@Override
	public void hideContents() {
		centerOnFiringTank(true);
		program.removeAll();
		hiding = true;	
		tutorialObj.setShowingTutorial(false);
	}

	private int power1;
	private int power2;
	private GObject obj;

	private boolean moveTankToRight;
	private int moveTankCount;
	private boolean isCurrentlyMoving;
	
	private final static double MAX_POWER_OF_TANK = 147.5;
	private final static double MIN_POWER_OF_TANK = 25;
	private final static double REDUCE_INCREASE = 120;
	private final static double LOW_POWER_INCREASE = 2.5;
	private final static double HIGH_POWER_INCREASE = 5;
	private final static int MOVE_SCREEN_BOUND = 150;

	 //OVERRIDE  mousePressed()
	 /*
	  * Gets the element at the mouse position and triggers the appropraite functions
	  * based on what the user selects
	  */	

	@Override
	public void mousePressed(MouseEvent e) {
		obj = null;
		if(!(program.getElementAt(e.getX(), e.getY()) instanceof GObject)) return;
		lastx = e.getX();
		lasty = e.getY();
		
		obj = program.getElementAt(e.getX(), e.getY());
		
		if (obj == back && !isCurrentlyMoving) 
		{
			isGamePaused = true;
			program.pausegame();
		}
		else if (obj == tutorial && !tutorialObj.isShowingTutorial())
		{
			tutorialObj.toggleTutorial(!tutorialObj.isShowingTutorial());
		}
		if(!tutorialObj.isShowingTutorial() && !isCurrentlyMoving)
		{
			if(obj == showFireScreen)
			{
				toggleShotDescription(false);
				showFireScreen();
			}
			else if(obj == tank1 || obj == cannon1)
			{
				if(!hideUI && level.isPlayer1Turn())
					toggleTankMoveScreen();
			}
			else if(obj == tank2 || obj == cannon2)
			{
				if(!hideUI && !level.isPlayer1Turn())
					toggleTankMoveScreen();
			}
			else if(obj == BACK)
			{
				hideFireScreen();
			}
			else if (obj == FIRE)
			{
				hideFireScreen();
				fireTank();
			}
			else if (obj == powerUp)
			{
				if (level.isPlayer1Turn()) 
				{	
					if(t[0].getPower() < MAX_POWER_OF_TANK)
					{
						if(t[0].getPower() >= REDUCE_INCREASE) 
							t[0].setPower(t[0].getPower()+LOW_POWER_INCREASE);
						else
							t[0].setPower(t[0].getPower()+HIGH_POWER_INCREASE);
						
						powerLevel.setLabel(""+ ++power1);
					}
				}
				else
				{	
					if(t[1].getPower() < MAX_POWER_OF_TANK)
					{
						if(t[1].getPower() >= REDUCE_INCREASE) 
							t[1].setPower(t[1].getPower()+LOW_POWER_INCREASE);
						else
							t[1].setPower(t[1].getPower()+HIGH_POWER_INCREASE);	
						
						powerLevel.setLabel(""+ ++power2);
					}
					
				}
				setPowerTextColor();
			}
			else if(obj == powerDown)
			{
				if (level.isPlayer1Turn()) 
				{	
					if(t[0].getPower() > MIN_POWER_OF_TANK)
					{
						if(t[0].getPower() > REDUCE_INCREASE)
							t[0].setPower(t[0].getPower()-LOW_POWER_INCREASE);
						else
							t[0].setPower(t[0].getPower()-HIGH_POWER_INCREASE);
						
						powerLevel.setLabel(""+ --power1);
					}
				}
				else
				{
					if(t[1].getPower() > MIN_POWER_OF_TANK)
					{
						if(t[1].getPower() > REDUCE_INCREASE)
							t[1].setPower(t[1].getPower()-LOW_POWER_INCREASE);
						else
							t[1].setPower(t[1].getPower()-HIGH_POWER_INCREASE);
						
						powerLevel.setLabel(""+ --power2);
					}
				}
				setPowerTextColor();
			}
			else if(obj == moveLeft && !isCurrentlyMoving)
			{
				if(level.moveTank(false))
				{	
					moveTankCount = 0;
					moveTankToRight = false;
					isCurrentlyMoving = true;
					timesP1MovedLeft++;
					moveTankTimer.start();
				}
			}
			else if(obj == moveRight && !isCurrentlyMoving)
			{
				if(level.moveTank(true))
				{
					moveTankCount = 0;
					moveTankToRight = true;
					isCurrentlyMoving = true;
					timesP1MovedRight++;
					moveTankTimer.start();
				}
			}
			else if(obj == backGround && !firing && !movingTank)
			{
				if(e.getX() < MOVE_SCREEN_BOUND)
					{
						moveScreenRight = true;
						moveTimer.start();
					}
				else if(e.getX() > width - MOVE_SCREEN_BOUND)
					{
						moveScreenRight = false;
						moveTimer.start();
					}
			}
			
			if(!firing && !movingTank)
			{
				if(decor.isInList(obj))
				{
					if(e.getX() < MOVE_SCREEN_BOUND)
					{
						moveScreenRight = true;
						moveTimer.start();
					}
					else if(e.getX() > width - MOVE_SCREEN_BOUND)
					{
						moveScreenRight = false;
						moveTimer.start();
					}
				}
			
				if(windImage != null && obj == windImage || obj == windText)
				{
					if(e.getX() < MOVE_SCREEN_BOUND)
					{
						moveScreenRight = true;
						 moveTimer.start();
					}
					else if(e.getX() > width - MOVE_SCREEN_BOUND)
					{
						moveScreenRight = false;
						moveTimer.start();
					}
				}
			}
		}
		else
		{
			if(!tutorialObj.isTutorialInAnimation())
			{
				if(tutorialObj.isTouching(obj))
				{
					tutorialObj.incrementTutorial();
				}
				if(tutorialObj.isText(obj))
					tutorialObj.incrementTutorial();
			}
		}
	}
	
	@Override
	public void mouseMoved(MouseEvent e) 
	{
		obj = program.getElementAt(e.getX(), e.getY());
		if(obj == AmmoTypeBackGround || obj == currentShot)
		{
				toggleShotDescription(true);
		}
		else 
		{
			toggleShotDescription(false);
		}
	}
	
	private boolean moveScreenRight;
	
	 //OVERRIDE  mouseReleased()
	 /*
	  * stops a moving tank timer
	  */	
	
	@Override
	public void mouseReleased(MouseEvent e)
	{
		moveTimer.stop();
	}
	
	private boolean movingTank;
	
	private final static int MOVE_RIGHT_ARROW_LOCATION = 130;
	private final static int MOVE_LEFT_ARROW_LOCATION = 100;
	
	//METHOD toggleTankMoveScreen()
	/*
	 * toggles between a moving tank screen and a power selection screen
	 */	
	
	private void toggleTankMoveScreen()
	{
		if(!movingTank)
		{
			program.remove(powerUp);
			program.remove(powerDown);
			program.remove(powerLevel);
			
			program.add(fuel);
			program.add(fuelOutline);
			program.add(fuelText);
			
			if(level.isPlayer1Turn())
			{
				moveRight.setLocation(tank1.getX() + MOVE_RIGHT_ARROW_LOCATION, tank1.getY());
				moveLeft.setLocation(tank1.getX() - MOVE_LEFT_ARROW_LOCATION, tank1.getY());
			}
			else
			{
				moveRight.setLocation(tank2.getX() + MOVE_RIGHT_ARROW_LOCATION, tank2.getY());
				moveLeft.setLocation(tank2.getX() - MOVE_LEFT_ARROW_LOCATION, tank2.getY());
			}
			program.add(moveRight);
			program.add(moveLeft);
			
			movingTank = true;
		}
		else
		{
			program.remove(fuel);
			program.remove(fuelOutline);
			program.remove(fuelText);
			program.remove(moveRight);
			program.remove(moveLeft);
			
			program.add(powerUp);
			program.add(powerDown);
			program.add(powerLevel);
			
			movingTank = false;
		}
	}
	
	boolean fireScreenUp;
	
	//METHOD showFireScreen() 
    /*
     * displays the "Big red button" to fire tank
	 */	
	
	private void showFireScreen() 
	{
		program.add(fireScreenBackGround);
		program.add(BACK);
		program.add(FIRE);
		fireScreenUp = true;
	}
	
	//METHOD hideFireScreen() 
    /*
     * displays the "Big red button" to fire tank
	 */	
	
	private void hideFireScreen()
	{
		program.remove(fireScreenBackGround);
		program.remove(BACK);
		program.remove(FIRE);
		fireScreenUp = false;
	}
	
	//METHOD recompileFuel(boolean player)
    /*
     * updates the image with the current fuel level
	 */	
	
	private void recompileFuel(boolean player)
	{
		if(player)
		{
			fuel.setLocation(width - FUEL_DISPLACEMENT, height - FUEL_HEIGHT + t[0].getFuel());
		}
		else
		{
			fuel.setLocation(FUEL_DISPLACEMENT_P2, height - FUEL_HEIGHT + t[1].getFuel());
		}
	}
	
	private final static int TERRAIN_MIN = 20;
	
	//METHOD drawTerrain(int width, int height)
    /*
     * Draws the terrain onto the screen pixel column by pixel column
	 */	
	
	private void drawTerrain(int width, int height) 
	{
		terrainList = new ArrayList<GRect>();
		
		ArrayList<Cordinate> terrain = level.getTerrain();
		
		for(int i = 0; i < width; i++)
		{
			GRect grass = new GRect(terrain.get(i).getX(),terrain.get(i).getY(), 1, TERRAIN_MIN);
			grass.setColor(world.getTopGroundColor());
			grass.setFilled(true);
			terrainList.add(grass);
			GRect dirt = new GRect(terrain.get(i).getX(), terrain.get(i).getY() + TERRAIN_MIN, 1, height - terrain.get(i).getY()+TERRAIN_MIN);
			dirt.setColor(world.getGroundColor());
			dirt.setFilled(true);
			terrainList.add(dirt);
		}
	}
	
	private GRect backGround;
	
	//METHOD drawBackGround()
    /*
     * Draws a blue background to the screen
	 */	
	
	private void drawBackGround()
	{
		
		backGround = new GRect(0,0, width, height);
		backGround.setFillColor(world.getSkyColor());
		backGround.setFilled(true);

	}
	
	private final static int TANK_HEIGHT = 50;
	private final static int HEALTH_DISPLACEMENT = 75;
	private final static int HEALTH_WIDTH = 10;
	private final static int CANNON_HEIGHT_DISPLACEMENT = 38;
	private final static int CANNON1_DISPLACEMENT = 85;//70
	private final static int CANNON2_DISPLACEMENT = 65;//80
	
	//METHOD drawTanks()
    /*
     * Draws both tanks to the screen and the player health bars
	 */	
	
	private void drawTanks()
	{

		tank1 = new GImage(p1Color.getTankColor(true), t[0].getTankCurrentPos().getX()-TANK_HEIGHT, t[0].getTankCurrentPos().getY()-TANK_HEIGHT);
		cannon1 = new GameImage(p1Color.getCannonColor(), t[0].getTankCurrentPos().getX()-CANNON1_DISPLACEMENT, t[0].getTankCurrentPos().getY()-CANNON_HEIGHT_DISPLACEMENT);
		
		hOutline1 = new GRect(t[0].getTankCurrentPos().getX()-(TANK_HEIGHT/2), t[0].getTankCurrentPos().getY()-HEALTH_DISPLACEMENT, TANK_HEIGHT,  HEALTH_WIDTH);
		h1 = new GRect(t[0].getTankCurrentPos().getX()-((TANK_HEIGHT/2)-1), t[0].getTankCurrentPos().getY()-(HEALTH_DISPLACEMENT-1), TANK_HEIGHT-2,  HEALTH_WIDTH-2);
		h1.setColor(Color.red);
		h1.setFilled(true);
		
		cannon1.rotate(Math.abs((int)((ROTATE180*2)-t[0].getAngle())));
		
		tank2 = new GImage(p2Color.getTankColor(false), t[1].getTankCurrentPos().getX()-TANK_HEIGHT, t[1].getTankCurrentPos().getY()-TANK_HEIGHT);
		cannon2 = new GameImage(p2Color.getCannonColor(), t[1].getTankCurrentPos().getX()-CANNON2_DISPLACEMENT, t[1].getTankCurrentPos().getY()-CANNON_HEIGHT_DISPLACEMENT);
		
		hOutline2 = new GRect(t[1].getTankCurrentPos().getX()-(TANK_HEIGHT/2), t[1].getTankCurrentPos().getY()-HEALTH_DISPLACEMENT, TANK_HEIGHT, HEALTH_WIDTH);
		h2 = new GRect(t[1].getTankCurrentPos().getX()-((TANK_HEIGHT/2)-1), t[1].getTankCurrentPos().getY()-(HEALTH_DISPLACEMENT-1), TANK_HEIGHT-2, HEALTH_WIDTH-2);
		h2.setColor(Color.red);
		h2.setFilled(true);
		
		cannon2.rotate(Math.abs((int)(ROTATE180+t[1].getAngle())));
	}
	
	//METHOD recompileTankAndArrows(boolean isPlayer1, int direction)
    /*
     * Moves the move arrows, tank and health bar to the correct location after they are moved
	 */	
	
	private void recompileTankAndArrows(boolean isPlayer1)
	{
		if(isPlayer1)
		{
			tank1.setLocation(tank1.getX(), t[0].getTankCurrentPos().getY()-TANK_HEIGHT);
			cannon1.rotate(angle1);
			cannon1.setLocation(cannon1.getX(),  t[0].getTankCurrentPos().getY()-CANNON_HEIGHT_DISPLACEMENT);
			cannon1.rotate(-1* angle1);
			moveRight.setLocation(tank1.getX() + MOVE_RIGHT_ARROW_LOCATION, tank1.getY());
			moveLeft.setLocation(tank1.getX() - (TANK_HEIGHT*2), tank1.getY());
			h1.setLocation(h1.getX(), t[0].getTankCurrentPos().getY()-(HEALTH_DISPLACEMENT-1));
			hOutline1.setLocation(hOutline1.getX(), t[0].getTankCurrentPos().getY()-HEALTH_DISPLACEMENT);
		}
		else
		{
			tank2.setLocation(tank2.getX(), t[1].getTankCurrentPos().getY()-TANK_HEIGHT);
			cannon2.rotate(-1 * (angle2 - ROTATE180));
			cannon2.setLocation(cannon2.getX(),   t[1].getTankCurrentPos().getY()-CANNON_HEIGHT_DISPLACEMENT);
			cannon2.rotate(angle2 - ROTATE180);
			moveRight.setLocation(tank2.getX() + MOVE_RIGHT_ARROW_LOCATION, tank2.getY());
			moveLeft.setLocation(tank2.getX() - (TANK_HEIGHT*2), tank2.getY());
			h2.setLocation(h2.getX(), t[1].getTankCurrentPos().getY()-(HEALTH_DISPLACEMENT-1));
			hOutline2.setLocation(hOutline2.getX(), t[1].getTankCurrentPos().getY()-HEALTH_DISPLACEMENT);
		}
	}
	
	GameImage projectileObject;
	ArrayList<Cordinate> flightPath;
	private boolean firing;
	private int increment;
	private int count;
	private int explosionTiming;
	private GImage explosion;
	private projectileType currentShotType;
	
	//private final static int FIRING_PROJECTILE_SIZE = 25;
	
	//METHOD fireTank()
    /*
     * Fire the current tank with the selected angle and power
     * After this method the screen disappears preventing the player from changing any inputs
	 */	
	
	private void fireTank()
	{
		if(level.isPlayer1Turn())
		{
			currentShotType = t[0].getCurrentShot();
			projectileObject = new GameImage("../media/ammo/" + currentShotType.getProjectileImage() + ".png");
			isShootingProjectileRight = true;
			projectileObject.rotate(-1 * angle1);
		}
		else 
		{	
			currentShotType = t[1].getCurrentShot();
			projectileObject = new GameImage("../media/ammo/" + currentShotType.getProjectileImage()  + ".png");
			isShootingProjectileRight = false;
			projectileObject.rotate(angle2);
		}	
		
		centerOnFiringTank(level.isPlayer1Turn());
		
		flightPath = level.shootTank();
		
		if(level.isPlayer1Turn())
			projectileObject.setLocation(flightPath.get(0).getX() - currentTerrainPos,flightPath.get(0).getY());
		else
		{
			projectileObject.setLocation(flightPath.get(0).getX() - currentTerrainPos - TANK_HEIGHT, flightPath.get(0).getY());
		}
		
		hideUI();
		lastHeightOfImageRotation = flightPath.get(0).getY();
		count = 0;
		increment = 1;
		explosionTiming = 0;
		firing = true;
		timer.start();
	}
	
	
	private int lastx;
	private int lasty;
	private int angle1;
	private int angle2;
	
	private final static int DEGREE90 = 90;
	private final static int DEGREE270 = 270;
	
	//OVERRIDE mouseDragged()
    /*
     * This method rotates the arrow image when selected 
     * and stops the screen movement if the players mouse exits a range
	 */	
	
	@Override
	public void mouseDragged(MouseEvent e)
	{
		if(obj == arrow)
		{
			if(level.isPlayer1Turn())
			{
				if((lasty - e.getY()) > 0 ||(lastx - e.getX()) > 0)
				{
					if(angle1 < DEGREE90)
					{
						t[0].setAngle(t[0].getAngle()+1);
						angleLabel.setLabel((int)t[0].getAngle() +"°");
						arrow.rotate(-1);
						cannon1.rotate(-1);
						angle1 ++;
						lasty = e.getY();
						lastx = e.getX();
					}	
				}
				else 
				{
					if(angle1  > 0)
					{
						t[0].setAngle(t[0].getAngle()-1);
						angleLabel.setLabel((int)t[0].getAngle() +"°");
						arrow.rotate(1);
						cannon1.rotate(1);
						angle1--;
						lasty = e.getY();
						lastx = e.getX();
					}	
				}
			}
			else
			{
				if((lasty - e.getY()) > 0 ||(e.getX() - lastx) > 0)
				{
					if(angle2 < DEGREE270)
					{
						t[1].setAngle(t[1].getAngle()+1);
						angleLabel.setLabel((int)t[1].getAngle() +"°");
						arrow.rotate(1);
						cannon2.rotate(1);
						angle2 ++;
						lasty = e.getY();
						lastx = e.getX();
					}	
				}
				else 
				{
					if(angle2  > ROTATE180)
					{
						t[1].setAngle(t[1].getAngle()-1);
						angleLabel.setLabel((int)t[1].getAngle() +"°");
						arrow.rotate(-1);
						cannon2.rotate(-1);
						angle2--;
						lasty = e.getY();
						lastx = e.getX();
					}	
				}
			}
		}
		else if (obj == backGround)
		{
			if(e.getX() > MOVE_SCREEN_BOUND && e.getX() < width-MOVE_SCREEN_BOUND)
				moveTimer.stop();
		}
		
		if(decor.isInList(obj))
		{
			if(e.getX() > MOVE_SCREEN_BOUND && e.getX() < width-MOVE_SCREEN_BOUND)
				moveTimer.stop();
		}
	}
	
	//METHOD recompileArrowAngle(boolean player)
    /*
     * This method rotates the arrow image to original position 
     * and then flips it 180 degrees
	 */	
	
	private void recompileArrowAngle(boolean player)
	{
		if(player)
		{
			if(isSinglePlayer)
				arrow.rotate((-1 * ROTATE180));
			else
				arrow.rotate((-1 * angle2));
		}
		else
		{
			arrow.rotate(angle1);
		}
		arrow.rotate(ROTATE180);
	}
	
	//METHOD setArrowCorrectAngle(boolean player)
    /*
     * This method rotates the angle arrow to the previous players position
	 */	
	
	private void setArrowCorrectAngle(boolean player)
	{
		if(player)
		{
			arrow.rotate((-1 * angle1));
		}
		else
		{
			if(isSinglePlayer)
				arrow.rotate(ROTATE180);
			else
				arrow.rotate(angle2);
		}
		arrow.rotate(ROTATE180);
	}
	
	private int currentTerrainPos = 0;
	
	//METHOD  moveTerrain(int x, boolean moveTanks)
    /*
     * This method moves the terrain and tanks if boolean is selected true
     * a number of pixels x 
     * 
     * This method was very hard to write please do not modify without everyone being present
	 */	
	
	private boolean moveTerrain(int x, boolean moveTanks)
	{
		if(0 <= currentTerrainPos + x  && width*2 >= currentTerrainPos + x )
		{
			for(int i = 0; i < terrainList.size(); i++)
			{
				terrainList.get(i).setLocation(terrainList.get(i).getX() - x, terrainList.get(i).getY());
			}
			
			decor.move(x);
			
			windImage.setLocation(windImage.getX()- x, windImage.getY());
			windText.setLocation(windText.getX()- x, windText.getY());
			
			if(moveTanks)
			{
				tank1.setLocation(tank1.getX() - x, tank1.getY());
				cannon1.setLocation(cannon1.getX() - x, cannon1.getY());
				tank2.setLocation(tank2.getX() - x, tank2.getY());
				cannon2.setLocation(cannon2.getX() - x, cannon2.getY());
				h1.setLocation(h1.getX() - x, h1.getY());
				h2.setLocation(h2.getX() - x, h2.getY());
				hOutline1.setLocation(hOutline1.getX() - x, hOutline1.getY());
				hOutline2.setLocation(hOutline2.getX() - x, hOutline2.getY());
			}
			else
			{
				if(!isGamePaused)
				{	
					if(level.isPlayer1Turn())
					{
						tank2.setLocation(tank2.getX() - x, tank2.getY());
						cannon2.setLocation(cannon2.getX() - x, cannon2.getY());
						h2.setLocation(h2.getX() - x, h2.getY());
						hOutline2.setLocation(hOutline2.getX() - x, hOutline2.getY());
					}
					else
					{
						tank1.setLocation(tank1.getX() - x, tank1.getY());
						cannon1.setLocation(cannon1.getX() - x, cannon1.getY());
						h1.setLocation(h1.getX() - x, h1.getY());
						hOutline1.setLocation(hOutline1.getX() - x, hOutline1.getY());
					}
				}
			}
			
			currentTerrainPos += x;
			return true;
		}
		else
		{
			if(!moveTanks)
			{
				if(level.isPlayer1Turn())
				{
					tank1.setLocation(tank1.getX() + x, tank1.getY());
					cannon1.setLocation(cannon1.getX() + x, cannon1.getY());
					h1.setLocation(h1.getX() + x, h1.getY());
					hOutline1.setLocation(hOutline1.getX() + x, hOutline1.getY());
				}
				else
				{
					tank2.setLocation(tank2.getX() + x, tank2.getY());
					cannon2.setLocation(cannon2.getX() + x, cannon2.getY());
					h2.setLocation(h2.getX() + x, h2.getY());
					hOutline2.setLocation(hOutline2.getX() + x, hOutline2.getY());
				}
			}
		}
		return false;
	}
	
	
	//METHOD  centerOnFiringTank(boolean isPlayer1)
    /*
     * This method moves the terrain to the firing tank
     * 
     * This method is very confusing, so do not attempt to modify without the team present
	 */	
	
	private void centerOnFiringTank(boolean isPlayer1)
	{
		if(isPlayer1)
		{
			if(!moveTerrain((t[0].getTankCurrentPos().getX() - width/2) - currentTerrainPos, true))
			{
				moveTerrain(-1*(currentTerrainPos - 1), true);
			}
		}
		else
		{
			if(!moveTerrain((t[1].getTankCurrentPos().getX() - width/2) - currentTerrainPos , true))
			{
				moveTerrain(width*2 - currentTerrainPos - 1, true);
			}
		}
	}
	
	boolean hidingShot;
	
	//METHOD  toggleShotDescription()
    /*
     * Simply displays and hides the shotDescription
	 */	
	
	public void toggleShotDescription(boolean show)
	{
		if(show)
		{
			program.add(hoverShot);
			program.add(hoverShotImage);
			program.add(hoverShotName);
			program.add(hoverShotDescription);
		}
		else
		{
			program.remove(hoverShot);
			program.remove(hoverShotImage);
			program.remove(hoverShotName);
			program.remove(hoverShotDescription);
		}
	}
	
	private boolean isGameOver;
	private boolean isWinnerPlayer1;
	
	private final static int MAX_HEALTH = 48;
	private final static int HEALTH_DEDUCTION = 12;
	private final static int MAX_TIMES_HIT = 4;
	
	//METHOD  updatePlayerHealth()
    /*
     * updates each players health and determines if a player has won.
	 */	
	
	private void updatePlayerHealth()
	{
		h1.setSize(MAX_HEALTH - (t[0].getTimesHit() * HEALTH_DEDUCTION), h1.getHeight());
		h2.setSize(MAX_HEALTH - (t[1].getTimesHit() * HEALTH_DEDUCTION), h2.getHeight());
		
		if(t[0].getTimesHit() >= MAX_TIMES_HIT)
		{
			isWinnerPlayer1 = false;
			isGameOver = true;
		}
		else if (t[1].getTimesHit() >= MAX_TIMES_HIT)
		{
			isWinnerPlayer1 = true;
			isGameOver = true;
		}
	}

	private int AICount;
	
	private boolean lastHit;
	private boolean wasHit;
	
	private boolean moveAI;
	private boolean moveAIRight;
	private int AIAngle;
	private double AIPower;
	private int PreviousAIAngle;
	private double PreviousAIPower;
	
	private int timesP1MovedRight;
	private int timesP1MovedLeft;
	private int timesAIMovedRight;
	private int timesAIMovedLeft;
	
	private int timesPlayerWasHit;
	private int previousTimesPlayerWasHit;
	private int timesAIWasHit;
	private int previousTimesAIWasHit;
	AI smartComputer;
	
	private boolean AIHasFuel;
	
	//METHOD  haveAIShoot()
    /*
     * Has the computer generate a random turn based on the difficulty level selected
     * by the player
	 */	
	
	private void haveAIShoot()
	{
		hideUI = true;
		cannon2.rotate(-1 * (angle2 - ROTATE180));
		angle2 = 180;
		AICount = 0;
		moveTankCount = 0;
		
		timesPlayerWasHit = t[0].getTimesHit();
		timesAIWasHit = t[1].getTimesHit();
		
		if(timesPlayerWasHit > previousTimesPlayerWasHit)
			lastHit = true;
		else
			lastHit = false;
		
		if(timesAIWasHit > previousTimesAIWasHit)
			wasHit = true;
		else
			wasHit = false;
		
		previousTimesPlayerWasHit = timesPlayerWasHit;
		previousTimesAIWasHit = timesAIWasHit;
		
		if(t[1].getFuel() < FUEL_HEIGHT)
			AIHasFuel = true;
		else
			AIHasFuel = false;
		
		smartComputer = new AI(world, difficulty, lastHit, 
				wasHit, level.getWind(), level.getNextShot(),
				timesP1MovedRight, timesP1MovedLeft,
				timesAIMovedRight, timesAIMovedLeft,
				PreviousAIPower, PreviousAIAngle,  AIHasFuel);
		
		AiInstructions thisTurn = smartComputer.getAiInstructions();
		t[1].setAngle(thisTurn.getAngle());
		AIAngle = thisTurn.getAngle();
		t[1].setPower(thisTurn.getPower());
		AIPower = thisTurn.getPower();
		moveAI = thisTurn.getMoveTank();
		moveAIRight = thisTurn.getMoveTankRight();
		PreviousAIAngle = AIAngle;
		PreviousAIPower = AIPower;
		
		if(moveAI)
		{
			if(moveAIRight)
			{
				level.moveTank(true);
				timesAIMovedRight++;
			}
			else
			{
				level.moveTank(false);
				timesAIMovedLeft++;
			}
		}
		moveTankToRight = moveAIRight;
		
		AITimer.start();
	}
	
	private final static int MAX_YELLOW = 140;
	private final static int MIN_YELLOW = 115;
	
	//METHOD  setPowerTextColor()
    /*
     * Updates the color of the power level
	 */	
	
	public void setPowerTextColor()
	{
		if(level.isPlayer1Turn())
		{
			if(t[0].getPower() > MAX_YELLOW)
			{
				powerLevel.setColor(Color.RED);
			}
			else if (t[0].getPower() < MIN_YELLOW)
			{
				powerLevel.setColor(Color.GREEN);
			}
			else
			{
				powerLevel.setColor(Color.YELLOW);
			}			
		}
		else
		{
			if(t[1].getPower() > MAX_YELLOW)
			{
				powerLevel.setColor(Color.RED);
			}
			else if (t[1].getPower() < MIN_YELLOW)
			{
				powerLevel.setColor(Color.GREEN);
			}
			else
			{
				powerLevel.setColor(Color.YELLOW);
			}
		}
	}
	
	//METHOD  restartLevel()
    /*
     * To be implemented
	 */	
	
	public void restartLevel()
	{
		isGameOver = false;
		isWinnerPlayer1 = true;
		hideUI = false;
		hiding = false;
		hidingShot = true;
		moveScreenRight = false;
		
		power1 = 25;
		power2 = 25;
		angle1 = 0;
		angle2 = 180;
		moveTankToRight = false;
		moveTankCount = 0;
		isCurrentlyMoving = false;
		movingTank = false;
		fireScreenUp = false;
		isGamePaused = false;
		
		AICount = 0;
		AIAngle = 0;
		lastHit = false;
		moveAI = false;
		moveAIRight = true;
		
		timesP1MovedRight = 0;
		timesP1MovedLeft = 0;
		timesAIMovedRight = 0;
		timesAIMovedLeft = 0;
		timesPlayerWasHit = 0;
		previousTimesPlayerWasHit = 0;
		timesAIWasHit = 0;
		previousTimesAIWasHit = 0;
		PreviousAIAngle = 0;
		PreviousAIPower = 0;
		
		firing = false;
		increment = 100;
		count = 100;
		explosionTiming = 100;
		currentTerrainPos = 0;
		
		level = new Level(width*3, height, p1, p2, world);
		t = level.getTanks();
		
		drawLevel();
		centerOnFiringTank(true);
	}
	
	private boolean isShootingProjectileRight;
	private int lastHeightOfImageRotation;
	
	private final static int MIN_DIF_FOR_ROTATION = 1;
	private final static int ROTATION_AMOUNT = 1;
	private final static int ASTECTIC_EXTRA_ROTATION = 5;
	private final static int ROTATE359 = 359;
	private final static int ROTATE355 = 355;
	
	//METHOD rotateProjectileImage(int time)
	/*
	 * Rotates the projectile object based on the direction it is moving
	 */
	
	public void rotateProjectileImage(int time)
	{
		int heightDifference = flightPath.get(time).getY() - lastHeightOfImageRotation;
		
		if(isShootingProjectileRight)
		{
			if((heightDifference < MIN_DIF_FOR_ROTATION && projectileObject.getRotation() < ROTATE359) || 
					((heightDifference > MIN_DIF_FOR_ROTATION) && 
							((projectileObject.getRotation() < (angle1 + ASTECTIC_EXTRA_ROTATION)) || 
									projectileObject.getRotation() > ROTATE355)))
			{
				projectileObject.rotate(ROTATION_AMOUNT);
			}
		}
		else
		{
			if((heightDifference < MIN_DIF_FOR_ROTATION && projectileObject.getRotation() > ROTATE180) ||
					projectileObject.getRotation() > (ROTATE180 - (angle2 + ASTECTIC_EXTRA_ROTATION - ROTATE180 )))
			{
				projectileObject.rotate(-1 * ROTATION_AMOUNT);
			}
		}
		lastHeightOfImageRotation = flightPath.get(time).getY();
	}
	
	// Returns file path of win/lose tank to display in winPane
	public String getFinalTankImage(boolean isSinglePlayer, boolean isPlayer1win) 
	{
			if(!isSinglePlayer && !isPlayer1win)
			{
				return p2Color.getTankColor(false);
			}
			return p1Color.getTankColor(true);
	}
		
	public GImage getTank(boolean isSinglePlayer, boolean isPlayer1win) 
	{
			if(!isSinglePlayer && !isPlayer1win) 
			{
				return tank2;
			}
			return tank1;
	}
		
	//Returns the matching cannon for getFinalTankImage
	public String getFinalTankCannon(GImage tank)
	{
			if (tank == tank1) 
			{
				return p1Color.getCannonColor();
			}
			return p2Color.getCannonColor();
	}
	public WorldType getWT() {
		return world;
	}
}
