package map;
import characters.LivingObject;

public class TerrainBlock extends Block
{
	private static final int _feetPositioning = 3;
	
	private boolean _isFloor;
	
	public TerrainBlock(int tile, boolean floor)
	{
		super(tile);
		this._isFloor = floor;
	}

	@Override
	public void affectLivingObj(LivingObject obj, int x, int y)
	{
		if (this._isFloor)
		{
			obj.setCollidedState(true);
			obj.setPosition(obj.getObjBox().x, y - (int)(obj.getObjBox().getHeight()) + this._feetPositioning);
		}
	}
}
