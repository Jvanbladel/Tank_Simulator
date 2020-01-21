package starter;
//This will be used in the Tank class as an enum that the player can change in an optional Tank Color Selection Screen.
public enum TANK_STYLE { 
	RED, BLUE, PINK, GREEN, YELLOW, GHOST, BLACK;
	
	private String fN = "TanksPNGS/";
	
	//returns the image file path of Color tank desired/ LEFT or Right
	
	public String getTankColor(boolean faceRight) 
	{
		switch(this) 
		{
		case RED: if(faceRight) {return fN+"LeftSide_RedTank.png";}else {return fN+"RightSide_RedTank.png";}
		case BLUE: if(faceRight) {return fN+"LeftSide_BlueTank.png";}else {return fN+"RightSide_BlueTank.png";}
		case PINK: if(faceRight) {return fN+"LeftSide_PinkTank.png";}else {return fN+"RightSide_PinkTank.png";}
		case GREEN: if(faceRight) {return fN+"LeftSide_GreenTank.png";}else {return fN+"RightSide_GreenTank.png";}
		case YELLOW: if(faceRight) {return fN+"LeftSide_YellowTank.png";}else {return fN+"RightSide_YellowTank.png";}
		case GHOST: if(faceRight) {return fN + "LeftSide_GhostTank.png";}else {return fN+"RightSide_GhostTank.png";}
		case BLACK: if(faceRight) {return fN + "LeftSide_BlackTank.png";}else {return fN+"RightSide_BlackTank.png";}
		}	
		return "n/a";
	}
	
	//returns the image file path of Color tank barrel desired
	
	public String getCannonColor()
	{
		switch(this) 
		{
		case RED: return fN + "RedCannon.png";
		case BLUE: return fN + "BlueCannon.png";
		case PINK: return fN + "PinkCannon.png";
		case GREEN: return fN + "GreenCannon.png";
		case YELLOW: return fN + "YellowCannon.png";
		case GHOST: return fN + "GhostCannon.png";
		case BLACK: return fN+"BlackCannon.png";
		}	
		return "n/a";
	}
}