package map;
import java.util.TimerTask;

public class GameLoop extends TimerTask
{
	private MapPanel _gamePanel;
	
	public GameLoop(MapPanel panel)
	{
		this._gamePanel = panel;
	}
	
	@Override
	public void run() 
	{
		/**
		 * Logic goes here:
		 */
		this._gamePanel.checkTerrainCollision();
		
		/**
		 * Rendering the map panel
		 */
		this._gamePanel.repaint();
	}

}
