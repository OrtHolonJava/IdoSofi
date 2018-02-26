package characters;
import java.awt.Rectangle;

/**
 * The LivingObject Class -
 * Represents any object in the game that is "alive".
 */
public abstract class LivingObject implements Gravity
{
	protected Rectangle _objBox;
	protected int _movementX, _movementY;
	protected boolean _isGravityApplied;
	
	public LivingObject(int x, int y, int width, int height)
	{
		this._objBox = new Rectangle(x, y, width, height);
		this._movementX = 0;
		this._movementY = 0;
	}
	
	public abstract void setCollidedState(boolean val);
	
	public  void setMovement()
	{
		this._objBox.x += this._movementX;
		this._objBox.y += this._movementY;
		this.applyGravity();
	}
	
	/**
	 * Getters and setters - 
	 */
	public Rectangle getObjBox() 
	{
		return _objBox;
	}
	
	public void setPosition(int x, int y)
	{
		this._objBox.x = x;
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
	
	
}
