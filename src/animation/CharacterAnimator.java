package animation;
import java.awt.Graphics;
import java.awt.Point;

import characters.CharacterState;

/**
 * The CharacterAnimator Class -
 * Acts as the graphical manager of the main game character. 
 */
public class CharacterAnimator extends ObjectAnimator
{
	// (Visual-aspect final integers)
	private static final int _spriteWidth = 200, _spriteHeight = 120, _charWidth = 30;
	private static final int[] _animationDelays = {70, 150, 0, 70, 0}, _animationsFrames = {4, 4, 1, 2, 1};
	private static final Point _drawerOffset = new Point(115, 35);
	
	/**
	 *  The Constructor Method - 
	 */
	public CharacterAnimator(int charID)
	{
		super(CharacterState.values().length);
		this._animations = new Animation[CharacterState.values().length];		
		for (int i = 0; i < CharacterState.values().length; i++)
		{
			this.initializeAnimation(charID, CharacterState.values()[i]);
		}
	}
	
	private void initializeAnimation(int charID, CharacterState state)
	{
		int stateOrdinal = state.ordinal();
		this._animations[stateOrdinal] = new Animation(_animationsFrames[stateOrdinal], _animationDelays[stateOrdinal], String.format("images\\sprites\\characters\\character%d\\%s", charID, state.toString()), _spriteWidth, _spriteHeight);
	}
	
	@Override
	public void drawCurrFrame(Graphics g, boolean isRight, int charX, int charY)
	{
		super.drawCurrFrame(g, isRight, charX, charY, _spriteWidth, _charWidth, _drawerOffset);
	}
	
}
