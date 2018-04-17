package characters;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

public class PlayerKeyListener implements KeyListener
{
	private GameCharacter _char;
	private static boolean[] _pressedKeys;

	public PlayerKeyListener(GameCharacter ch)
	{
		this._char = ch;
		_pressedKeys = new boolean[525]; // 525 - Number of keycodes.
	}

	@Override
	public void keyPressed(KeyEvent e)
	{
		_pressedKeys[e.getKeyCode()] = true;

		/**
		 * Handle momentary input -
		 */
		switch (e.getKeyCode())
		{
			case KeyEvent.VK_Z:
				this._char.jump();
				break;
			
			case KeyEvent.VK_UP:
				this._char.setWillingToClimb(true);
				break;
		}	
	}

	@Override
	public void keyReleased(KeyEvent e)
	{
		_pressedKeys[e.getKeyCode()] = false;
		
		/**
		 * Handle momentary input -
		 */
		switch (e.getKeyCode())
		{								
			case KeyEvent.VK_RIGHT:
				this._char.stopWalking();
				break;
				
			case KeyEvent.VK_LEFT:
				this._char.stopWalking();
				break;
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
		if (_pressedKeys[KeyEvent.VK_LEFT])
		{
			this._char.walkLeft();
		}
		else if (_pressedKeys[KeyEvent.VK_RIGHT])
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
