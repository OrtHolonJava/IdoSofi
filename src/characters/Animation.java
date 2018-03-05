package characters;
import java.awt.Graphics;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.List;
import javax.swing.Timer;

/**
 * The Animation Class -
 */
public abstract class Animation implements AnimationTimerListener
{
	/**
	 * Static variables - 
	 */
	private static final int _timeUnit = 2;
	private static List<AnimationTimerListener> _timerListeners = new ArrayList<AnimationTimerListener>();
	
	/**
	 * Initialization of the universal animation timer.
	 */
	private static Timer _universalTimer = new Timer(_timeUnit, new ActionListener()
	{
		@Override
		public void actionPerformed(ActionEvent e)
		{
			for (int i = 0; i < _timerListeners.size(); i++)
			{
				_timerListeners.get(i).timerTicked();
			}
		}
	});
	
	protected int _currTick, _currFrame, _delay;
	
	public Animation()
	{
		addTimerListener(this);
		this._currTick=0;
	}
	
	public abstract void drawCurrFrame(Graphics g, boolean facingRight, int x, int y);
	
	public static void startUniversalTimer()
	{
		_universalTimer.start();
	}
	
	public static void addTimerListener(AnimationTimerListener lis)
	{
		_timerListeners.add(lis);
	}
}
