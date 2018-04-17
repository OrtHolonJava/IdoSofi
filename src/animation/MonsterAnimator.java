package animation;
import java.awt.Graphics;
import java.awt.Point;
import characters.MonsterState;

public class MonsterAnimator extends ObjectAnimator
{
	// (Visual-aspect final integers)
	private static final int _spriteWidth = 60, _spriteHeight = 60, _charWidth = 30;
	private static final int[] _animationDelays = {70, 70, 0, 0, 100}, _animationsFrames = {4, 2, 1, 1, 4};
	private static final Point _drawerOffset = new Point(15, 30);
	
	/**
	 *  The Constructor Method - 
	 */
	public MonsterAnimator(int monsterID)
	{
		super(MonsterState.values().length);
		this._animations = new Animation[MonsterState.values().length];		
		for (int i = 0; i < MonsterState.values().length; i++)
		{
			this.initializeAnimation(monsterID, MonsterState.values()[i]);
		}
	}
	
	private void initializeAnimation(int charID, MonsterState state)
	{
		int stateOrdinal = state.ordinal();
		this._animations[stateOrdinal] = new Animation(_animationsFrames[stateOrdinal], _animationDelays[stateOrdinal], String.format("images\\sprites\\monsters\\monster%d\\%s", charID, state.toString()), _spriteWidth, _spriteHeight);
	}
	
	@Override
	public void drawCurrFrame(Graphics g, boolean isRight, int charX, int charY)
	{
		super.drawCurrFrame(g, isRight, charX, charY, _spriteWidth, _charWidth, _drawerOffset);
	}

}
