package characters;

import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

public class PlayerKeyListener implements KeyListener
{
	private GameCharacter _char;
	private static boolean[] _pressedKeys;
	public static final int KEY_LEFT = 0, KEY_RIGHT = 1, KEY_UP = 2, CONTINUOUS_INPUT_KEYS = 3;

	public PlayerKeyListener(GameCharacter ch)
	{
		this._char = ch;
		this._pressedKeys = new boolean[CONTINUOUS_INPUT_KEYS];
	}

	@Override
	public void keyPressed(KeyEvent e)
	{
		if (e.getKeyCode() == KeyEvent.VK_LEFT)
		{
			_pressedKeys[KEY_LEFT] = true;
		}

		else if (e.getKeyCode() == KeyEvent.VK_RIGHT)
		{
			_pressedKeys[KEY_RIGHT] = true;
		}
		
		else if (e.getKeyCode() == KeyEvent.VK_UP)
		{
			_pressedKeys[KEY_UP] = true;
		}

		/**
		 * Handle momentary input -
		 */
		if (e.getKeyCode() == KeyEvent.VK_Z)
		{
			this._char.jump();
		}

	}

	@Override
	public void keyReleased(KeyEvent e)
	{
		/**
		 * Stop walking - 
		 */
		if (e.getKeyCode() == KeyEvent.VK_LEFT)
		{
			_pressedKeys[KEY_LEFT] = false;
			this._char.stopWalking();
		}

		else if (e.getKeyCode() == KeyEvent.VK_RIGHT)
		{
			_pressedKeys[KEY_RIGHT] = false;
			this._char.stopWalking();
		}
		
		/**
		 * Stop climbing;
		 */
		else if (e.getKeyCode() == KeyEvent.VK_UP)
		{
			_pressedKeys[KEY_UP] = false;
			this._char.stopClimbing();
		}
	}

	/**
	 * Method: Handles continuous input (such as walking) -
	 */
	public void processInput()
	{
		/**
		 * Walking -
		 */
		if (_pressedKeys[KEY_LEFT])
		{
			this._char.walkLeft();
		}
		else if (_pressedKeys[KEY_RIGHT])
		{
			this._char.walkRight();
		}
	}

	@Override
	public void keyTyped(KeyEvent arg0)
	{

	}
	
	public static boolean[] getPressedKeys()
	{
		return _pressedKeys;
	}

}
