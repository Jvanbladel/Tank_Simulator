 package starter;
 import java.util.ArrayList;

public class Projectile {
	
	projectileType type;
	double angle;
	double power;
	Cordinate position;
	boolean isPlayer1;
	int wind;
	double gravity;
	
	Projectile(projectileType pType, double angle, double power, Cordinate position, boolean isPlayer1, int wind, double gravity)
	{
		this.type=pType;
		this.angle=angle;
		this.power= power - type.getMass();
		this.position=position;
		this.isPlayer1 = isPlayer1;
		this.wind = wind;
		this.gravity = -1 * gravity;
		count = 0;
		windCount = 0;
	}
	
	//METHOD getPosAtTime(double time) 
	/*
	 * Returns a generated coordinate for a specific time interval
	 * 
	 * Do not mess with it will break the game
	 */
	
	private Cordinate getPosAtTime(double time, int windCount) 
	{
		Cordinate c = new Cordinate(0, 0);
		
		if(isPlayer1)
		{
			double deltaX = time * power * Math.cos(Math.toRadians(angle));
			double vy= time * power * Math.sin(Math.toRadians(angle));
			double yGrav = ((gravity/2)*((time)*(time)));
			double deltaY = vy + yGrav;
					
			c.setX((int)(position.getX() + deltaX) + (int)(wind * windCount));
			c.setY((int)(position.getY() - deltaY));
		}
		else
		{
			double deltaX = time * power * Math.cos(Math.toRadians(angle));
			double vy= time * power * Math.sin(Math.toRadians(angle));
			double yGrav = ((gravity/2)*((time)*(time)));
			double deltaY = vy + yGrav;
					
			c.setX((int)(position.getX() - deltaX) - (int)(wind * windCount));
			c.setY((int)(position.getY() - deltaY));
		}

		return c;
	}

	public double getAngle()
	{
		return angle;
	}
	
	public Cordinate getCord() 
	{
		return position;
	}
	
	//METHOD getFlightPath()
	/*
	 * Returns a generated flightpath for the projectile
	 * 
	 * Do not mess with it will break the game
	 */
	
	private final static int MAX_TIME = 60;
	private final static double INCREMENT_OF_X_ON_TIME = .1;
	private int count;
	private int windCount;
	
	public ArrayList<Cordinate> getFlightPath()
	{
		ArrayList<Cordinate> cordinates = new ArrayList<>();
		
		for(double x = 0; x<MAX_TIME; x+=INCREMENT_OF_X_ON_TIME) 
		{ 
			if(count % 3 == 0)
				cordinates.add(getPosAtTime(x, ++windCount));
			else
				cordinates.add(getPosAtTime(x, windCount));
			count++;
		}
		
		return cordinates;
	}
}
