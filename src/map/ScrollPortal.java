package map;
import java.util.ArrayList;
import java.util.List;
import java.awt.Graphics;
import java.awt.Rectangle;
import animation.Animation;

/**
 * The ScrollPortal Class.
 */
public class ScrollPortal
{
	private static final int _width = 159, _height = 215, _animationFrames = 12, _animationDelay = 20; // Portal dimensions and visual related integeric values.
	
	private int _id;
	private boolean _isRight;
	private Rectangle _rect;
	private List<ScrollPortalListener> _listeners;
	private Animation _anim;
	
	public ScrollPortal(int id, int x, int y, boolean isRight)
	{
		this._id = id;
		this._isRight = isRight;
		this._rect = new Rectangle(x, y, _width, _height);
		this._listeners = new ArrayList<ScrollPortalListener>();
		
		/**
		 * Initializing animation - 
		 */
		this._anim = new Animation(_animationFrames, _animationDelay, "images\\portal", _width, _height);
	}
	
	public void addListener(ScrollPortalListener lis)
	{
		this._listeners.add(lis);
	}
	
	public void drawPortal(Graphics g)
	{
		this._anim.drawCurrentFrame(g, this._isRight, this._rect.x, this._rect.y);
	}
}
