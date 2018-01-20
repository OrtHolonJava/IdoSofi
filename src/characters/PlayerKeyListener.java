package characters;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

public class PlayerKeyListener implements KeyListener 
{
	private GameCharacter _char;
	private boolean[] _pressedKeys;
	
	public PlayerKeyListener(GameCharacter ch)
	{
		this._char = ch;
		this._pressedKeys = new boolean[250];
	}
	
	@Override
	public void keyPressed(KeyEvent e) 
	{
		this._pressedKeys[e.getKeyCode()] = true;
		
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
		this._pressedKeys[e.getKeyCode()] = false;
	}
	
	/**
	 * Method: Handles continuous input (such as walking) - 
	 */
	public void processInput()
	{
		if (this._pressedKeys[KeyEvent.VK_A] || this._pressedKeys[KeyEvent.VK_D])
		{
			if (this._pressedKeys[KeyEvent.VK_A])
			{
				this._char.walkLeft();
			}
			if (this._pressedKeys[KeyEvent.VK_D])
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
