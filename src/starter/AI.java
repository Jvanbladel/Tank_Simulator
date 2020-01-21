package starter;

import acm.util.RandomGenerator;

public class AI {

	WorldType world;
	projectileType shot;
	boolean lastShotHit, tookDamageLastTurn, hasFuel;
	int dif, wind, 
	timesApponentMovedRight, timesApponentMovedLeft, 
	timesMovedRight,timesMovedLeft, distance, previousAngle;
	double mass, previousPower;
	
	private RandomGenerator rgen;
	
	public AI(WorldType world, int dif, boolean lastShotHit, 
			boolean tookDamageLastTurn, int wind, projectileType shot,
			int timesApponentMovedRight, int timesApponentMovedLeft,
			int timesMovedRight, int timesMovedLeft, 
			double previousPower, int previousAngle, boolean hasFuel)
	{
		this.world = world;
		this.dif = dif;
		this.lastShotHit = lastShotHit;
		this.tookDamageLastTurn = tookDamageLastTurn;
		this.wind = wind;
		this.shot = shot;
		this.timesApponentMovedRight = timesApponentMovedRight;
		this.timesApponentMovedLeft = timesApponentMovedLeft;
		this.timesMovedRight = timesMovedRight;
		this.timesMovedLeft = timesMovedLeft;
		this.previousPower = previousPower;
		this.previousAngle = previousAngle;
		this.hasFuel = hasFuel;
		setDistanceAppart();
		getOtherVariables();
		this.rgen = RandomGenerator.getInstance();
	}
	
	private void setDistanceAppart()
	{
		int p1Distance = timesApponentMovedLeft - timesApponentMovedRight;
		int p2Distance = timesMovedRight - timesMovedLeft;
		this.distance = p1Distance + p2Distance;
	}
	
	private void getOtherVariables()
	{
		this.mass = shot.getMass();
		//this.gravity = world.getGravity();
	}
	
	public AiInstructions getAiInstructions()
	{
		AiInstructions instructions = new AiInstructions();
		
		if(this.dif == 1)
		{
			instructions.setAngle(easyAngle());
			instructions.setPower(easyPower());
			instructions.setMoveTank(easyMoveTank());
			instructions.setMoveTankRight(easyMoveTankRight());
		}
		else if(this.dif == 2)
		{
			boolean repeatSameShot = false;
			if(lastShotHit && rgen.nextInt(0,4) > 0)
				repeatSameShot = true;
			instructions.setAngle(mediumAngle(repeatSameShot));
			instructions.setPower(mediumPower(repeatSameShot));
			instructions.setMoveTank(mediumMoveTank(repeatSameShot));
			instructions.setMoveTankRight(mediumMoveTankRight());
		}
		else if(this.dif == 3)
		{
			boolean repeatSameShot = false;
			if(lastShotHit && rgen.nextInt(0,4) > 0)
				repeatSameShot = true;
			instructions.setMoveTank(hardMoveTank());
			instructions.setMoveTankRight(hardMoveTankRight());
			instructions.setAngle(hardAngle());
			instructions.setPower(hardPower(repeatSameShot));
		}
		
		return instructions;
	}
	
	private double easyPower()
	{
		double ePower = 0;
		
		if(world == WorldType.MOON)
		{
			ePower = rgen.nextInt(49,60);
		}
		else if(world == WorldType.MARS)
		{
			ePower = rgen.nextInt(80,94);
		}
		else
		{
			ePower = rgen.nextInt(130, 140);
		}
		
		return ePower;
	}
	
	private double mediumPower(boolean repeatShot)
	{
		double mPower = 0;
		
		if(repeatShot)
		{
			return previousPower;
		}
		else if(world == WorldType.MOON)
		{
			mPower = rgen.nextInt(55, 60);
		}
		else if(world == WorldType.MARS)
		{
			mPower = rgen.nextInt(83, 90);
		}
		else if (world == WorldType.MOUNTAINOUS)
		{
			mPower = rgen.nextInt(130, 140);
		}
		else
		{
			mPower = rgen.nextInt(133, 136);
		}
		
		return mPower;
	}
	
	private double hardPower(boolean repeat)
	{
		double hPower = 0;
		
		if(repeat)
		{
			return previousPower;
		}
		if(world == WorldType.MOON)
		{
			hPower = rgen.nextInt(56, 60);
			//hPower += distance + distanceMoved;
		}
		else if(world == WorldType.MARS)
		{
			hPower = rgen.nextInt(85, 89);
			//hPower += (distance+ distanceMoved)*2;
		}
		else if (world == WorldType.MOUNTAINOUS)
		{
			hPower = rgen.nextInt(131, 140);
			//hPower += (distance+ distanceMoved)*3;
			hPower -= 3*wind;
		}
		else
		{
			hPower = rgen.nextInt(133, 136);
			//hPower += (distance+ distanceMoved)*3;
			hPower += wind;
		}
		
		//hPower += (shot.getMass()-10);
		
		return hPower;
	}
	
	private int easyAngle()
	{
		int eAngle = rgen.nextInt(20,50);
		return eAngle;
	}
	
	private int mediumAngle(boolean repeat)
	{
		if(repeat)
		{
			return previousAngle;
		}
		int mAngle = rgen.nextInt(25, 45);
		return mAngle;
	}
	
	private int hardAngle()
	{
		int hAngle = 35;
		return hAngle;
	}
	
	private boolean easyMoveTank()
	{
		return (rgen.nextInt(0, 5) < 2 && hasFuel);
	}
	private boolean mediumMoveTank(boolean repeat)
	{
		if(repeat)
			return false;
		return (rgen.nextInt(0, 4) < 2 && hasFuel);
	}
	private boolean hardMoveTank()
	{
		return (hasFuel && tookDamageLastTurn);
	}
	private boolean easyMoveTankRight()
	{
		return rgen.nextInt(0, 2) == 0;
	}
	private boolean mediumMoveTankRight()
	{
		return rgen.nextInt(0, 3) <= 1;
	}
	private boolean hardMoveTankRight()
	{
		return rgen.nextInt(0, 10) <= 7;
	}
}
