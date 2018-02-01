package characters;

import map.Map;
import map.MapPanel;

/**
 * The Player's Camera Class - 
 */
public class PlayerCamera 
{
	private double _x, _y;
	private float _scale;
	private final int _alpha = 20;
	private GameCharacter _char;
	private MapPanel _panel;
	
	/**
	 * The Constructor Method.
	 * @param ch
	 * @param scale
	 */
	public PlayerCamera(GameCharacter ch, float scale, MapPanel panel)
	{
		this._char = ch;
		this._scale = scale;
		this._panel = panel;
		this._x = (this._char.getObjBox().x - (this._panel.getWidth() / (2 * this._scale))) * -1;
		this._y = (this._char.getObjBox().y - (this._panel.getHeight() / (2 * this._scale))) * -1; 
	}
	
	public void setPosition()
	{
		/**
		 * If the player is within the map's vertical bounds - 
		 */
		if (this._char.getObjBox().x + (this._panel.getWidth() / (2 * this._scale)) <= this._panel.getMap().getMapWidth() * Map._blockSize
				&& this._char.getObjBox().x - (this._panel.getWidth() / (2 * this._scale)) >= 0)
		{
			this._x += ((this._char.getObjBox().x - (this._panel.getWidth() / (2 * this._scale))) * -1 - this._x) / this._alpha;
		}
		else
		{
			if (this._char.getObjBox().x - (this._panel.getWidth() / (2 * this._scale)) >= 0)
			{
				this._x += ((this._panel.getMap().getMapWidth() * Map._blockSize - (this._panel.getWidth() / this._scale)) * -1 - this._x) / this._alpha;
			}
			else
			{
				this._x += (-this._x) / this._alpha;
			}
		}
		
		if (this._char.getObjBox().y + (this._panel.getHeight() / (2 * this._scale)) <= this._panel.getMap().getMapHeight() * Map._blockSize)
		{
			this._y += ((this._char.getObjBox().y - (this._panel.getHeight() / (2 * this._scale))) * -1 - this._y) / this._alpha;
		}
		else
		{
			this._y += ((this._panel.getMap().getMapHeight() * Map._blockSize - (this._panel.getHeight() / this._scale)) * -1 - this._y) / this._alpha;
		}
	}

	public double getX()
{
		return _x;
	}

	public void setX(double x) 
	{
		_x = x;
	}

	public double getY() 
	{
		return _y;
	}

	public void setY(double y) 
	{
		_y = y;
	}

	public float getScale() 
	{
		return _scale;
	}

	public void setScale(float scale) 
	{
		_scale = scale;
	}
	
	
}
