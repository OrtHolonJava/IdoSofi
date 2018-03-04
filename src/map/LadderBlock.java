package map;

import characters.CharacterState;
import characters.GameCharacter;
import characters.PlayerKeyListener;

public class LadderBlock extends ObjectBlock
{
	private final int _initialPositioning = 4;
	private boolean _initializer;

	public LadderBlock(int tile, int x, int y, boolean init)
	{
		super(tile, x, y);
		this._initializer = init;
	}

	@Override
	public void affectLivingObj(GameCharacter gc)
	{
		if (PlayerKeyListener.getPressedKeys()[PlayerKeyListener.KEY_UP]) // The character is willing to climb
		{
			if (gc.getCurrState() != CharacterState.Climbing) // Getting on ladder
			{
				if (this._initializer)
				{
					gc.setCollidedState(false);
					gc.setCurrState(CharacterState.Climbing);
					gc.setPosition(this._x, gc.getObjBox().y - _initialPositioning);
				}
				return;
			}
			else // Mid-climbing. Lift the character as needed.
			{
				gc.setPosition(this._x, gc.getObjBox().y - gc.getClimbingSpeed());
			}
		}
	}
}
