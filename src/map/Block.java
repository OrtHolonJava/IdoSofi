package map;

/**
 * The Block Class -
 */
public abstract class Block
{
	protected int _tile, _x, _y;
	
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
	 * The toString Method
	 */
	@Override
	public String toString()
	{
		return "Tile: " + this._tile;
	}
}
