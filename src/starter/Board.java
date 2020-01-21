package starter;

import java.util.ArrayList;

import acm.util.RandomGenerator;

public class Board {
	
		/*
		 * Purpose of this class is to hold 
		 * the terrain and the player tanks 
		 */

		private RandomGenerator rgen;
	
		private ArrayList<Cordinate> terrain;
	
		private Tank player1;
		private Tank player2;
		
		private int maxHeight;
		private int maxWidth;
		
		private WorldType world;
		
		private int wind;
	
		public Board(int program_width, int program_height, ArrayList<projectileType> tank1Ammo, ArrayList<projectileType> tank2Ammo,
				WorldType world)
		{
			this.maxHeight = program_height-1;
			this.maxWidth = program_width-1;
			this.rgen = RandomGenerator.getInstance();
			this.wind = 0;
			this.world = world;
			
			generateTerrain(program_width, program_height);
			
			player1 = new Tank(true, program_width/6, getTerrainHeight(program_width/6), tank1Ammo);
			player2 = new Tank(false, program_width - (program_width/6), getTerrainHeight(program_width - (program_width/6)), tank2Ammo);
		
			
			changeWind();
		}
		
		//METHOD generateTerrain(int width, int height)
		/*
		 * Generates random terrain for the size put in by the parameters
		 */
		
		private final static int MINIMUM_HEIGHT = 60;
		
		private void generateTerrain(int width, int height)
		{
			terrain = new ArrayList<Cordinate>();
			
			int maxHeight = (height-1) - (height/2);
			int minHeight = (height-1) - MINIMUM_HEIGHT;
			
			int currentHeight = rgen.nextInt(minHeight, maxHeight);
			
			for(int col = 0; col < width; col++)
			{
				terrain.add(new Cordinate(col, currentHeight));
				currentHeight = calcNewCurrentHeight(maxHeight, minHeight, currentHeight);
			}
			
		}
		
		//METHOD calcNewCurrentHeight(int maxHeight, int minHeight, int currentHeight)
	    /*
		 * Returns an integer that is within a certain range of the parameters
	     */
		
		private int calcNewCurrentHeight(int maxHeight, int minHeight, int currentHeight)
		{
			int addition = rgen.nextInt(-1 * world.getMountatiousLevel(), world.getMountatiousLevel());
			
			if(currentHeight + addition > minHeight)
			{
				return currentHeight - world.getMountatiousLevel();
			}
			else if (currentHeight + addition < maxHeight)
			{
				return currentHeight + world.getMountatiousLevel();
			}
			
			return currentHeight + addition;
		}
	
		public ArrayList<Cordinate> getTerrain()
		{
			return terrain;
		}
		
		//METHOD getTerrainHeight(int col)
	    /*
		 * Returns an integer of the height of the terrain at a particular x
	     */
		
		private int getTerrainHeight(int col)
		{
			for(int c = 0; c < terrain.size(); c++)
			{
				if(terrain.get(c).getX() == col)
					return terrain.get(c).getY();
			}
			
			return maxHeight/2;
		}
		
		public Tank[] getTanks()
		{
			Tank[] output = {player1, player2};
			
			return output;
		}
		
		
		public ArrayList<Cordinate> shootTank(Tank t)
		{
			return t.shoot(wind, world.getGravity());
		}
		
		public boolean isHittingObject(Cordinate cord)
		{	
			return player1.hitTank(cord) || player2.hitTank(cord) || hitTerrain(cord);
		}
		
		public boolean hitTerrain(Cordinate cord)
		{
			if(cord.getX() < -50 ||cord.getX() > maxWidth+100)
				return true;
			if(cord.getY() > maxHeight || cord.getY() < 0 || cord.getX() < 0 ||cord.getX() > maxWidth)
				return false;
			return terrain.get(cord.getX()).getY() <= cord.getY();
		}
		
		//METHOD moveTank(boolean isPlayer1, boolean isMovingRight)
	    /*
		 * Moves a selected tank a selected direction. 
		 * if move was made returns true
		 * if move could not be made returns false
	     */
		
		private final static int TANK_MOVE = 50;
		
		public boolean moveTank(boolean isPlayer1, boolean isMovingRight)
		{
			if(isPlayer1)
			{
				if(isMovingRight)
					return (player1.moveTank(isMovingRight, getTerrainHeight(player1.getTankCurrentPos().getX()+TANK_MOVE)));
				else
					return (player1.moveTank(isMovingRight, getTerrainHeight(player1.getTankCurrentPos().getX()-TANK_MOVE)));
			}
			if(isMovingRight)
				return (player2.moveTank(isMovingRight, getTerrainHeight(player2.getTankCurrentPos().getX()+TANK_MOVE)));
			return (player2.moveTank(isMovingRight, getTerrainHeight(player2.getTankCurrentPos().getX()-TANK_MOVE)));
		}
		
		public projectileType getNextShot(boolean isPlayer1)
		{
			if(isPlayer1)
				return player1.getNextShot();
			return player2.getNextShot();
		}
		
		public int getWind()
		{
			return wind;
		}
		
		public void changeWind()
		{
			wind = rgen.nextInt(-1*world.getMaxWind(), world.getMaxWind());
		}
		
}
