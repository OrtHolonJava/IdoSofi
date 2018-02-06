package characters;
import java.awt.Graphics;
import java.awt.Point;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.Timer;
import images.Img;

/**
 * The CharacterAnimator Class - 
 */
public class CharacterAnimator implements ActionListener
{
	private static final int _walkingFrames = 4, _jumpingFrames = 1, _standingFrames = 4, _spriteWidth = 200, _spriteHeight = 120, _charWidth = 30;
	private static final Point _drawerOffset = new Point(115, 35);
	
	private Img[] _walkingAnim, _jumpingAnim, _standingAnim, _currentAnim;
	
	private int _currFrame;
	private int _timeBetweenFrames; // Currently not in use.
	private Timer _timer; // Currently not in use.
	
	/**
	 *  The Constructor Method - 
	 */
	public CharacterAnimator(int charID)
	{
		this._currFrame = 0;
		
		/**
		 * Initializing the walking animation frames array -
		 */
		this._walkingAnim = new Img[_walkingFrames];
		for (int i = 0; i < _walkingAnim.length; i++)
		{
			this._walkingAnim[i] = new Img(String.format("images\\sprites\\characters\\character%d\\walk\\%d.png", charID, i), 0, 0, _spriteWidth, _spriteHeight);
		}
		
		/**
		 * Initializing the standing animation frames array -
		 */
		this._standingAnim = new Img[_standingFrames];
		for (int i = 0; i < _standingAnim.length; i++)
		{
			this._standingAnim[i] = new Img(String.format("images\\sprites\\characters\\character%d\\stand\\%d.png", charID, i), 0, 0, _spriteWidth, _spriteHeight);
		}
		
		/**
		 * Initializing the jumping animation frames array -
		 */
		this._jumpingAnim = new Img[_jumpingFrames];
		for (int i = 0; i < _jumpingAnim.length; i++)
		{
			this._jumpingAnim[i] = new Img(String.format("images\\sprites\\characters\\character%d\\jump\\%d.png", charID, i), 0, 0, _spriteWidth, _spriteHeight);
		}
		
		this._currentAnim = this._standingAnim;
	}
	
	public void drawCharacter(Graphics g, boolean isRight, int charX, int charY)
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
	
	/**
	 * The action of the animation timer.
	 */
	@Override
	public void actionPerformed(ActionEvent e)
	{
		
	}
	
}
