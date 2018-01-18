package map;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class GameLoop implements ActionListener
{
	private MapPanel _gamePanel;
	
	public GameLoop(MapPanel panel)
	{
		this._gamePanel = panel;
	}
	
	/**
	 * The Game Loop
	 * @param arg0
	 */
	@Override
	public void actionPerformed(ActionEvent arg0) 
	{
		if (this._gamePanel.isRunning())
		{
			/**
			 * Logic goes here:
			 */
			this._gamePanel.setLogic();
			
			/**
			 * Rendering the map panel
			 */
			this._gamePanel.repaint();
			//this._gamePanel.paintImmediately(0, 0, this._gamePanel.getWidth(), this._gamePanel.getHeight());	
		}
	}

}
