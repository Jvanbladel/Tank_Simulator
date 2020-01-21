package starter;

import java.util.ArrayList;

import acm.graphics.GImage;
import acm.graphics.GObject;
import acm.graphics.GRect;
import acm.util.RandomGenerator;

public class Decorations {
	
	private ArrayList<GImage> decorationList;
	private WorldType world;
	private RandomGenerator rgen;
	private ArrayList<GRect> terrainList;
	private MainApplication program;

	public Decorations(WorldType world, ArrayList<GRect> terrainList, MainApplication program)
	{
		decorationList = new ArrayList<GImage>();
		this.world = world;
		this.rgen = RandomGenerator.getInstance();
		this.terrainList = terrainList;
		this.program = program;
		draw();
	}
	
	public void add()
	{
		for(int i = 0; i < decorationList.size(); i++)
		{
			program.add(decorationList.get(i));
		}
	}
	
	public void move(int x) {
		for(int i = 0; i < decorationList.size(); i++)
		{
			decorationList.get(i).setLocation(decorationList.get(i).getX() - x, decorationList.get(i).getY());
		}
	}
	
	public boolean isInList(GObject obj)
	{
		for(int i = 0; i < decorationList.size(); i++)
		{
			if(obj == decorationList.get(i))
			{
				return true;
			}
		}
		return false;
	}
	
	private void draw()
	{
		decorationList = new ArrayList<GImage>();
		if(world == WorldType.MEADOWS)
		{
			drawMeadows();
		}
		else if(world == WorldType.DESERT)
		{
			drawDesert();
		}
		else if(world == WorldType.MOUNTAINOUS)
		{
			drawMountainous();
		}
		else if(world == WorldType.MOON)
		{
			drawMoon();
		}
		else if(world == WorldType.MARS)
		{
			drawMars();
		}
		if(world.isClouds())
			drawClouds();
	}
	
	private final static int NUMBER_OF_CLOUD_INSTANCES = 6;
	private final static int CLOUD_MIN_GEN = 2;
	private final static int CLOUD_MAX_GEN1 = 5;
	private final static int CLOUD_MAX_GEN2 = 4;
	private final static int CLOUD_SIZE = 50;
	private final static int CLOUD_DISPLACEMENT = 40;
	
	private void drawClouds()
	{		
		for(int i = 0; i < NUMBER_OF_CLOUD_INSTANCES; i++)
		{
			GImage newCloud = new GImage("../media/Decorations/cloud2.png");
			GImage newCloud2 = new GImage("../media/Decorations/cloud2.png");
			
			int c1 = rgen.nextInt(CLOUD_MIN_GEN,CLOUD_MAX_GEN1);
			int c2 = rgen.nextInt(CLOUD_MIN_GEN,CLOUD_MAX_GEN2);
			
			newCloud.setSize(CLOUD_SIZE * c1, CLOUD_SIZE);
			newCloud2.setSize(CLOUD_SIZE * c2, CLOUD_SIZE);
			
			if(i%2 == 0)
			{
				newCloud.setLocation((3*CLOUD_SIZE) + i * MainApplication.WINDOW_WIDTH/2 , CLOUD_DISPLACEMENT);
				newCloud2.setLocation(CLOUD_SIZE + i * MainApplication.WINDOW_WIDTH, (3* CLOUD_SIZE));
			}
			else
			{
				newCloud.setLocation(CLOUD_SIZE + i *MainApplication.WINDOW_WIDTH , (3* CLOUD_SIZE));
				newCloud2.setLocation((CLOUD_SIZE*3)+ i * MainApplication.WINDOW_WIDTH/2, CLOUD_DISPLACEMENT);
			}
			
			decorationList.add(newCloud);
			decorationList.add(newCloud2);
		}
	}
	
	
	private final static String DECORATION_EXT = "../media/Decorations/";
	private final static String PNG_EXT = ".png";	
	private final static int SUN_SIZE = 125;
	private final static int SUN_X = 50;
	private final static int SUN_Y = 100;
	private final static int UP_RECT_SIZE_X = 100;
	private final static int UP_RECT_SIZE_Y = 130;
	private final static int MIDDLE_POS = 2401;
	private final static int BALLOON_X = 2200;
	private final static int BALLOON_Y = 110;
	
