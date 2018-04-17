package map;

import characters.CharacterState;
import characters.GameCharacter;
import characters.LivingObject;

public class LadderBlock extends Block
{
	private final int _initialPositioning = 4;
	private boolean _initializer;

	public LadderBlock(int tile, int x, int y, boolean init)
	{
		super(tile, x, y);
		this._initializer = init;
	}

	@Override
	public void affectLivingObj(LivingObject obj)
	{
		if (obj instanceof GameCharacter)
		{
			GameCharacter gc = (GameCharacter)obj;
			if (gc.isWillingToClimb()) // The character is willing to climb
			{
				if (gc.getCurrState() != CharacterState.Climbing) // Getting on ladder
				{
					if (this._initializer)
					{
						gc.setCollidedState(false);
						gc.stopHorizontalMovement();
						gc.setCurrState(CharacterState.Climbing);
						gc.setPosition(this.getX(), gc.getObjBox().y - _initialPositioning);
					}
					return;
				}
				else // Mid-climbing. Lift the character as needed.
				{
					gc.setPosition(this.getX(), gc.getObjBox().y - gc.getClimbingSpeed());
				}
				obj.setLastFeetBlock(obj.getFeetBlock());
			}
		}
	}
}
