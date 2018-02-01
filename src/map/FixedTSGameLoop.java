package map;

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
		double test1=0, test2=0, test3=0, test4=0;
		long lastTime = System.nanoTime(), now;
		double amountOfTicks = 60.0;
		double amountOfRenders = 120.0;
		double nsTick = 1000000000 / amountOfTicks;
		double nsRender = 1000000000 / amountOfRenders;
		double deltaTick = 0;
		double deltaRender = 0;
		long timer = System.currentTimeMillis();
		int updates = 0;
		int frames = 0;
		while (this._gamePanel.isRunning())
		{
			now = System.nanoTime();
			deltaTick += (now - lastTime) / nsTick;
			deltaRender += (now - lastTime) / nsRender;
			lastTime = now;
			while (deltaTick >= 1)
			{
				test1 = System.nanoTime();
				tick();
				test2 = System.nanoTime() - test1;
				updates++;
				deltaTick--;
			}

			while (deltaRender >= 1)
			{
				test3 = System.nanoTime();
				render();
				test4 = System.nanoTime() - test3;
				frames++;
				deltaRender--;
			}

			if (System.currentTimeMillis() - timer > 1000)
			{
				timer += 1000;
				System.out.println("FPS: " + frames + " TICKS: " + updates + " Render time: " + test4 + " Tick time: " + test2);
				frames = 0;
				updates = 0;
			}
		}
	}

	private void tick()
	{
		/**
		 * Logic goes here:
		 */
		this._gamePanel.setLogic();
	}

	private void render()
	{
		/**
		 * Rendering the map panel
		 */
		this._gamePanel.repaint();
	}
}
