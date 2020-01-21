package starter;

import java.util.ArrayList;
import acm.util.RandomGenerator;
public enum projectileType {
	BASIC, MISSILE, DOUBLE, SLUG, AIR_STRIKE, BOMBING_RUN, NULLSHOT;
	
	public String toString() 
	{
		switch(this) 
		{
			case BASIC: return "basic";
			case MISSILE: return "missile";
			case DOUBLE: return "double";
			case SLUG:  return "slug";
			case AIR_STRIKE:  return "flare";
			case BOMBING_RUN:  return "bomber_run";
			case NULLSHOT:	return "nullshot";
		}	
		return "n/a";
	}
	
	public String getProjectileImage() 
	{
		switch(this) 
		{
			case BASIC: return "basic";
			case MISSILE: return "missile";
			case DOUBLE: return "double";
			case SLUG:  return "slug";
			case AIR_STRIKE:  return "flare";
			case BOMBING_RUN:  return "flare";
			case NULLSHOT:	return "nullshot";
		}	
		return "n/a";
	}
	
	public double getMass() 
	{
		switch(this) 
		{
			case BASIC: return 10; 
			case MISSILE: return 3;
			case DOUBLE: return 10;
			case SLUG:  return 16;
			case AIR_STRIKE:  return 7;
			case BOMBING_RUN:  return 7;
			case NULLSHOT: return 10;
		}
		return 0;
	}
		
	public String descriptionOfAmmo()
	{
		switch(this) 
		{
		case BASIC: return "A shot that follows a normal\ntragectory and explodes\non impact.";
		case MISSILE: return "A shot that shoots faster\nand straighter";
		case DOUBLE: return "This shot shoots two basic shots\ntogether";
		case SLUG:  return "This shot is extra heavy, firing,\na slug round that hits like a truck";
		case AIR_STRIKE:  return "Shoots a projectile that is a \nlittle lighter than normal.\ncreates a signal which is the\nlocation where an air strike hits";
		case BOMBING_RUN:  return "Shoots a projectile that\ncreates a signal for a bombing\nrun to hit near the location.";
		}
		return "n/a";
	}
	
	public String getDraftName()
	{
		switch(this) 
		{
		case BASIC: return "Basic Shot";
		case MISSILE: return "Missle";
		case DOUBLE: return "Double Shot";
		case SLUG:  return "Slug Shot";
		case AIR_STRIKE:  return "Air Strike";
		case BOMBING_RUN:  return "Bombing Run";
		}
		return "n/a";
	}
	public static projectileType randType() {
		RandomGenerator rgen = RandomGenerator.getInstance();
		int randNum=rgen.nextInt(0,6); 
		
		switch(randNum) {
			case 0:
				return projectileType.BASIC;
			case 1:
				return projectileType.MISSILE;
			case 2:
				return projectileType.SLUG;
			case 3:
				return projectileType.DOUBLE;
			case 4:
				return projectileType.AIR_STRIKE;
			case 5:
				return projectileType.BOMBING_RUN;
			default:
				return projectileType.BASIC;
		}
	}
	public static projectileType stringToType(String str) {
		switch(str) {
		case "basic":
		case "BASIC":
			return projectileType.BASIC;
		case "slug":
		case "SLUG":
			return projectileType.SLUG;
		case "missile":
		case "MISSILE":
			return projectileType.MISSILE;
		case "double":
		case "DOUBLE":
			return projectileType.DOUBLE;
		case "air_strike":
		case "AIR_STRIKE":
			return projectileType.AIR_STRIKE;
		case "bombing_run":
		case "BOMBING_RUN":
			return projectileType.BOMBING_RUN;
		case "null":
		case "NULL":
		default:
			return projectileType.NULLSHOT;
		}
	}
}
