package characters;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;



public class PlayerKeyListener implements KeyListener 
{
	private GameCharacter _char;
	private boolean[] _pressedKeys;
	private static int KEY_A = 0, KEY_D = 1, COUNTINUOUS_INPUT_KEYS = 2;
	
	public PlayerKeyListener(GameCharacter ch)
	{
		this._char = ch;
		this._pressedKeys = new boolean[COUNTINUOUS_INPUT_KEYS];
	}
	
	@Override
	public void keyPressed(KeyEvent e) 
	{
		if (e.getKeyCode() == KeyEvent.VK_A)
		{
			this._pressedKeys[KEY_A] = true;
		}
		
		if (e.getKeyCode() == KeyEvent.VK_D)
		{
			this._pressedKeys[KEY_D] = true;
		}
		
		/**
		 * Handle momentary input - 
		 */
		if (e.getKeyCode() == KeyEvent.VK_W)
		{
			this._char.jump();
		}
		
		
	}

	@Override
	public void keyReleased(KeyEvent e)
	{
		if (e.getKeyCode() == KeyEvent.VK_A)
		{
			this._pressedKeys[KEY_A] = false;
		}
		
		if (e.getKeyCode() == KeyEvent.VK_D)
		{
			this._pressedKeys[KEY_D] = false;
		}
	}
	
	/**
	 * Method: Handles continuous input (such as walking) - 
	 */
	public void processInput()
	{
		if (this._pressedKeys[KEY_A] || this._pressedKeys[KEY_D])
		{
			if (this._pressedKeys[KEY_A])
			{
				this._char.walkLeft();
			}
			if (this._pressedKeys[KEY_D])
			{
				this._char.walkRight();
			}	
		}
		else
		{
			this._char.deEffectXMovement();
			this._char.stopHorizontalMovement();
		}
	}
	
	@Override
	public void keyTyped(KeyEvent arg0) 
	{
		
	}

}
