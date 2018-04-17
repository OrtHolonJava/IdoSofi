package map;

/**
 * The BlockMapKey Class -
 * Instances of it are used as the key of each block in the block hash map.
 */
public class BlockMapKey
{
	private int _id;
	private BlockType _type;
	
	public BlockMapKey(int id, BlockType type)
	{
		this._id = id;
		this._type = type;
	}
	
	@Override
	public boolean equals(Object obj)
	{
		if (!(obj instanceof BlockMapKey))
		{
			return false;
		}
		return this._id == ((BlockMapKey)obj)._id && this._type == ((BlockMapKey)obj)._type;
	}
	
	@Override
	public int hashCode()
	{
		return this._type.ordinal() << 31 | this._id;
	}

	/**
	 * Getters and Setters -
	 */
	public int getId()
	{
		return _id;
	}

	public BlockType getType()
	{
		return _type;
	}
	
	
}