	private void drawMeadows()
	{
		GImage sun = new GImage(DECORATION_EXT + "mountain_sun" + PNG_EXT);
		sun.setSize(SUN_SIZE, SUN_SIZE);
		sun.setLocation(SUN_X, SUN_Y);
		decorationList.add(sun);
		
		GImage tree = new GImage(DECORATION_EXT + "tree" + PNG_EXT);
		tree.setSize(UP_RECT_SIZE_X, UP_RECT_SIZE_Y);
		tree.setLocation(terrainList.get(MIDDLE_POS).getX(), terrainList.get(MIDDLE_POS).getY() - UP_RECT_SIZE_Y);
		decorationList.add(tree);
		
		GImage balloon = new GImage(DECORATION_EXT + "hotair_ballon" + PNG_EXT);
		balloon.setSize(UP_RECT_SIZE_X, UP_RECT_SIZE_Y);
		balloon.setLocation(BALLOON_X, BALLOON_Y);
		decorationList.add(balloon);
	}
	
	private final static int DESERT_SUN_MULTIPLIER_X = 6;
	private final static int DESERT_SUN_MULTIPLIER_Y = 2;
	private final static int DESERT_OBJ_SIZE_X = 50;
	private final static int DESERT_OBJ_SIZE_Y = 80;
	private final static int D_OBJ_1_POS = 2001;
	private final static int D_OBJ_2_POS = 2801;
	private final static int D_OBJ_3_POS = 2501;
	private final static int HORIZONTAL_DECORATION_X = 100;
	private final static int HORIZONTAL_DECORATION_Y = 60;
	private final static int D_CLOUD_SIZE_X = 400;
	private final static int D_CLOUD_SIZE_Y = 75;
	private final static int D_CLOUD_POS_X = 1950;
	private final static int D_CLOUD_POS_Y = 120;
	
	private void drawDesert()
	{
		GImage sun = new GImage(DECORATION_EXT + "sun" + PNG_EXT);
		sun.setSize(DESERT_SUN_MULTIPLIER_X * SUN_X, DESERT_SUN_MULTIPLIER_Y* SUN_Y);
		sun.setLocation(SUN_X, SUN_Y);
		decorationList.add(sun);
		
		GImage cactus1 = new GImage(DECORATION_EXT + "cactus1" + PNG_EXT);
		cactus1.setSize(DESERT_OBJ_SIZE_X, DESERT_OBJ_SIZE_Y);
		cactus1.setLocation(terrainList.get(D_OBJ_1_POS).getX(), terrainList.get(D_OBJ_1_POS).getY()- DESERT_OBJ_SIZE_Y);
		decorationList.add(cactus1);
		
		GImage cactus2 = new GImage(DECORATION_EXT + "cactus2"+ PNG_EXT);
		cactus2.setSize(DESERT_OBJ_SIZE_X, DESERT_OBJ_SIZE_Y);
		cactus2.setLocation(terrainList.get(D_OBJ_2_POS).getX(), terrainList.get(D_OBJ_2_POS).getY()- DESERT_OBJ_SIZE_Y);
		decorationList.add(cactus2);
		
		GImage skull = new GImage(DECORATION_EXT + "skull"+ PNG_EXT);
		skull.setSize(HORIZONTAL_DECORATION_X, HORIZONTAL_DECORATION_Y);
		skull.setLocation(terrainList.get(D_OBJ_3_POS).getX(), terrainList.get(D_OBJ_3_POS).getY()- HORIZONTAL_DECORATION_Y);
		decorationList.add(skull);
		
		GImage cloud = new GImage(DECORATION_EXT + "cloudSean"+ PNG_EXT);
		cloud.setSize(D_CLOUD_SIZE_X, D_CLOUD_SIZE_Y);
		cloud.setLocation(D_CLOUD_POS_X, D_CLOUD_POS_Y);
		decorationList.add(cloud);
	}
	
