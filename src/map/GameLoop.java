package map;

public class GameLoop implements Runnable
{
	private Thread _gameThread;
	private MapPanel _gamePanel;
	private final double _updateCap = 1.0 / 60.0;
	private int _fps;
	
	public GameLoop(MapPanel panel)
	{
		this._gamePanel = panel;
	}
	
	public void startGame()
	{
		this._gameThread = new Thread(this);
		this._gameThread.start();
	}
	
	@Override
	public void run()
	{
		double firstTime = 0,
				lastTime = System.nanoTime() / 1000000000.0,
				passedTime = 0,
				unprocessedTime = 0,
				frameTime = 0;
		int frames = 0;
		
		while (this._gamePanel.isRunning())
		{
			firstTime = System.nanoTime() / 1000000000.0;
			passedTime = firstTime - lastTime;
			lastTime = firstTime;
			unprocessedTime += passedTime;
			frameTime += passedTime;
			
			if (unprocessedTime >= _updateCap)
			{
				unprocessedTime -= _updateCap;
				tick();
				render();
				frames++;
				
				if (frameTime >= 1.0)
				{
					frameTime = 0;
					_fps = frames;
					frames = 0;
					System.out.println("FPS: " + _fps);
				}
			}
			
			
			/**
			 * Preventing over-consumption of the computer's CPU power -
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
	
	private void render()
	{
		_gamePanel.repaint();
	}
	
	private void tick()
	{
		_gamePanel.setLogic();
	}
}
