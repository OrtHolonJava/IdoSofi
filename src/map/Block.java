package map;

import characters.LivingObject;

/**
 * The Block Class -
 */
public abstract class Block
{
	protected int _tile;

	/**
	 * The Constructor Method - Receives values for each attribute and
	 * initializes an instance of the class.
	 * 
	 * @param x
	 * @param y
	 * @param size
	 * @param type
	 */
	public Block(int tile)
	{
		this._tile = tile;
	}

	/**
	 * Getters and Setters:
	 */
	public int getTile()
	{
		return this._tile;
	}

	/**
	 * Abstract Method - The effect a collision with the block causes. Each
	 * variation of block implements it in its own way.
	 * 
	 * @param obj
	 */
	public abstract void affectLivingObj(LivingObject obj, int x, int y);

	/**
	 * The toString Method
	 */
	@Override
	public String toString()
	{
		return "Tile: " + this._tile;
	}
}
