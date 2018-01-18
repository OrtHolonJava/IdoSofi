package characters;

import map.MapPanel;

/**
 * The Player's Camera Class - 
 */
public class PlayerCamera 
{
	private int _x, _y;
	private float _scale;
	private final int _alpha = 25;
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
		this._x = (this._char.getObjBox().x - (this._panel.getWidth() / (int)(2 * this._scale))) * -1;
		this._y = (this._char.getObjBox().y - (this._panel.getHeight() / (int)(2 * this._scale))) * -1; 
	}
	
	public void setPosition()
	{
		if (this._char.getObjBox().x + (this._panel.getWidth() / (int)(2 * this._scale)) <= this._panel.getMap().getMapWidth() * this._panel.getMap().getBlockSize())
		{
			this._x += ((this._char.getObjBox().x - (this._panel.getWidth() / (int)(2 * this._scale))) * -1 - this._x) / this._alpha;
		}
		else
		{
			this._x += ((this._panel.getMap().getMapWidth() * this._panel.getMap().getBlockSize() - (this._panel.getWidth() / this._scale)) * -1 - this._x) / this._alpha;
		}
		if (this._char.getObjBox().y + (this._panel.getHeight() / (int)(2 * this._scale)) <= this._panel.getMap().getMapHeight() * this._panel.getMap().getBlockSize())
		{
			this._y += ((this._char.getObjBox().y - (this._panel.getHeight() / (int)(2 * this._scale))) * -1 - this._y) / this._alpha;
		}
		else
		{
			this._y += ((this._panel.getMap().getMapHeight() * this._panel.getMap().getBlockSize() - (this._panel.getHeight() / this._scale)) * -1 - this._y) / this._alpha;
		}
	}

	public int getX()
{
		return _x;
	}

	public void setX(int x) 
	{
		_x = x;
	}

	public int getY() 
	{
		return _y;
	}

	public void setY(int y) 
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