	private final static int MOUNTAIN_OBJ_SIZE_X = 60;
	private final static int MOUNTAIN_OBJ_SIZE_Y = 80;
	private final static int M_OBJ_1_POS = 2001;
	private final static int M_OBJ_2_POS = 2901;
	private final static int M_BIRDS_SIZE_X = 75;
	private final static int M_BIRDS_SIZE_Y = 50;
	private final static int M_BIRDS_POS_X = 2200;
	private final static int M_BIRDS_POS_Y = 110;
	
	private void drawMountainous()
	{
		GImage sun = new GImage(DECORATION_EXT + "mountain_sun" + PNG_EXT);
		sun.setSize(SUN_SIZE, SUN_SIZE);
		sun.setLocation(SUN_X, SUN_Y);
		decorationList.add(sun);
		
		GImage yeti = new GImage(DECORATION_EXT + "yeti" + PNG_EXT);
		yeti.setSize(MOUNTAIN_OBJ_SIZE_X, MOUNTAIN_OBJ_SIZE_Y);
		yeti.setLocation(terrainList.get(M_OBJ_1_POS).getX(), terrainList.get(M_OBJ_1_POS).getY()-MOUNTAIN_OBJ_SIZE_Y);
		decorationList.add(yeti);
		
		GImage climber = new GImage(DECORATION_EXT + "climber" + PNG_EXT);
		climber.setSize(MOUNTAIN_OBJ_SIZE_X, MOUNTAIN_OBJ_SIZE_Y);
		climber.setLocation(terrainList.get(M_OBJ_2_POS).getX(), terrainList.get(M_OBJ_2_POS).getY()-MOUNTAIN_OBJ_SIZE_Y);
		decorationList.add(climber);
		
		GImage birds = new GImage(DECORATION_EXT + "flock_birds" + PNG_EXT);
		birds.setSize(M_BIRDS_SIZE_X, M_BIRDS_SIZE_Y);
		birds.setLocation(M_BIRDS_POS_X, M_BIRDS_POS_Y);
		decorationList.add(birds);
	}
	
	private final static int M_EARTH_SIZE = 225;
	private final static int M_EARTH_POS_X = 625;
	private final static int M_EARTH_POS_Y = 75;
	private final static int M_LANDER_SIZE = 100;
	private final static int M_LANDER_POS_X = 2001;
	private final static int M_LANDER_POS_Y_CHANGE = 90;
	private final static int M_FLAG_SIZE = 70;
	private final static int M_FLAG_POS_X = 2211;
	private final static int M_SATELLITE_SIZE_X = 145;
	private final static int M_SATELLITE_SIZE_Y = 95;
	private final static int SATELLITE_POS_X = 2050;
	private final static int SATELLITE_POS_Y = 50;
	
	private void drawMoon()
	{
		drawStars();
		
		GImage earth = new GImage(DECORATION_EXT + "earth" + PNG_EXT);
		earth.setSize(M_EARTH_SIZE, M_EARTH_SIZE);
		earth.setLocation(M_EARTH_POS_X, M_EARTH_POS_Y);
		decorationList.add(earth);
		
		GImage lander = new GImage(DECORATION_EXT + "moon_lander" + PNG_EXT);
		lander.setSize(M_LANDER_SIZE, M_LANDER_SIZE);
		lander.setLocation(terrainList.get( M_LANDER_POS_X).getX(), terrainList.get( M_LANDER_POS_X).getY()- M_LANDER_POS_Y_CHANGE);
		decorationList.add(lander);
		
		GImage flag = new GImage(DECORATION_EXT + "flag" + PNG_EXT);
		flag.setSize(M_FLAG_SIZE, M_FLAG_SIZE);
		flag.setLocation(terrainList.get(M_FLAG_POS_X).getX(), terrainList.get(M_FLAG_POS_X).getY()-M_LANDER_POS_Y_CHANGE);
		decorationList.add(flag);
		
		GImage satellite = new GImage(DECORATION_EXT + "satellite" + PNG_EXT);
		satellite.setSize(M_SATELLITE_SIZE_X, M_SATELLITE_SIZE_Y);
		satellite.setLocation(SATELLITE_POS_X, SATELLITE_POS_Y);
		decorationList.add(satellite);
	}
	
