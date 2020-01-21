package starter;

import java.util.ArrayList;

import acm.util.RandomGenerator;

public class Draft {

	
	private boolean isSinglePlayer;
	private int diff;
	
	private boolean isPlayer1Turn;
	
	
	ArrayList<projectileType> gameAmmo;
	ArrayList<projectileType> listCompCanSelctfrom;
	ArrayList<projectileType> p1;
	ArrayList<projectileType> p2;
	
	int count;
	
	private RandomGenerator rgen;
	
	public Draft(boolean isSinglePlayer, int diff)
	{
		this.rgen = RandomGenerator.getInstance();
		this.isPlayer1Turn = true;
		this.isSinglePlayer = isSinglePlayer;
		this.diff = diff;
		
		gameAmmo =  new ArrayList<projectileType>();
		listCompCanSelctfrom =  new ArrayList<projectileType>();
		p1 =  new ArrayList<projectileType>();
		p2 =  new ArrayList<projectileType>();
		
		populateGameAmmo();
		
		count = 0;
		
	}
	
	//Make this more randomized
	
	public void populateGameAmmo()
	{
		projectileType rand;
		for(int i=0; i<10;i++) {
			rand=projectileType.randType();
			gameAmmo.add(rand);
			listCompCanSelctfrom.add(rand);
		}
		
		
	}
	
	public ArrayList<projectileType> getAmmoForGame()
	{
		return gameAmmo;
	}
	
	public boolean isPlayer1Turn()
	{
		return isPlayer1Turn;
	}
	
	public void playerSelect(projectileType projectile)
	{
		if(isPlayer1Turn)
		{
			p1.add(projectile);
		}
		else
		{
			p2.add(projectile);
		}	
		remove(projectile);
		isPlayer1Turn = !isPlayer1Turn;
		count++;
	}
	
	//METHOD
	
	public boolean remove(projectileType projectile)
	{
		for(int i = 0; i < listCompCanSelctfrom.size(); i++)
		{
			if(listCompCanSelctfrom.get(i) == projectile)
			{
				listCompCanSelctfrom.remove(i);
				return true;
			}
		}
		return false;
	}
	
	public projectileType computerSelect()
	{
		int select = rgen.nextInt(0, listCompCanSelctfrom.size()-1);
		return listCompCanSelctfrom.get(select);
	}
	
	public ArrayList<projectileType> getp1()
	{
		return p1;
	}
	
	public ArrayList<projectileType> getp2()
	{
		return p2;
	}
	
	public boolean isDraftOver()
	{
		return count==10;
	}
}
