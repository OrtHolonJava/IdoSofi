package characters;

public class GameCharacter extends LivingObject 
{
	private boolean _isRight;
	private CharacterState _currState;
	
	public GameCharacter(int x, int y, int width, int height) 
	{
		super(x, y, width, height);
		this._isRight = this._isGravityApplied = true;
		this._currState = CharacterState.Jumping;
		this._movementX = 0; this._movementY = -15;
	}
	
	public void walkRight()
	{
		this._movementX = 3;
		this._currState = CharacterState.Walking;
	}
	
	public void walkLeft()
	{
		this._movementX = -3;
		this._currState = CharacterState.Walking;
	}
	
	@Override
	public void setMovement()
	{
		this.applyGravity();
		this._objBox.x += this._movementX;
		this._objBox.y += this._movementY;
	}
	
	public CharacterState getCurrState() 
	{
		return _currState;
	}

	public void setCurrState(CharacterState currState) 
	{
		_currState = currState;
	}

	@Override
	public void setGravity(boolean val)
	{
		this._isGravityApplied = val;
	}

	@Override
	public void applyGravity()
	{
		// TODO Auto-generated method stub
		if (this._isGravityApplied)
		{
			if (this._movementY < this._maxGForce)
			{
				this._movementY += 1;
			}
			this._currState = CharacterState.Falling;
		}
		else
		{
			this._movementY = 0;
			this._currState = CharacterState.Standing;
		}
	}

}
