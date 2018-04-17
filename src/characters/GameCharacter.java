package characters;
import java.util.ArrayList;
import java.util.List;

import animation.CharacterAnimator;

/**
 * The GameCharacter Class - Represents the main character of the game -
 */
public class GameCharacter extends LivingObject
{
	private int _charID;
	private boolean _willingToClimb;
	private List<GameCharacterListener> _listeners;
	
	/**
	 * Character's stats -
	 */
	public static final int _charBoxWidth = 30, _charBoxHeight = 65;
	public static final int _walkingSpeed = 4, _flyingSpeed = 3, _climbingSpeed = 3, _jumpingHeight = 17;

	public GameCharacter(int x, int y, int width, int height, int id)
	{
		super(x, y, width, height);
		this._charID = id;
		this._listeners = new ArrayList<GameCharacterListener>();
		
		/**
		 * Setting defaultive settings -
		 */
		this._isRight = this._isGravityApplied = true;
		this._isCollided = this._willingToClimb = false;
		this._movementX = 0;
		this._movementY = 0;
		this._animator = new CharacterAnimator(this._charID);
		this.setCurrState(CharacterState.Falling);
	}
	
	public void addListener(GameCharacterListener lis)
	{
		this._listeners.add(lis);
	}
	
	public void walkRight()
	{
		if (this.getCurrState() != CharacterState.Climbing)
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
		if (this.getCurrState() != CharacterState.Climbing)
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
		if (this._isCollided && this.getCurrState() != CharacterState.Climbing)
		{
			this._movementY = -_jumpingHeight;
			this._isCollided = false;
		}
	}
	
	public CharacterState getCurrState()
	{
		return CharacterState.values()[this._currState];
	}

	@Override
	public void setGravity(boolean val)
	{
		this._isGravityApplied = val;
	}
	
	/**
	 * Method - The way the game character implements the gravity interface
	 */
	@Override
	public void applyGravity()
	{
		if (this._isCollided || this.getCurrState() == CharacterState.Climbing) // Collided or not prone to gravity.
		{
			this._movementY = 0;
			this._isGravityApplied = false;
		}
		else
		{
			this._isGravityApplied = true;
			if (this._movementY < Gravity._maxGForce)
			{
				this._movementY++;
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
		this._movementY = 0;
	}
	
	/**
	 * Sets the relevant state whenever the character is not actively moving
	 */
	private void deEffectMovement()
	{
		/**
		 * Reset character status -
		 */
		if (this._isCollided)
		{
			this.setCurrState(CharacterState.Standing);
			this.stopClimbing();
			this.setWillingToClimb(false);
		}
		else
		{
			this.setCurrState(CharacterState.Falling);
		}
	}
	
	@Override
	public void stopWalking()
	{
		if (this.getCurrState() == CharacterState.Walking || this.getCurrState() == CharacterState.Falling || this.getCurrState() == CharacterState.Standing) // Character is walking
		{
			this.stopHorizontalMovement();
			this.deEffectMovement();
		}
	}
	
	public void stopClimbing()
	{
		if (this.getCurrState() == CharacterState.Climbing)
		{
			this.deEffectMovement();
			this.stopVerticalMovement();
		}
	}
	
	/**
	 * Method - Set the current state of collision with terrain.
	 */
	@Override
	public void setCollidedState(boolean val)
	{
		if (val && this._movementY < 0)
		{
			return;
		}
		if (this._isCollided == false && val) // If the character has just landed.
		{
			this._isCollided = val;
			this.deEffectMovement();
			return;
		}
		this._isCollided = val;
	}

	public boolean isWillingToClimb()
	{
		return _willingToClimb;
	}

	public void setWillingToClimb(boolean willingToClimb)
	{
		_willingToClimb = willingToClimb;
	}
	
	public int getClimbingSpeed()
	{
		return _climbingSpeed;
	}
	
	@Override
	public void update()
	{
		super.update();
		for (int i = 0; i < this._listeners.size(); i++)
		{
			this._listeners.get(i).playerMoved(this);
		}
	}
}