	private final static int MARS_PHOBOS_SIZE = 85;
	private final static int MARS_PHOBOS_POS_X = 1450;
	private final static int MARS_PHOBOS_POS_Y = 105;
	private final static int MARS_SUN_SIZE = 150;
	private final static int MARS_SUN_POS_X = 525;
	private final static int MARS_SUN_POS_Y = 55;
	private final static int MARS_ROVER_POS = 2501;
	private final static int MARS_SATELLITE_SIZE_X = 125;
	private final static int MARS_SATELLITE_SIZE_Y = 80;
	
	private void drawMars()
	{
		GImage phobos = new GImage(DECORATION_EXT + "phobos" + PNG_EXT);	
		phobos.setSize(MARS_PHOBOS_SIZE, MARS_PHOBOS_SIZE);
		phobos.setLocation(MARS_PHOBOS_POS_X, MARS_PHOBOS_POS_Y);
		decorationList.add(phobos);
		
		GImage sun = new GImage(DECORATION_EXT + "mars_sun" + PNG_EXT);
		sun.setSize(MARS_SUN_SIZE, MARS_SUN_SIZE);
		sun.setLocation(MARS_SUN_POS_X, MARS_SUN_POS_Y);
		decorationList.add(sun);
		
		GImage satellite = new GImage(DECORATION_EXT + "satellite" + PNG_EXT);
		satellite.setSize(MARS_SATELLITE_SIZE_X, MARS_SATELLITE_SIZE_Y);
		satellite.setLocation(SATELLITE_POS_X, SATELLITE_POS_Y);
		decorationList.add(satellite);
		
		GImage rover = new GImage(DECORATION_EXT + "mars_rover" + PNG_EXT);
		rover.setSize(HORIZONTAL_DECORATION_X, HORIZONTAL_DECORATION_Y);
		rover.setLocation(terrainList.get(MARS_ROVER_POS).getX(), terrainList.get(MARS_ROVER_POS).getY()-HORIZONTAL_DECORATION_Y);
		decorationList.add(rover);

	}
	
	private final static String STAR_PATH = "../media/Decorations/star";
	private final static String STAR_EXTENTION = ".png";
	private final static int STAR_COLS = 45;
	private final static int STAR_ROWS = 6;
	private final static int STAR_HEIGHT_SPACING = 36;
	private final static int SY = 10;
	private final static int SX_MIN = 7;
	private final static int SX_MAX = 70;
	private final static int STAR_MIN_SIZE = 2;
	private final static int STAR_MAX_SIZE = 6;
	
	
	private void drawStars()
	{	
		for(int i = 0; i < STAR_COLS; i++)
		{
			for(int star = 0; star < STAR_ROWS; star++)
			{
				GImage newStar = new GImage(STAR_PATH + (star % 2 == 0 ? "" : "2") +STAR_EXTENTION);
				int starSize = rgen.nextInt(STAR_MIN_SIZE, STAR_MAX_SIZE);
				newStar.setSize(starSize,starSize);
				int sx = rgen.nextInt(SX_MIN, SX_MAX);
				int sy = rgen.nextInt(-1 * SY, SY);
				newStar.setLocation((i*sx) + (star *(STAR_ROWS-star)), sy + (STAR_HEIGHT_SPACING*(star+1)));
				decorationList.add(newStar);
			}
		}
	}
}
