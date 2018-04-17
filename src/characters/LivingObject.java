package characters;
import java.awt.Graphics;
import java.awt.Point;
import java.awt.Rectangle;
import animation.ObjectAnimator;
import map.Map;

/**
 * The LivingObject Class -
 * Represents any object in the game that is "alive".
 */
public abstract class LivingObject implements Gravity, Updateable
{
	protected Rectangle _objBox;
	protected int _movementX, _movementY, _movementSlide;
	protected boolean _isGravityApplied, _isRight, _isCollided;
	protected ObjectAnimator _animator;
	protected int _currState;
	private int _lastFeetBlock;
	
	public LivingObject(int x, int y, int width, int height)
	{
		this._objBox = new Rectangle(x, y, width, height);
		this._movementX = 0;
		this._movementY = 0;
		this._lastFeetBlock = this.getFeetBlock(); 
	}
	
	public abstract void setCollidedState(boolean val);	
	
	public <E extends Enum> void setCurrState(E currState)
	{
		if (currState.ordinal() != this._currState)
		{
			_currState = currState.ordinal();
			this._animator.setState(currState.ordinal());
		}
	}
	
	public void setMovement()
	{
		this._objBox.x += this._movementX + this._movementSlide;
		this._objBox.y += this._movementY;
		this.applyGravity();
		this.reduceSlide();
	}
	
	/**
	 * Every living object has the ability to walk and therefore any class that extends this should implement those.
	 */
	public abstract void walkLeft();
	public abstract void walkRight();
	public abstract void stopWalking();
	
	private void reduceSlide()
	{
		if (this._movementSlide != 0)
		{
			this._movementSlide += (this._movementSlide > 0) ? -1 : 1;
		}
	}
	
	/**
	 * Getters and setters - 
	 */
	public boolean isCollided()
	{
		return _isCollided;
	}
	
	/**
	 * Method - Returns the map cell ID in which the character's feet are located.
	 */
	public int getFeetBlock()
	{
		return (this._objBox.x + this._objBox.width / 2) / Map._blockSize + (this._objBox.y + this._objBox.height) / Map._blockSize * Map._sizeW;
	}
	
	public void setSlide(int power)
	{
		this._movementSlide = power;
	}
	
	/**
	 * Method - Returns the map cell ID in which the character's head is located.
	 */
	public int getMidBlock()
	{
		return (this._objBox.x + this._objBox.width / 2) / Map._blockSize + (this._objBox.y + this._objBox.height - Map._blockSize) / Map._blockSize * Map._sizeW;
	}
	
	/**
	 * Method - Returns the "feet point" of the living object.
	 * @return point
	 */
	public Point getFeetPoint()
	{
		return new Point(this._objBox.x + this._objBox.width / 2, this._objBox.y + this._objBox.height);
	}
	
	public Rectangle getObjBox() 
	{
		return _objBox;
	}
	
	public void setPosition(int x, int y)
	{
		this._objBox.x = x;
		this._objBox.y = y;
	}
	
	/**
	 * Special set - Set the object in a location in which his feet are located in the given x value.
	 * @param x
	 */
	public void setFeetX(int x)
	{
		this.setX(x - this._objBox.width / 2);
	}
	
	public void setX(int x)
	{
		this._objBox.x = x;
	}
	
	public void setY(int y)
	{
		this._objBox.y = y;
	}
	
	public int getMovementX() 
	{
		return _movementX;
	}

	public void setMovementX(int movementX) 
	{
		_movementX = movementX;
	}

	public int getMovementY() 
	{
		return _movementY;
	}

	public void setMovementY(int movementY) 
	{
		_movementY = movementY;
	}
	
	public boolean isRight()
	{
		return _isRight;
	}

	public void setRight(boolean isRight)
	{
		_isRight = isRight;
	}
	
	public int getLastFeetBlock()
	{
		return _lastFeetBlock;
	}
	
	public void setLastFeetBlock(int lastFeetBlock)
	{
		_lastFeetBlock = lastFeetBlock;
	}

	public void drawObj(Graphics g)
	{
		this._animator.drawCurrFrame(g, this._isRight, this._objBox.x, this._objBox.y);
	}
	
	public void update()
	{
		this.setMovement();
	}
}
