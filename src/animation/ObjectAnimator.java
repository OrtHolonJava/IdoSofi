package animation;
import java.awt.Graphics;
import java.awt.Point;

public abstract class ObjectAnimator
{
		protected Animation[] _animations;
		protected int _currAnimation;
		
		/**
		 *  The Constructor Method - 
		 */
		public ObjectAnimator(int states)
		{
			this._animations = new Animation[states];
		}
		
		public void setState(int stateOrdinal) 
		{
			this._currAnimation = stateOrdinal;
			this._animations[this._currAnimation].setCurrFrame(0);
			this._animations[this._currAnimation].setCurrTick(0);
		}
		
		public void drawCurrFrame(Graphics g, boolean isRight, int charX, int charY, int spriteWidth, int charWidth, Point drawerOffset)
		{
			int fixedX, fixedY;
			if (isRight)
			{
				fixedX = charX - (spriteWidth - charWidth - drawerOffset.x);
			}
			else
			{
				fixedX = charX - drawerOffset.x;
			}
			fixedY = charY - drawerOffset.y;
			this._animations[this._currAnimation].drawCurrentFrame(g, isRight, fixedX, fixedY);
		}
		
		public abstract void drawCurrFrame(Graphics g, boolean isRight, int charX, int charY);
}
