package animation;
import java.awt.Graphics;

import images.Img;

/**
 * The Basic Animation Class.
 */
public class Animation implements AnimationTimerListener
{
	protected Img[] _frames;
	protected int _currTick, _currFrame, _delay;
	
	public Animation(int frames, int delay, String folderPath, int spriteWidth, int spriteHeight)
	{
		/**
		 * Registering the animation as an animation timer listener -
		 */
		AnimationTimer.addTimerListener(this);
		
		/**
		 * Initializing images array - 
		 */
		this._frames = new Img[frames];
		for (int i = 0; i < frames; i++)
		{
			this._frames[i] = new Img(String.format(folderPath + "\\%d.png", i), 0, 0, spriteWidth, spriteHeight);
		}
		
		this._currTick = 0;
		this._currFrame = 0;
		this._delay = delay;
	}
	
	public void drawCurrentFrame(Graphics g, boolean isRight, int x, int y)
	{
		this._frames[this._currFrame].setImgCords(x, y);
		
		if (isRight)
		{
			this._frames[this._currFrame].drawImgFlipped(g);
		}
		else
		{
			this._frames[this._currFrame].drawImg(g);
		}
	}

	/**
	 * The action of the animation timer (frame switch).
	 */
	public void frameSwitch()
	{
		this._currFrame = (this._currFrame + 1) % this._frames.length;
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
	
	/**
	 * Getters and Setters - 
	 */
	public int getCurrTick()
	{
		return _currTick;
	}

	public void setCurrTick(int currTick)
	{
		_currTick = currTick;
	}

	public int getCurrFrame()
	{
		return _currFrame;
	}

	public void setCurrFrame(int currFrame)
	{
		_currFrame = currFrame;
	}

	public int getDelay()
	{
		return _delay;
	}

	public void setDelay(int delay)
	{
		_delay = delay;
	}
	
	
	
}
