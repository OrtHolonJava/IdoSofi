package map;
import javax.swing.SwingUtilities;

public class FixedTSGameLoop implements Runnable
{
	private MapPanel _gamePanel;

	public FixedTSGameLoop(MapPanel panel)
	{
		this._gamePanel = panel;
	}

	@Override
	public void run()
	{
		long lastTime = System.nanoTime(), now;
		double amountOfTicks = 60.0;
		double nsTick = 1000000000 / amountOfTicks;
		double deltaTick = 0;
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
			
			/**
			 * Preventing from the while loop to consume the CPU like mad.
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

	private void tick()
	{
		/**
		 * Logic goes here:
		 */
		_gamePanel.setLogic();
	}

	private void render()
	{
		/**
		 * Rendering the map panel
		 */
		SwingUtilities.invokeLater(() -> _gamePanel.paintImmediately(_gamePanel.getBounds()));
	}
	
	private void both()
	{
		tick();
		render();
	}
}
