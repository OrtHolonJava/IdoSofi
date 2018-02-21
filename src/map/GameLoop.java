package map;

import javax.swing.SwingUtilities;

public class GameLoop implements Runnable
{
	private Thread _gameThread;
	private MapPanel _gamePanel;
	
	/**
	 * Game loop numeric finals:
	 */
	private final double _ups = 60.0,
			_timeBetweenUpdates = 1000000000 / _ups,
			_targetFPS = 60.0,
			_timeBetweenRenders = 1000000000 / _targetFPS;
	
	private final int _maxUpdatesBeforeRender = 1;

	public GameLoop(MapPanel panel)
	{
		this._gamePanel = panel;
	}

	public void startGame()
	{
		this._gameThread = new Thread(this);
		_gameThread.setDaemon(true);
		this._gameThread.start();
	}

	@Override
	public void run()
	{
		/**
		 * New version -
		 */
		
		double lastUpdateTime = System.nanoTime(); // Store the time of the last update call.
		double lastRenderTime = System.nanoTime(); // Store the time of the last render call.
		double now;
		int updateCount;
		
		/**
		 * FPS Calculation Variables
		 */
		int lastSecondTime = (int) (lastUpdateTime / 1000000000);
		int frameCount = 0;
		
		while (this._gamePanel.isRunning())
		{
			now = System.nanoTime();
			updateCount = 0;
			
			/**
			 * Doing as many game updates as we currently need to.
			 */
			while (now - lastUpdateTime > _timeBetweenUpdates && updateCount < _maxUpdatesBeforeRender)
			{
				this.tick();
				lastUpdateTime += _timeBetweenUpdates;
				updateCount++;
			}

			//If for some reason an update takes forever, we don't want to do an insane number of catchups.
			//If you were doing some sort of game that needed to keep EXACT time, you would get rid of this.
			if (now - lastUpdateTime > _timeBetweenUpdates)
			{
				lastUpdateTime = now - _timeBetweenUpdates;
			}
			
			/**
			 * Render the current (updated) state of the game.
			 */
			this.render();
			frameCount++;
			lastRenderTime = now;

			//Update the frames we got.
			int thisSecond = (int) (lastUpdateTime / 1000000000);
			if (thisSecond > lastSecondTime)
			{
				System.out.println("FPS: " + frameCount);
				frameCount = 0;
				lastSecondTime = thisSecond;
			}

			/**
			 * The timing mechanism.
			 * The thread sleeps within this while loop until enough time has passed and another update or render call is required.
			 */
			while (now - lastRenderTime < _timeBetweenRenders && now - lastUpdateTime < _timeBetweenUpdates)
			{
				Thread.yield(); //Yield until it has been at least the target time between renders. This saves the CPU from hogging.
				
				/**
				 * Preventing over-consumption of the system's CPU power.
				 */
				try
				{
					Thread.sleep(1);
				}
				catch (Exception e)
				{
				}
				
				now = System.nanoTime();
			}
		}
	}

	private void render()
	{
		SwingUtilities.invokeLater(() -> _gamePanel.repaint());
		//SwingUtilities.invokeLater(() -> _gamePanel.paintImmediately(_gamePanel.getBounds()));
	}

	private void tick()
	{
		SwingUtilities.invokeLater(() -> _gamePanel.setLogic());
	}
}
