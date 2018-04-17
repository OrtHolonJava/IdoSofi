package map;
import characters.LivingObject;

/**
 * The TerrainBlock Class - 
 */
public class TerrainBlock extends Block
{
	private static final int _feetPositioning = 3;
	
	private boolean _isFloor;
	
	
	public TerrainBlock(int tile, int x, int y, boolean floor)
	{
		super(tile, x, y);
		this._isFloor = floor;
	}

	public void affectLivingObj(LivingObject obj)
	{
		if (this._isFloor)
		{
			obj.setCollidedState(true);
			obj.setPosition(obj.getObjBox().x, this.getY() - (int)(obj.getObjBox().getHeight()) + _feetPositioning);
			obj.setLastFeetBlock(obj.getFeetBlock());
		}
	}
	
	public boolean isFloor()
	{
		return this._isFloor;
	}
}
