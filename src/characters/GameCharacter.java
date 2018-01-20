package characters;

public class GameCharacter extends LivingObject 
{
	private boolean _isRight, _isCollided;
	private CharacterState _currState;
	
	public GameCharacter(int x, int y, int width, int height) 
	{
		super(x, y, width, height);
		this._isRight = this._isGravityApplied = true;
		this._currState = CharacterState.Falling;
		this._isCollided = false;
		this._movementX = 0; this._movementY = 0;
	}
	
	public void walkRight()
	{
		if (this._isCollided)
		{
			this._currState = CharacterState.Walking;
			this._movementX = 4;
		}
		else
		{
			this._currState = CharacterState.Flying;
			this._movementX = 3;
		}
	}
	
	public void walkLeft()
	{
		if (this._isCollided)
		{
			this._currState = CharacterState.Walking;
			this._movementX = -4;
		}
		else
		{
			this._currState = CharacterState.Flying;
			this._movementX = -3;
		}
	}
	
	public void jump()
	{
		if (this._isCollided)
		{
			this._movementY = -17;
			this._currState = CharacterState.Jumping;
			this._isCollided = false;
		}
	}
	
	@Override
	public void setMovement()
	{
		this._objBox.x += this._movementX;
		this._objBox.y += this._movementY;
		this.applyGravity();
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
		if (this._isCollided)
		{
			this._movementY = 0;
			this._isGravityApplied = false;
		}
		else
		{
			this._isGravityApplied = true;
			if (this._movementY < Gravity._maxGForce)
			{
				this._movementY += 1;
			}
			this._currState = CharacterState.Falling;
		}
	}
	
	/**
	 * Method: Whenever the player is not actively moving on the horizontal axis
	 */
	public void stopHorizontalMovement()
	{
		if (this._movementX > 0)
		{
			this._movementX--;
		}
		else if (this._movementX < 0)
		{
			this._movementX++;
		}
	}
	
	public void deEffectXMovement()
	{
		/**
		 * Reset character status - 
		 */
		if (this._isCollided)
		{
			this._currState = CharacterState.Standing;
		}
		else
		{
			this._currState = CharacterState.Falling;
		}
	}
	
	public boolean isRight() 
	{
		return _isRight;
	}

	public void setRight(boolean isRight) 
	{
		_isRight = isRight;
	}

	public boolean isCollided() 
	{
		return _isCollided;
	}

	@Override
	public void setCollidedState(boolean val) 
	{
		this._isCollided = val;
	}
	
	

}
