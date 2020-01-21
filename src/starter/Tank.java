package starter;

import java.util.ArrayList;


public class Tank {
	
	
	private static final int TANK_WIDTH = 100;
	private static final int TANK_HEIGHT = 50;
	private static final int BARREL_LENGTH = 75;
	
	private static final int RIGHTX = 30;
	private static final int STARTY = 43;

	private boolean isRight; // true = facing right, false = facing left
	
	private Cordinate tankCurrentPos; // Position of the tank and pivot point
	private Cordinate centerOfTank; // start position of the shot
	
	private int fuel; // start at 0 and increases by 29 for every move
	private double angle; //angle in degrees
	private double power;
	
	private int timesHit;//health
	
	public TANK_STYLE colorScheme;
	
	private ArrayList<projectileType> ammunition;
	
	private int currentShot;
	
	public Tank (boolean faceRight, int startingX, int startingY, ArrayList<projectileType> ammunition) {
		this.colorScheme=TANK_STYLE.GREEN; //default color of tank
		this.isRight = faceRight;
		this.ammunition = ammunition;
		this.tankCurrentPos = new Cordinate(startingX, startingY);
		
		if(isRight)
		{
			this.centerOfTank = new Cordinate(startingX + RIGHTX, startingY - STARTY);
		}
		else
		{
			this.centerOfTank = new Cordinate(startingX, startingY - STARTY);
		}
		
		this.fuel = 0;
		this.angle = 0; //0-90
		this.power = 100; // max 150 min 40
		this.currentShot = 0;
		this.timesHit = 0;
	}
	public Tank (boolean faceRight, int startingX, int startingY, ArrayList<projectileType> ammunition, TANK_STYLE colorScheme) { //allows tank color to be changed
		this.colorScheme=colorScheme;
		this.isRight = faceRight;
		this.ammunition = ammunition;
		this.tankCurrentPos = new Cordinate(startingX, startingY);
		
		if(isRight)
		{
			this.centerOfTank = new Cordinate(startingX + RIGHTX, startingY - STARTY);
		}
		else
		{
			this.centerOfTank = new Cordinate(startingX, startingY - STARTY);
		}
		
		this.fuel = 0;
		this.angle = 0; //0-90
		this.power = 100; // max 150 min 40
		this.currentShot = 0;
		this.timesHit = 0;
	}
	
	public int getTimesHit()
	{
		return timesHit;
	}

	public int getFuel() {
		return fuel;
	}
	
	public void setFuel(int fuel) {
		this.fuel = fuel;
	}
	
	public double getAngle()
	{
		return angle;
	}
	
	public void setAngle(double degrees)
	{
		this.angle = degrees;
	}
	
	public double getPower()
	{
		return power;
	}
	
	public void setPower(double power)
	{
		this.power = power;
	}
	
	public Cordinate getTankCurrentPos()
	{
		return tankCurrentPos;
	}
	
	//METHOD setTankCurrentPos(int x, int y)
	/*
	 * Moves the tank a certain position x and certain pos y
	 */
	
	public void setTankCurrentPos(int x, int y)
	{
		tankCurrentPos.setX(x);
		tankCurrentPos.setY(y);
		if(isRight)
		{
			this.centerOfTank = new Cordinate(x + RIGHTX, y - STARTY);
		}
		else
		{
			this.centerOfTank = new Cordinate(x, y - STARTY);
		}
	}
	
	public boolean isFacingRight() {
		return isRight;
	}
	
	public projectileType getCurrentShot()
	{
		projectileType output = ammunition.get(currentShot);
		
		currentShot++;
		
		if(currentShot == ammunition.size())
			currentShot = 0;
		
		return output;
	}
	
	public projectileType getNextShot()
	{
		return ammunition.get(currentShot);
	}
	
	//METHOD calcFireCord()
	/*
	 * Uses a right angle with the hypotinus being the lenght of the tank barrel
	 * an angle being the players selected angle  to calculate the firing position of each tank
	 * based on their facing direction
	 * 
	 * Do not change this or it will fire incorrectly
	 */
	
	public Cordinate calcFireCord()
	{		
		if(isRight)
		{
			return new Cordinate(centerOfTank.getX() + (int)(BARREL_LENGTH * Math.cos(Math.toRadians(angle))) ,
					centerOfTank.getY() - (int)(BARREL_LENGTH * Math.sin(Math.toRadians(angle))));
		}
		
		return new Cordinate(centerOfTank.getX() - (int)(BARREL_LENGTH * Math.cos(Math.toRadians(angle))),
				centerOfTank.getY() - (int)(BARREL_LENGTH * Math.sin(Math.toRadians(angle))));
	}
	
	//METHOD hitTank(Cordinate bulletPos)
	/*
	 * Determines if a cordinate hits the tank
	 */
	
	public boolean hitTank(Cordinate bulletPos)
	{
		if(bulletPos.getX() > tankCurrentPos.getX() - (TANK_WIDTH/2) && bulletPos.getX() < tankCurrentPos.getX() + (TANK_WIDTH/2))
		{
			if(bulletPos.getY() > tankCurrentPos.getY() - (TANK_HEIGHT) && bulletPos.getY() < tankCurrentPos.getY())
			{
				timesHit++;
				return true;
			}
		}
		
		return false;
	}
	
	//METHOD shoot()
	/*
	 * Creates a shot object with various variables to create a flightpath
	 */
	
	public ArrayList<Cordinate> shoot(int wind, double gravity)
	{
		Projectile shot = new Projectile(this.ammunition.get(this.currentShot), this.angle, this.power, calcFireCord(), 
				this.isRight, wind, gravity);
		return shot.getFlightPath();
	}

	//METHOD moveTank(boolean isMovingRight, int newY)
	/*
	 * moves the tank a direction with a new height
	 */
	
	private static final int FUEL_LIMIT = 145;
	private static final int FUEL_SUBTRACTION = 29;
	private static final int MOVE_AMOUNT = 50;
	
	public boolean moveTank(boolean isMovingRight, int newY)
	{
		if(fuel >= FUEL_LIMIT)
		{
			return false;
		}
		else
		{
			if(isMovingRight)
			{
				tankCurrentPos.setX(tankCurrentPos.getX() + MOVE_AMOUNT);
				tankCurrentPos.setY(newY);
				
				if(isRight)
				{
					centerOfTank.setX(tankCurrentPos.getX()+ RIGHTX);
				}
				else
				{
					centerOfTank.setX(tankCurrentPos.getX());
				}
				
				centerOfTank.setY(tankCurrentPos.getY() - STARTY);
			}
			else
			{
				tankCurrentPos.setX(tankCurrentPos.getX() - MOVE_AMOUNT);
				tankCurrentPos.setY(newY);
				
				if(isRight)
				{
					centerOfTank.setX(tankCurrentPos.getX() + RIGHTX);
				}
				else
				{
					centerOfTank.setX(tankCurrentPos.getX());
				}
				
				centerOfTank.setY(tankCurrentPos.getY() - STARTY);
			}
			fuel += FUEL_SUBTRACTION;
		}
		return true;
	}
}
