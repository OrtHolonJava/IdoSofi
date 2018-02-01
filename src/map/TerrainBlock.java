package map;
import characters.LivingObject;

public class TerrainBlock extends Block
{
	private static final int _feetPositioning = 3;
	
	private boolean _isFloor;
	
	public TerrainBlock(int tile, int x, int y, boolean floor)
	{
		super(tile, x, y);
		this._isFloor = floor;
	}

	@Override
	public void affectLivingObj(LivingObject obj)
	{
		if (this._isFloor)
		{
			obj.setCollidedState(true);
			obj.setPosition(obj.getObjBox().x, this._y - (int)(obj.getObjBox().getHeight()) + _feetPositioning);
		}
	}
}
