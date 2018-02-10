package characters;

import java.awt.Graphics;

import map.Map;

/**
 * The GameCharacter Class - Represents the main character of the game -
 */
public class GameCharacter extends LivingObject
{
	private boolean _isRight, _isCollided;
	private CharacterState _currState;
	private int _charID;
	private CharacterAnimator _animator;

	/**
	 * Character's stats -
	 */
	private final int _walkingSpeed = 4, _flyingSpeed = 3, _climbingSpeed = 3, _jumpingHeight = 18;

	public GameCharacter(int x, int y, int width, int height, int id)
	{
		super(x, y, width, height);
		this._charID = id;
		this._isRight = this._isGravityApplied = true;
		this._isCollided = false;
		this._movementX = 0;
		this._movementY = 0;
		this._animator = new CharacterAnimator(this._charID);
		this.setCurrState(CharacterState.Falling); // (Defaultive)
	}

	public void walkRight()
	{
		if (this._currState != CharacterState.Climbing)
		{
			this._isRight = true;
			if (this._isCollided)
			{
				this.setCurrState(CharacterState.Walking);
				this._movementX = _walkingSpeed;
			}
			else
			{
				this._movementX = _flyingSpeed;
			}
		}
	}

	public void walkLeft()
	{
		if (this._currState != CharacterState.Climbing)
		{
			this._isRight = false;
			if (this._isCollided)
			{
				this.setCurrState(CharacterState.Walking);
				this._movementX = -_walkingSpeed;
			}
			else
			{
				this._movementX = -_flyingSpeed;
			}
		}
	}

	public void jump()
	{
		if (this._isCollided && this._currState != CharacterState.Climbing)
		{
			this._movementY = -_jumpingHeight;
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
		if (!currState.equals(this._currState))
		{
			_currState = currState;
			this._animator.setState(currState);
		}
	}

	@Override
	public void setGravity(boolean val)
	{
		this._isGravityApplied = val;
	}

	@Override
	public void applyGravity()
	{
		if (this._isCollided || this._currState == CharacterState.Climbing)
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
			this.setCurrState(CharacterState.Falling);
		}
	}

	/**
	 * Method: Whenever the player is not actively moving on the horizontal axis
	 */
	public void stopHorizontalMovement()
	{
		this._movementX = 0;
	}
	
	/**
	 * Method: Whenever the player is not actively moving on the vertical axis
	 */
	public void stopVerticalMovement()
	{
		this._movementX = 0;
	}

	private void deEffectMovement()
	{
		/**
		 * Reset character status -
		 */
		if (this._isCollided)
		{
			this.setCurrState(CharacterState.Standing);
		}
		else
		{
			this.setCurrState(CharacterState.Falling);
		}
	}
	
	public void stopBeingActive()
	{
		this.deEffectMovement();
		this.stopHorizontalMovement();
	}
	
	public void stopClimbing()
	{
		if (this._currState == CharacterState.Climbing)
		{
			System.out.println("stop");
			this.stopVerticalMovement();
			stopBeingActive();
		}
	}
	
	/**
	 * Method - Returns the map cell ID in which the character's feet are located.
	 */
	public int getFeetBlock(int mapWidth)
	{
		return (this._objBox.x + Map._blockSize / 2) / Map._blockSize + (this._objBox.y + this._objBox.height) / Map._blockSize * mapWidth;
	}

	/**
	 * Method - Returns the map cell ID in which the character's head is located.
	 */
	public int getHeadBlock(int mapWidth)
	{
		return (this._objBox.x + Map._blockSize / 2) / Map._blockSize + this._objBox.y / Map._blockSize * mapWidth;
	}
	
	/**
	 * Method - Returns the map cell ID in which the character's head is located.
	 */
	public int getMidBlock(int mapWidth)
	{
		return (this._objBox.x + Map._blockSize / 2) / Map._blockSize + (this._objBox.y + this._objBox.height - Map._blockSize) / Map._blockSize * mapWidth;
	}
	
	
	public int getClimbingSpeed()
	{
		return this._climbingSpeed;
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

	public void drawCharacter(Graphics g)
	{
		this._animator.drawCharacter(g, this._isRight, this._objBox.x, this._objBox.y);
	}

}
