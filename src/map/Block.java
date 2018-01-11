package map;
import java.awt.Rectangle;

/**
 * The Block Class - 
 */
public class Block
{
	private BlockType _type;
	private Rectangle _rectangle;
	
	/**
	 * The Constructor Method - Receives values for each attribute and initializes an instance of the class.
	 * @param x
	 * @param y
	 * @param size
	 * @param type
	 */
	public Block(int x, int y, int size, BlockType type)
	{
		this._rectangle = new Rectangle(x, y, size, size);
		this._type = type;
	}
	
	/**
	 * Getters and Setters:
	 */
	public BlockType getType() 
	{
		return _type;
	}

	public void setType(BlockType type)
	{
		_type = type;
	}

	public Rectangle getRectangle()
	{
		return _rectangle;
	}

	public void setRectangle(Rectangle rectangle)
	{
		_rectangle = rectangle;
	}
	
	@Override
	public String toString() 
	{
		return "Type: " + this._type + ", Rec: " + this._rectangle.toString();
	}
}
