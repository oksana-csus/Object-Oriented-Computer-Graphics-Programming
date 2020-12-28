package com.mycompany.a3;

public class GWInfo 
{
	public static final int TICK_RATE = 20; // in ms
	public static final int TurnAmount = (int)TICK_RATE/2;
	public static final int MAX_LEFT = -(TICK_RATE*2);
	public static final int MAX_RIGHT = (int)TICK_RATE*2;
	private static int sHeight = 0;
	
	public static void setGameSize(int x, int y) 
	{
		if (GameSize.x == -1 || GameSize.y == -1) 
		{
			new GameSize(x, y);
		}
	}
	public static void setSouthContainerHeight(int height)
	{
		sHeight = height;
	}
	
	private static class GameSize
	{ 
		private static int x = -1;
		private static int y = -1;
		
		public GameSize(int x, int y) 
		{
			GameSize.x = x;
			GameSize.y = y;
		}
	}
	
	public static int gameSizeX() { return GameSize.x; }
	public static int gameSizeY() { return GameSize.y; }
	
}
