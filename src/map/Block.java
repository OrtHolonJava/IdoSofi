package map;
import java.awt.Graphics;
import java.awt.Rectangle;
import java.util.ArrayList;
import java.util.List;

/**
 * The Block Class - 
 */
public class Block
{
	private BlockType _type;
	private Rectangle _rectangle;
	private List<Integer> _tiles; 
	
	/**
	 * The Constructor Method - Receives values for each attribute and initializes an instance of the class.
	 * @param x
	 * @param y
	 * @param size
	 * @param type
	 */
	public Block(int x, int y, int size, int tile, BlockType type)
	{
		this._rectangle = new Rectangle(x, y, size, size);
		this._type = type;
		this._tiles = new ArrayList<Integer>();
		this._tiles.add(tile);
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
	
	/**
	 * The size of the tiles list is another indication to the block's full type.
	 * If a Ladder block is double 
	 */
	public int getLayers()
	{
		return this._tiles.size();
	}

	public void setRectangle(Rectangle rectangle)
	{
		_rectangle = rectangle;
	}
	
	public List<Integer> getTileList()
	{
		return this._tiles;
	}
	
	public void addTile(int tile)
	{
		this._tiles.add(tile);
	}
	
	/**
	 * The toString Method
	 */
	@Override
	public String toString() 
	{
		return "Type: " + this._type + ", Rec: " + this._rectangle.toString();
	}
}
