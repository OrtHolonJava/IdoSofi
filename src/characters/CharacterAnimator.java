package characters;
import java.awt.Graphics;
import java.awt.Point;
import images.Img;

/**
 * The CharacterAnimator Class - 
 */
public class CharacterAnimator extends Animation
{
	// (Visual-aspect final integers)
	private static final int _walkingFrames = 4, _jumpingFrames = 1, _standingFrames = 4, _climbingFrames = 2, _spriteWidth = 200, _spriteHeight = 120, _charWidth = 30;
	private static final int[] _animationDelays = {40, 100, 0, 40};
	private static final Point _drawerOffset = new Point(115, 35);
	
	private Img[] _walkingAnim, _jumpingAnim, _standingAnim, _climbingAnim, _currentAnim;
	private Img[][] _animations;
	
	/**
	 *  The Constructor Method - 
	 */
	public CharacterAnimator(int charID)
	{
		this._animations = new Img[CharacterState.values().length][];
		int animations = 0;
		
		/**
		 * Initializing the walking animation frames array -
		 */
		this._walkingAnim = new Img[_walkingFrames];
		this._animations[animations++] = this._walkingAnim;
		for (int i = 0; i < _walkingAnim.length; i++)
		{
			this._walkingAnim[i] = new Img(String.format("images\\sprites\\characters\\character%d\\walk\\%d.png", charID, i), 0, 0, _spriteWidth, _spriteHeight);
		}
		
		/**
		 * Initializing the standing animation frames array -
		 */
		this._standingAnim = new Img[_standingFrames];
		this._animations[animations++] = this._standingAnim;
		for (int i = 0; i < _standingAnim.length; i++)
		{
			this._standingAnim[i] = new Img(String.format("images\\sprites\\characters\\character%d\\stand\\%d.png", charID, i), 0, 0, _spriteWidth, _spriteHeight);
		}
		
		/**
		 * Initializing the jumping animation frames array -
		 */
		this._jumpingAnim = new Img[_jumpingFrames];
		this._animations[animations++] = this._jumpingAnim;
		for (int i = 0; i < _jumpingAnim.length; i++)
		{
			this._jumpingAnim[i] = new Img(String.format("images\\sprites\\characters\\character%d\\jump\\%d.png", charID, i), 0, 0, _spriteWidth, _spriteHeight);
		}
		
		/**
		 * Initializing the climbing animation frames array -
		 */
		this._climbingAnim = new Img[_climbingFrames];
		this._animations[animations++] = this._climbingAnim;
		for (int i = 0; i < _climbingAnim.length; i++)
		{
			this._climbingAnim[i] = new Img(String.format("images\\sprites\\characters\\character%d\\climb\\%d.png", charID, i), 0, 0, _spriteWidth, _spriteHeight);
		}
	}
	
	@Override
	public void drawCurrFrame(Graphics g, boolean isRight, int charX, int charY)
	{
		if (isRight)
		{
			this._currentAnim[this._currFrame].setImgCords(charX - (_spriteWidth - _charWidth - _drawerOffset.x), charY - _drawerOffset.y);
			this._currentAnim[this._currFrame].drawImgFlipped(g);
		}
		else
		{
			this._currentAnim[this._currFrame].setImgCords(charX - _drawerOffset.x, charY - _drawerOffset.y);
			this._currentAnim[this._currFrame].drawImg(g);
		}
	}
	
	public void setCurrFrame(int frame)
	{
		this._currFrame = frame;
	}
	
	public int getCurrFrame()
	{
		return this._currFrame;
	}
	
	public void setState(CharacterState state)
	{
		this._currFrame = 0;
		this._currTick = 0;
		this._currentAnim = this._animations[state.ordinal()];
		this._delay = _animationDelays[state.ordinal()];
	}
	
	/**
	 * The action of the animation timer (frame switch).
	 */
	public void frameSwitch()
	{
		this._currFrame = (this._currFrame + 1) % this._currentAnim.length;
	}

	@Override
	public void timerTicked()
	{
		this._currTick++;
		if (this._currTick >= this._delay)
		{
			this.frameSwitch();
			this._currTick = 0;
		}
	}
	
}
