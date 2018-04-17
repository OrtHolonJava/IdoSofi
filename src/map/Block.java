package map;

import characters.LivingObject;

/**
 * The Block Class -
 */
public abstract class Block
{
	private int _tile, _x, _y;
	
	/**
	 * The Constructor Method - Receives values for each attribute and
	 * initializes an instance of the class.
	 * 
	 * @param x
	 * @param y
	 * @param size
	 * @param type
	 */
	public Block(int tile, int x, int y)
	{
		this._tile = tile;
		this._x = x;
		this._y = y;
	}

	/**
	 * Getters and Setters:
	 */
	public int getTile()
	{
		return this._tile;
	}
	
	/**
	 * Abstract Method - The effect a collision with the block causes. 
	 * Each variation of block implements it in its own way.
	 * 
	 * @param obj
	 */
	public abstract void affectLivingObj(LivingObject obj);
	
	/**
	 * The toString Method -
	 */
	@Override
	public String toString()
	{
		return "Tile: " + this._tile;
	}

	/**
	 * Getters and Setters -
	 */
	public int getX()
	{
		return _x;
	}

	public void setX(int x)
	{
		_x = x;
	}

	public int getY()
	{
		return _y;
	}

	public void setY(int y)
	{
		_y = y;
	}

	public void setTile(int tile)
	{
		_tile = tile;
	}
}
