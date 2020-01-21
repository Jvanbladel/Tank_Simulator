package starter;
import java.awt.Color;
// This will be used in the Tank class as an enum that the player can change in an optional Tank Color Selection Screen.
public enum WorldType { 
	MEADOWS, MOON, MARS, DESERT, MOUNTAINOUS;
	
	public final static String FP= "../media/BackGroundImages/";
	public final static String BP ="Button/map_";
	public final static String GB = "Button/gravity_";
	
	public Color getSkyColor()
	{
		switch(this) 
		{
		case MEADOWS: return new Color(51, 173, 255);
		case MOON: return new Color(44, 42, 113);
		case MARS: return new Color(251, 173, 128);
		case DESERT: return new Color(244, 222, 20);
		case MOUNTAINOUS: return new Color(128, 231, 251);
		}	
		return new Color(51, 173, 255);
	}
	
	public Color getGroundColor()
	{
		switch(this) 
		{
		case MEADOWS: return new Color(191, 128, 64);
		case MOON: return new Color(203, 199, 190);
		case MARS: return new Color(216, 76, 33);
		case DESERT: return new Color(243, 182, 59);
		case MOUNTAINOUS: return new Color(154, 108, 40);
		}	
		return new Color(51, 173, 255);
	}
	
	public Color getTopGroundColor()
	{
		switch(this) 
		{
		case MEADOWS: return new Color(0, 204, 0);
		case MOON: return new Color(195, 194, 209);
		case MARS: return new Color(216, 76, 33);
		case DESERT: return new Color(244, 162, 20);
		case MOUNTAINOUS: return new Color(199, 197, 193);
		}	
		return new Color(51, 173, 255);
	}
	
	public boolean isClouds()
	{
		switch(this) 
		{
		case MEADOWS: return true;
		case MOON: return false;
		case MARS: return false;
		case DESERT: return false;
		case MOUNTAINOUS: return true;
		}	
		return false;
	}
	
	public int getMountatiousLevel()
	{
		switch(this) 
		{
		case MEADOWS: return 3;
		case MOON: return 2;
		case MARS: return 3;
		case DESERT: return 1;
		case MOUNTAINOUS: return 5;
		}	
		return 3;
	}
	
	public int getMaxWind()
	{
		switch(this) 
		{
		case MEADOWS: return 5;
		case MOON: return 0;
		case MARS: return 0;
		case DESERT: return 2;
		case MOUNTAINOUS: return 8;
		}	
		return 3;
	}
	
	public String getFilePath()
	{
		switch(this) 
		{
		case MEADOWS: return FP + "meadowsBackGroundImage.png";
		case MOON: return FP + "moonBackgroundImage.jpg";
		case MARS: return FP + "marsBackGroundImage.png";
		case DESERT: return FP + "desertBackgroundImage.jpg";
		case MOUNTAINOUS: return FP+ "mountainBackgroundImage.jpg";
		}	
		return "N/A";
	}
	
	public Color getTextColor()
	{
		switch(this) 
		{
		case MEADOWS: return new Color(0, 0, 0);
		case MOON: return new Color(255, 255, 255);
		case MARS: return new Color(255, 255, 255);
		case DESERT: return new Color(0, 0, 0);
		case MOUNTAINOUS: return new Color(255, 255, 255);
		}	
		return new Color(51, 173, 255);
	}
	
	public double getGravity()
	{
		switch(this) 
		{
		case MEADOWS: return 9.81;
		case MOON: return 1.62;
		case MARS: return 3.711;
		case DESERT: return 9.81;
		case MOUNTAINOUS: return 9.81;
		}	
		return 9.81;
	}
	
	public String getGravityFP() {
		switch(this)
		{
		case MEADOWS: return GB + "9.81.png";
		case MOON: return GB + "1.62.png";
		case MARS: return  GB + "3.711.png";
		case DESERT: return  GB + "9.81.png";
		case MOUNTAINOUS: return GB + "9.81.png";
		}
		return "N/A";
	}
	
	public String getButtonpath() {
		switch(this)
		{
		case MEADOWS: return BP + "meadow.png";
		case MOON: return BP + "moon.png";
		case MARS: return BP + "mars.png";
		case DESERT: return BP + "desert.png";
		case MOUNTAINOUS: return BP + "mountain.png";
		}
		return "N/A";
	}
	
	public String getPlane() {
		switch(this)
		{
		case MEADOWS: return "plane";
		case MOON: return "spaceship";
		case MARS: return "spaceship";
		case DESERT: return "plane";
		case MOUNTAINOUS: return "plane";
		}
		return "plane";
	}
	
}