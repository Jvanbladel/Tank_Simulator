package starter;

public class AiInstructions {

	double power; 
	int angle;
	boolean moveTank;
	boolean moveTankRight;
	
	public AiInstructions()
	{
		this.power = 25; 
		this.angle = 45;
		this.moveTank = false;
		this.moveTankRight = true;
	}
	
	public AiInstructions(double power, int angle, boolean moveTank, 
			boolean moveTankRight)
	{
		this.power = power; 
		this.angle = angle;
		this.moveTank = moveTank;
		this.moveTankRight = moveTankRight;
	}
	
	public double getPower()
	{
		return power;
	}
	
	public void setPower(double power)
	{
		this.power = power; 
	}
	
	public int getAngle()
	{
		return angle;
	}
	
	public void setAngle(int angle)
	{
		this.angle = angle; 
	}
	
	public boolean getMoveTank()
	{
		return moveTank;
	}
	
	public void setMoveTank(boolean moveTank)
	{
		this.moveTank = moveTank;
	}
	
	public boolean getMoveTankRight()
	{
		return moveTankRight;
	}
	
	public void setMoveTankRight(boolean moveTankRight)
	{
		this.moveTankRight = moveTankRight;
	}
}
