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
		long lastTime = System.nanoTime(), now;
		double amountOfTicks = 60.0;
		double ns = 1000000000 / amountOfTicks;
		double delta = 0;
		long timer = System.currentTimeMillis();
		int updates = 0;
		int frames = 0;
		while (this._gamePanel.isRunning())
		{
			now = System.nanoTime();
			delta += (now - lastTime) / ns;
			lastTime = now;
			while (delta >= 1)
			{
				tick();
				updates++;
				render();
				frames++;
				delta--;
			}
			render();
			frames++;
			
			/**
			 * Capping the framerate - 
			 */
			try
			{
				Thread.sleep(5);
			}
			catch (InterruptedException e)
			{
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			if (System.currentTimeMillis() - timer > 1000)
			{
				timer += 1000;
				//System.out.println("FPS: " + frames + " TICKS: " + updates);
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
