package map;
import javax.swing.SwingUtilities;

/**
 * The Game Loop Class -
 */
public class FixedTSGameLoop implements Runnable
{
	private MapPanel _gamePanel;
	private final long _fps = 60;

	public FixedTSGameLoop(MapPanel panel)
	{
		this._gamePanel = panel;
	}

	@Override
	public void run()
	{
		long lastTime = System.nanoTime(), now;
		double nsTick = 1000000000 / _fps, deltaTick = 0;
		
		while (this._gamePanel.isRunning())
		{
			now = System.nanoTime();
			deltaTick += (now - lastTime) / nsTick;
			lastTime = now;
			if (deltaTick >= 1)
			{
				tick();
				render();
				deltaTick--;
			}
			else
			{
				/**
				 * Preventing from the loop to over-consume CPU power - 
				 */
				try
				{
					Thread.sleep(1);
				}
				catch (InterruptedException e)
				{
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
		}
	}
	
	private void tick()
	{
		/**
		 * Logic goes here:
		 */
		SwingUtilities.invokeLater(() -> _gamePanel.setLogic());
	}

	private void render()
	{
		/**
		 * Rendering the map panel
		 */
		SwingUtilities.invokeLater(() -> _gamePanel.paintImmediately(_gamePanel.getBounds()));
	}
}
