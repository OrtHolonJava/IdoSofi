package map;
import java.awt.Rectangle;
import characters.LivingObject;

/**
 * The Block Class - 
 */
public abstract class Block
{
	protected Rectangle _rectangle;
	protected int _tile; 
	
	/**
	 * The Constructor Method - Receives values for each attribute and initializes an instance of the class.
	 * @param x
	 * @param y
	 * @param size
	 * @param type
	 */
	public Block(int x, int y, int size, int tile)
	{
		this._rectangle = new Rectangle(x, y, size, size);
		this._tile = tile; 
	}
	
	/**
	 * Getters and Setters:
	 */
	public Rectangle getRectangle()
	{
		return _rectangle;
	}

	public void setRectangle(Rectangle rectangle)
	{
		_rectangle = rectangle;
	}
	
	public int getTile()
	{
		return this._tile;
	}
	
	/**
	 * Abstract Method - The effect a collision with the block causes. Each variation of block implements it in its own way.
	 * @param obj
	 */
	public abstract void affectLivingObj(LivingObject obj);
	
	/**
	 * The toString Method
	 */
	@Override
	public String toString() 
	{
		return ", Rec: " + this._rectangle.toString() + ", Tile: " + this._tile;
	}
}
