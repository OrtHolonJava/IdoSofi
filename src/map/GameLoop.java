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
			_targetFPS = 60,
			_timeBetweenRenders = 1000000000 / _targetFPS;
	
	private final int _maxUpdatesBeforeRender = 1;
	
	private static int _currentFPS;
	
	public GameLoop(MapPanel panel)
	{
		this._gamePanel = panel;
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
			 * Doing as many game updates as we currently need to -
			 */
			while (now - lastUpdateTime > _timeBetweenUpdates && updateCount < _maxUpdatesBeforeRender)
			{
				this.tick();
				lastUpdateTime += _timeBetweenUpdates;
				updateCount++;
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
				_currentFPS = frameCount;
				frameCount = 0;
				lastSecondTime = thisSecond;
			}

			/**
			 * The timing mechanism.
			 * The thread sleeps within this while loop until enough time has passed and another update or render call is required.
			 */
			while (now - lastRenderTime < _timeBetweenRenders && now - lastUpdateTime < _timeBetweenUpdates)
			{
				//Thread.yield(); //Yield until it has been at least the target time between renders. This saves the CPU from hogging.
				
				/**
				 * Preventing over-consumption of the system's CPU power.
				 */
				try
				{
					//Thread.sleep(1);
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
		//SwingUtilities.invokeLater(() -> _gamePanel.paintImmediately(_gamePanel.getBounds()));
		//_gamePanel.paintImmediately(_gamePanel.getBounds());
		//SwingUtilities.invokeLater(() -> _gamePanel.repaint());
		_gamePanel.repaint();
	}

	private void tick()
	{
		SwingUtilities.invokeLater(() -> _gamePanel.setLogic());
	}
	
	public void startGame()
	{
		this._gameThread = new Thread(this);
		_gameThread.setDaemon(true);
		this._gameThread.start();
	}
	
	public static int getCurrentFPS()
	{
		return _currentFPS;
	}
}