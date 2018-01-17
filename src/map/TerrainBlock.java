package map;
import characters.GameCharacter;
import characters.LivingObject;

public class TerrainBlock extends Block
{
	private boolean _isFloor;
	private final int _feetPositioning = 3;
	
	public TerrainBlock(int x, int y, int size, int tile, boolean floor)
	{
		super(x, y, size, tile);
		this._isFloor = floor;
	}

	@Override
	public void affectLivingObj(LivingObject obj)
	{
		if (this._isFloor)
		{
			obj.setGravity(false);
			obj.setPosition(obj.getObjBox().x, this._rectangle.y - (int)(obj.getObjBox().getHeight()) + this._feetPositioning);
		}
	}
}
