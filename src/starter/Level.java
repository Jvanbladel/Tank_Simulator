package starter;

import java.util.ArrayList;

public class Level {
	
	/*
	 * Purpose of this class is to hold 
	 * the current players turn and the board
	 */

	private Board board;

	private boolean player1Turn = true;
	
	public Level(int r, int c, ArrayList<projectileType> tank1Ammo, ArrayList<projectileType> tank2Ammo, WorldType world)
	{
		board = new Board(r, c, tank1Ammo, tank2Ammo, world);
	}
	
	public ArrayList<Cordinate> getTerrain()
	{
		return board.getTerrain();
	}
	
	public Tank[] getTanks()
	{
		return board.getTanks();
	}
	
	public ArrayList<Cordinate> shootTank()
	{
		Tank[] t = board.getTanks();
		if(player1Turn)
		{
			player1Turn = !player1Turn;
			return board.shootTank(t[0]);
		}
		player1Turn = !player1Turn;
		return board.shootTank(t[1]);
	}
	
	public boolean isPlayer1Turn()
	{
		return player1Turn;
	}
	
	public boolean HitObject(Cordinate cord)
	{
		return board.isHittingObject(cord);
	}
	
	public boolean moveTank(boolean isMovingRight)
	{
		return board.moveTank(player1Turn, isMovingRight);
	}
	
	public projectileType getNextShot()
	{
		return board.getNextShot(player1Turn);
	}
	
	public void changeWind()
	{
		board.changeWind();
	}
	
	public int getWind()
	{
		return board.getWind();
	}
}
