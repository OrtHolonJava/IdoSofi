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
		double amountOfRenders = 120.0;
		double nsTick = 1000000000 / amountOfTicks;
		double nsRender = 1000000000 / amountOfRenders;
		double deltaTick = 0;
		double deltaRender = 0;
		while (this._gamePanel.isRunning())
		{
			now = System.nanoTime();
			deltaTick += (now - lastTime) / nsTick;
			deltaRender += (now - lastTime) / nsRender;
			lastTime = now;
			while (deltaTick >= 1)
			{
				tick();
				deltaTick--;
			}

			while (deltaRender >= 1)
			{
				render();
				deltaRender--;
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
		 SwingUtilities.invokeLater(() -> _gamePanel.repaint());
	}
}
